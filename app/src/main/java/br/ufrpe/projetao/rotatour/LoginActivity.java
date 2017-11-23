package br.ufrpe.projetao.rotatour;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private TextView mInfo;
    private LoginButton mFacebookButton;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton mGoogleButton;
    private Button mBtnLoginRT; // login com conta RotaTour
    private Button mBtnCadastrarContaRT; // cadastrar conta RotaTour
    public static final int GOOGLE_SIGN_IN_CODE = 777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        mBtnLoginRT = (Button) findViewById(R.id.login_button_signIn);
        mBtnCadastrarContaRT = (Button) findViewById(R.id.login_button_signUp);

        //OPCIONAL -- printar keyhash do app no Logcat do android studio
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "br.ufrpe.projetao.rotatour",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        //fim print keyhash

        mBtnLoginRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginRotaActivity.class));
            }
        });

        mBtnCadastrarContaRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CriarContaActivity.class));
            }
        });

        //Google login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleButton = (SignInButton)findViewById(R.id.login_button_google);

        //mudar texto do botão google
        setGoogleButtonText(mGoogleButton, getString(R.string.login_google));
        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(intent, GOOGLE_SIGN_IN_CODE);
            }
        });

        //Facebook login
        mInfo = (TextView)findViewById(R.id.info);
        mFacebookButton = (LoginButton)findViewById(R.id.login_button_facebook);
        mFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            //Tratar login FACEBOOK
            @Override
            public void onSuccess(LoginResult loginResult) {
                mInfo.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );
            }

            @Override
            public void onCancel() {
                mInfo.setText("Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                mInfo.setText(e.getMessage()    );
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN_IN_CODE){
           GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    //Tratar login GOOGLE
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            mInfo.setText(result.getSignInAccount().getEmail());
        }else{
            Toast.makeText(this, R.string.login_falhou, Toast.LENGTH_LONG).show();
        }
    }

    protected void setGoogleButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                tv.setTextSize(13);
                return;
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }
}


