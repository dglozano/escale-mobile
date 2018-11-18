package com.dglozano.escale.ble;

import java.util.UUID;

public class GattConstants {

    // Generic Access Service
    public static final UUID GENERIC_ACCESS_SERVICE =
            UUID.fromString("00001800-0000-1000-8000-00805F9B34FB");
    public static final UUID DEVICE_NAME =
            UUID.fromString("00002A00-0000-1000-8000-00805F9B34FB");
    public static final UUID APPEARANCE =
            UUID.fromString("00002A01-0000-1000-8000-00805F9B34FB");
    public static final UUID PERIPHERICAL_PRIVACY_FLAG =
            UUID.fromString("00002A02-0000-1000-8000-00805F9B34FB");
    public static final UUID RECONNECTION_ADDRESS =
            UUID.fromString("00002A03-0000-1000-8000-00805F9B34FB");
    public static final UUID PERIPHERICAL_PREFERRED_CONNECTION_PARAMETERS =
            UUID.fromString("00002A04-0000-1000-8000-00805F9B34FB");

    // Generic Attribute Service
    public static final UUID GENERIC_ATTRIBUTE_SERVICE =
            UUID.fromString("00001801-0000-1000-8000-00805F9B34FB");
    public static final UUID SERVICE_CHANGED =
            UUID.fromString("00002A05-0000-1000-8000-00805F9B34FB");

    // Device Information Service
    public static final UUID DEVICE_INFORMATION_SERVICE =
            UUID.fromString("0000180A-0000-1000-8000-00805F9B34FB");
    public static final UUID SERIAL_NUMBER_STRING =
            UUID.fromString("00002A25-0000-1000-8000-00805F9B34FB");
    public static final UUID MANUFACTURER_NAME_STRING =
            UUID.fromString("00002A29-0000-1000-8000-00805F9B34FB");
    public static final UUID SYSTEM_ID =
            UUID.fromString("00002A23-0000-1000-8000-00805F9B34FB");
    public static final UUID FIRMWARE_REVISION_STRING =
            UUID.fromString("00002A26-0000-1000-8000-00805F9B34FB");
    public static final UUID MODEL_NUMBER_STRING =
            UUID.fromString("00002A24-0000-1000-8000-00805F9B34FB");
    public static final UUID HARDWARE_REVISION_STRING =
            UUID.fromString("00002A27-0000-1000-8000-00805F9B34FB");
    public static final UUID SOFTWARE_REVISION_STRING =
            UUID.fromString("00002A28-0000-1000-8000-00805F9B34FB");
    public static final UUID PNP_ID =
            UUID.fromString("00002A50-0000-1000-8000-00805F9B34FB");
    public static final UUID IEEE_11073_20601_REGULATORY_CERTIFICATION_DATA_LIST =
            UUID.fromString("00002A2A-0000-1000-8000-00805F9B34FB");

    // Custom "FEBA" Service
    public static final UUID CUSTOM_FEBA_SERVICE =
            UUID.fromString("0000FEBA-0000-1000-8000-00805F9B34FB");
    public static final UUID OTA_DATA =
            UUID.fromString("0000FA10-0000-1000-8000-00805F9B34FB");
    public static final UUID OTA_CMD =
            UUID.fromString("0000FA11-0000-1000-8000-00805F9B34FB");
    public static final UUID DATA_ERROR_STATUS =
            UUID.fromString("0000FA13-0000-1000-8000-00805F9B34FB");

    // Battery Service
    public static final UUID BATTERY_SERVICE =
            UUID.fromString("0000180F-0000-1000-8000-00805F9B34FB");
    public static final UUID BATTERY_LEVEL =
            UUID.fromString("00002A19-0000-1000-8000-00805F9B34FB");

    // Custom Service FFF0 for users listing, activity, activate scale and unit change
    public static final UUID CUSTOM_FFF0_SERVICE =
            UUID.fromString("0000FFF0-0000-1000-8000-00805F9B34FB");
    public static final UUID CUSTOM_FFF1_UNIT_CHARACTERISTIC =
            UUID.fromString("0000FFF1-0000-1000-8000-00805F9B34FB");
    public static final UUID CUSTOM_FFF2_USER_LIST_CHARACTERISTIC =
            UUID.fromString("0000FFF2-0000-1000-8000-00805F9B34FB");
    public static final UUID CUSTOM_FFF3_PH_ACTIVITY_CHARACTERISTIC =
            UUID.fromString("0000FFF3-0000-1000-8000-00805F9B34FB");
    public static final UUID CUSTOM_FFF4_ACTIVATE_SCALE_CHARACTERISTIC =
            UUID.fromString("0000FFF4-0000-1000-8000-00805F9B34FB");
    public static final UUID CUSTOM_FFF5_UNKNOWN_CHARACTERISTIC =
            UUID.fromString("0000FFF5-0000-1000-8000-00805F9B34FB");

    // Body composition service
    public static final UUID BODY_COMPOSITION_SERVICE =
            UUID.fromString("0000181B-0000-1000-8000-00805F9B34FB");
    public static final UUID BODY_COMPOSITION_FEATURE =
            UUID.fromString("00002A9B-0000-1000-8000-00805F9B34FB");
    public static final UUID BODY_COMPOSITION_MEASUREMENT =
            UUID.fromString("00002A9C-0000-1000-8000-00805F9B34FB");

    // Weight Scale service
    public static final UUID WEIGHT_SCALE_SERVICE =
            UUID.fromString("0000181D-0000-1000-8000-00805F9B34FB");
    public static final UUID WEIGHT_SCALE_FEATURE =
            UUID.fromString("00002A9E-0000-1000-8000-00805F9B34FB");
    public static final UUID WEIGHT_MEASUREMENT =
            UUID.fromString("00002A9D-0000-1000-8000-00805F9B34FB");

    // Current Time service
    public static final UUID CURRENT_TIME_SERVICE =
            UUID.fromString("00001805-0000-1000-8000-00805F9B34FB");
    public static final UUID CURRENT_TIME =
            UUID.fromString("00002A2B-0000-1000-8000-00805F9B34FB");
    public static final UUID LOCAL_TIME_INFO =
            UUID.fromString("00002A0F-0000-1000-8000-00805F9B34FB");
    public static final UUID REFERENCE_TIME_INFO =
            UUID.fromString("00002A14-0000-1000-8000-00805F9B34FB");

    // User Data service
    public static final UUID USER_DATA_SERVICE =
            UUID.fromString("0000181C-0000-1000-8000-00805F9B34FB");
    public static final UUID DATE_OF_BIRTH =
            UUID.fromString("00002A85-0000-1000-8000-00805F9B34FB");
    public static final UUID GENDER =
            UUID.fromString("00002A8C-0000-1000-8000-00805F9B34FB");
    public static final UUID HEIGHT =
            UUID.fromString("00002A8E-0000-1000-8000-00805F9B34FB");
    public static final UUID DB_CHANGE_INCREMENT =
            UUID.fromString("00002A99-0000-1000-8000-00805F9B34FB");
    public static final UUID USER_INDEX =
            UUID.fromString("00002A9A-0000-1000-8000-00805F9B34FB");
    public static final UUID USER_CONTROL_POINT =
            UUID.fromString("00002A9F-0000-1000-8000-00805F9B34FB");

    // Descriptors
    public static final UUID CLIENT_CHARACTERISTICS_CONFIGURATION =
            UUID.fromString("00002902-0000-1000-8000-00805F9B34FB");
    public static final UUID CHARACTERISTIC_USER_DESCRIPTION =
            UUID.fromString("00002901-0000-1000-8000-00805F9B34FB");

}