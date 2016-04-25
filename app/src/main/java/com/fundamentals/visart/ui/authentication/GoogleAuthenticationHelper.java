package com.fundamentals.visart.ui.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.fundamentals.visart.R;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import java.io.IOException;

/**
 * Created by jermiedomingo on 3/19/16.
 */
public class GoogleAuthenticationHelper implements GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = GoogleAuthenticationHelper.class.getSimpleName();

    public Context context;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions gso;

    public GoogleAuthenticationHelper(Context context) {
        this.context = context;
    }


    public void handleSignInResult(Intent data) {


        GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String email = acct.getEmail();
            String scopes = "oauth2:profile email";
            new TokenTask().execute(email, scopes);


        } else {
            // Signed out, show unauthenticated UI.
        }
    }

    private class TokenTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String scopes = params[1];
            String token = null;
            try {
                token = GoogleAuthUtil.getToken(context.getApplicationContext(), email, scopes);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (GoogleAuthException e) {
                e.printStackTrace();
            }

            return token;
        }

        @Override
        protected void onPostExecute(String token) {
            ((LoginActivity) context).onAuthenticateUser("google", token);
        }
    }

    public void connectToGoogleAPIClient() {

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }

        // Configure sign-in to request the user's ID and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.server_client_id))
                .requestEmail()
                .requestScopes(Plus.SCOPE_PLUS_LOGIN, Plus.SCOPE_PLUS_PROFILE)
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                //.enableAutoManage(((FragmentActivity) context) /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addOnConnectionFailedListener(this)
                .build();

        mGoogleApiClient.connect();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public Scope[] getGSOScopeArray() {
        return gso.getScopeArray();
    }

    public Intent getSignInINtent() {
        return Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
    }


}
