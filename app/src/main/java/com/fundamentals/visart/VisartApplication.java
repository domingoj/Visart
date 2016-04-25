package com.fundamentals.visart;

import android.app.Application;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.fundamentals.visart.ui.authentication.LoginActivity;
import com.fundamentals.visart.utils.FirebaseRefFactory;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by jermiedomingo on 3/8/16.
 */
public class VisartApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        FacebookSdk.sdkInitialize(getApplicationContext());


    }
}