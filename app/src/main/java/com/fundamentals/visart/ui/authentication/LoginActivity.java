package com.fundamentals.visart.ui.authentication;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.fundamentals.visart.R;
import com.fundamentals.visart.managers.UserManager;
import com.fundamentals.visart.ui.MainActivity;
import com.fundamentals.visart.utils.Constants;
import com.fundamentals.visart.utils.FirebaseRefFactory;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.Plus;

import org.w3c.dom.Text;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jermiedomingo on 3/12/16.
 */
public class LoginActivity extends AppCompatActivity {

    @Bind(R.id.btn_facebook_login)
    Button mLoginWithFacebookButton;
    @Bind(R.id.btn_google_login)
    Button mLoginWithGoogleButton;

    @Bind(R.id.btn_login)
    Button mLoginWithEmailButton;
    @Bind(R.id.input_email)
    EditText mEmailEditText;
    @Bind(R.id.input_password)
    EditText mPasswordEditText;


    CallbackManager callbackManager;

    private GoogleAuthenticationHelper mGoogleAuthenticationHelper;
    private static final int RC_SIGN_IN = 9001;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                onAuthenticateUser(Constants.FB_PROVIDER, loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LoginActivity.this, "Login error. Please try again later!", Toast.LENGTH_SHORT).show();
            }
        });

        mGoogleAuthenticationHelper = new GoogleAuthenticationHelper(this);
        //mGoogleAuthenticationHelper.connectToGoogleAPIClient();


    }

    @OnClick(R.id.btn_facebook_login)
    void loginWithFacebook() {

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

    }

    @OnClick(R.id.btn_google_login)
    void loginWithGoogle() {

        mGoogleAuthenticationHelper.connectToGoogleAPIClient();
        Intent signInIntent = mGoogleAuthenticationHelper.getSignInINtent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @OnClick(R.id.btn_login)
    void loginWithEmail() {
        //TODO login with email implementation
    }


    protected void onAuthenticateUser(String provider, String token) {

        final Firebase ref = FirebaseRefFactory.getFirebaseRef();
        if (token != null) {
            ref.authWithOAuthToken(provider, token, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {

                    //Update values of the authenticated user

                    UserManager.updateUser(authData);

                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    //Start the main app
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);

                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {

                    // there was an error
                }
            });
        } else {
            ref.unauth();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {

            mGoogleAuthenticationHelper.handleSignInResult(data);

        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


}
