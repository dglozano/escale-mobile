package com.dglozano.escale.util;

import android.content.SharedPreferences;

import com.dglozano.escale.db.EscaleDatabase;
import com.dglozano.escale.di.annotation.CacheDirectory;
import com.dglozano.escale.di.annotation.RootFileDirectory;

import java.io.File;

import javax.inject.Inject;

import timber.log.Timber;

import static com.dglozano.escale.util.Constants.HAS_NEW_UNREAD_DIET_SHARED_PREF;
import static com.dglozano.escale.util.Constants.IS_FIREBASE_TOKEN_SENT_SHARED_PREF;
import static com.dglozano.escale.util.Constants.REFRESH_TOKEN_SHARED_PREF;
import static com.dglozano.escale.util.Constants.SCALE_USER_INDEX_SHARED_PREF;
import static com.dglozano.escale.util.Constants.SCALE_USER_PIN_SHARED_PREF;
import static com.dglozano.escale.util.Constants.TOKEN_SHARED_PREF;
import static com.dglozano.escale.util.Constants.UNREAD_MESSAGES_SHARED_PREF;

public class LogoutHelper {

    private AppExecutors mAppExecutors;
    private SharedPreferences mSharedPreferences;
    private EscaleDatabase mRoomDatabase;
    private File mRootFileDirectory;
    private File mCacheDirectory;

    @Inject
    public LogoutHelper(AppExecutors appExecutors,
                        SharedPreferences sharedPreferences,
                        EscaleDatabase roomDatabase,
                        @RootFileDirectory File rootFileDirectory,
                        @CacheDirectory File cacheDirectory) {
        this.mAppExecutors = appExecutors;
        this.mSharedPreferences = sharedPreferences;
        this.mRoomDatabase = roomDatabase;
        this.mRootFileDirectory = rootFileDirectory;
        this.mCacheDirectory = cacheDirectory;
    }

    public void logout() {
        mAppExecutors.getDiskIO().execute(() -> {
            Timber.d("Clearing preferences");
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putLong(Constants.LOGGED_USER_ID_SHARED_PREF, -1L);
            editor.remove(TOKEN_SHARED_PREF);
            editor.remove(REFRESH_TOKEN_SHARED_PREF);
            editor.remove(SCALE_USER_INDEX_SHARED_PREF);
            editor.remove(SCALE_USER_PIN_SHARED_PREF);
            editor.putBoolean(IS_FIREBASE_TOKEN_SENT_SHARED_PREF, false);
            editor.putBoolean(HAS_NEW_UNREAD_DIET_SHARED_PREF, false);
            editor.putInt(UNREAD_MESSAGES_SHARED_PREF, 0);
            editor.apply();
            Timber.d("Clearing db");
            mRoomDatabase.clearAllTables();
            Timber.d("Clearing internal storage");
            FileUtils.deleteContentOfDirectory(mRootFileDirectory);
            Timber.d("Clearing cache directory");
            FileUtils.deleteContentOfDirectory(mCacheDirectory);
        });
    }
}
