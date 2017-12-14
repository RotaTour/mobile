package br.ufrpe.projetao.rotatour;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

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
    public static final int REQUEST_CADASTRO = 78;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        mCallbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();
        }

        mBtnLoginRT = findViewById(R.id.login_button_signIn);
        mBtnCadastrarContaRT = findViewById(R.id.login_button_signUp);

        mBtnLoginRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), LoginRotaActivity.class), REQUEST_LOGIN);
            }
        });

        mBtnCadastrarContaRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), CriarContaActivity.class), REQUEST_CADASTRO);
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
        //setar permissoes no Facebook
        mFacebookButton.setReadPermissions("public_profile");
        mFacebookButton.setReadPermissions("email");
        //solicitar login FACEBOOK
        mFacebookButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            String name;
            String avatar;
            String email;
            String provider = "facebook";
            String provider_id;
            @Override
            public void onSuccess(LoginResult loginResult) {
                /*mInfo.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );*/

                //solicitar informações do perfil Facebook
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());
                                try {
                                    email = object.getString("email");
                                    name = object.getString("name");
                                    avatar = object.getJSONObject("picture").getJSONObject("data").getString("url");
                                    provider_id = object.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                loginSocial(name, email, avatar, provider, provider_id);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday,picture");
                request.setParameters(parameters);
                request.executeAsync();
                //comunicação com nosso servidor
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
        } else if(requestCode == REQUEST_LOGIN && resultCode == RESULT_OK){
            finish();
        } else if(requestCode == REQUEST_CADASTRO && resultCode == RESULT_OK){
            finish();
        }
    }

    //Tratar login GOOGLE
    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            //pegar dados da conta do Google
            GoogleSignInAccount contaGoogle = result.getSignInAccount();
            String name = contaGoogle.getDisplayName();
            final String email = contaGoogle.getEmail();
            String avatar = contaGoogle.getPhotoUrl().toString();
            String provider = "google";
            String provider_id = contaGoogle.getId();

            //comunicação com nosso servidor
            loginSocial(name, email, avatar, provider, provider_id);

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

    private void loginSocial(String name, final String email, String avatar, String provider, String provider_id){
        VolleySingleton.getInstance(getApplicationContext()).postRegisterSocial(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                Log.i("Login", "entrou no response");
                try {
                    jsonObject = new JSONObject(response);
                    response = jsonObject.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("Login", "token: " + response);
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(new Usuario(email, null, response));
                startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null || error.networkResponse == null)
                    return;
                Toast.makeText(getApplicationContext(),"Deu erro",Toast.LENGTH_LONG).show();
                Log.d("login", "net code error " + String.valueOf(error.networkResponse.statusCode));
            }
        }, name, email, avatar, provider, provider_id);
    }
}