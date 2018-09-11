package com.example.dglozano.escale;

import android.app.Activity;
import android.bluetooth.le.ScanCallback;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.dglozano.escale.bluetooth.BluetoothCommunication;
import com.example.dglozano.escale.utils.GattConstants;
import com.example.dglozano.escale.utils.PermissionHelper;

import static com.example.dglozano.escale.bluetooth.BluetoothCommunication.byteInHex;
import static com.example.dglozano.escale.utils.GattConstants.*;

public class MeasurementActivity extends AppCompatActivity {

    private static final String TAG = MeasurementActivity.class.getSimpleName();

    // Variables de control
    private boolean mBound = false;
    private BluetoothCommunication mBluetoothCommService;

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mBound = true;
            BluetoothCommunication.LocalBinder localBinder = (BluetoothCommunication.LocalBinder) binder;
            mBluetoothCommService = localBinder.getService();
            if(PermissionHelper.requestBluetoothPermission(MeasurementActivity.this)){
                scanAndConnect();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    private void initScale() {
        mBluetoothCommService.readBytes(GattConstants.BATTERY_SERVICE, BATTERY_LEVEL)
                .thenCompose(ch -> {
                    System.out.println("Char read " + ch.toString());
                    return mBluetoothCommService.setIndicationOn(
                            USER_DATA_SERVICE,
                            USER_CONTROL_POINT,
                            CLIENT_CHARACTERISTICS_CONFIGURATION);
                })
                .thenCompose(ds -> {
                    System.out.println("Descriptor indication on " + ds.toString());
                    return mBluetoothCommService.setIndicationOn(
                            WEIGHT_SCALE_SERVICE,
                            WEIGHT_MEASUREMENT,
                            CLIENT_CHARACTERISTICS_CONFIGURATION);
                })
                .thenCompose(ds -> {
                    System.out.println("write response " + ds.toString());
                    return mBluetoothCommService.createUser(new byte[] {0x10,0x00});
                })
                .thenCompose(ds -> {
                    System.out.println("write response " + ds.toString());
                    return mBluetoothCommService.deleteUser((byte) 0x01);
                })
                .thenCompose(ds -> {
                    System.out.println("write response " + ds.toString());
                    return mBluetoothCommService.consentUser((byte) 0x01, new byte[] {0x10,0x00});
                })
                .thenCompose(ds -> {
                    System.out.println("write response " + ds.toString());
                    return mBluetoothCommService.deleteUser((byte) 0x01);
                })
                .thenCompose(ds -> {
                    System.out.println("write response " + ds.toString());
                    return mBluetoothCommService.createUser(new byte[] {0x10,0x00});
                })
                .thenCompose(ds -> {
                    System.out.println("write response " + ds.toString());
                    return mBluetoothCommService.consentUser((byte) 0x01, new byte[] {0x10,0x00});
                })
                .thenAccept(ch -> {
                    byte[] bytesTurnOnscale = {0x00};
                    mBluetoothCommService.writeBytes(
                            CUSTOM_FFF0_SERVICE,
                            CUSTOM_FFF4_ACTIVATE_SCALE_CHARACTERISTIC,
                            bytesTurnOnscale);
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    // PermissionHelper solicitó habilitar Bluetooth porque estaba desactivado. Este es el callback.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == PermissionHelper.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_CANCELED) {
                finish();
                return;
            } else {
                if(mBound)
                    mBluetoothCommService.scanForBleDevices(getString(R.string.bf600)).thenAccept(device -> {
                        System.out.println("Got a device from remote service " + device.getAddress());
                    });
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    // PermissionHelper pidió permiso para COARSE, cuando elije algo vuelve acá
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],int[] grantResults) {
        switch (requestCode) {
            case PermissionHelper.PERMISSION_REQUEST_COARSE: {
                // si el request es cancelado el arreglo es vacio.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // tengo el permiso, busco bluetooth!!
                    scanAndConnect();
                } else {
                    Toast.makeText(this, R.string.coarse_permission_message, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void scanAndConnect() {
        mBluetoothCommService.scanForBleDevices(getString(R.string.bf600))
                .exceptionally(ex -> {
                    Log.d(TAG,"Oops! We have an exception - " + ex.getMessage());
                    scanAndConnect();
                    return null;
                })
                .thenCompose(device -> mBluetoothCommService.connectGatt(device))
                .thenRun(() -> {
                    Log.d(TAG, "HOLA");
                    initScale();
                });
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, BluetoothCommunication.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
