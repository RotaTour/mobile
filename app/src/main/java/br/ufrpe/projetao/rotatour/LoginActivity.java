package br.ufrpe.projetao.rotatour;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private TextView mInfo;
    private LoginButton mFacebookButton;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton mGoogleButton;
    private Button mBtnLoginRT; // login com conta RotaTour
    private Button mBtnCadastrarContaRT; // cadastrar conta RotaTour
    public static final int GOOGLE_SIGN_IN_CODE = 777;
    public static final int REQUEST_LOGIN = 77;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mCallbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, PrincipalActivity.class));
            killActivity();
        }

        mBtnLoginRT = findViewById(R.id.login_button_signIn);
        mBtnCadastrarContaRT = findViewById(R.id.login_button_signUp);

        /*//OPCIONAL -- printar keyhash do app no Logcat do android studio
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
        */

        mBtnLoginRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), LoginRotaActivity.class), REQUEST_LOGIN);
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
        mGoogleButton = findViewById(R.id.login_button_google);

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
        mInfo = findViewById(R.id.info);
        mFacebookButton = findViewById(R.id.login_button_facebook);
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
                startActivity(new Intent (getApplicationContext(), PrincipalActivity.class));
            }

            @Override
            public void onCancel() {
                mInfo.setText(R.string.login_facebookCancel);
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
        } else  if(requestCode == REQUEST_LOGIN && resultCode == RESULT_OK){
            finish();
        }
    }

    //Tratar login GOOGLE
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            mInfo.setText(result.getSignInAccount().getEmail());
            Intent intent = new Intent (getApplicationContext(), PrincipalActivity.class);
            startActivity(intent);
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

    //Pressionar botão BACK duas vezes para sair
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.login_backTwice, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    private void killActivity() {
        finish();
    }
}


