package com.fundamentals.visart.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.fundamentals.visart.ui.authentication.LoginActivity;

/**
 * Created by jermiedomingo on 4/2/16.
 */
public class AuthChecker {

    public static boolean checkUserAuth(Context context) {
        if (FirebaseRefFactory.getFirebaseRef().getAuth() == null) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return true;

        }
        return false;
    }
}
