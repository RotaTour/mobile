package br.ufrpe.projetao.rotatour;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.Response;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import br.ufrpe.projetao.rotatour.activities.LoginActivity;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;

//Classe para salvar informações de sessão de usuário no próprio dispositivo Android

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_PASSWORD = "keypassword";
    private static final String KEY_TOKEN = "keytoken";
    private static final String KEY_PROVIDER = "keyprovider";
    private static final String KEY_PROVIDER_ID = "keyproviderid";
    private static final String KEY_AVATAR = "keyavatar";

    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //metodo para logar o usuario, vai armazenar as informacoes do usuario em SharedPreferences
    public void userLogin(Usuario user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_TOKEN, user.getToken());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_PROVIDER, user.getProvider());
        editor.putString(KEY_PROVIDER_ID, user.getProviderId());
        editor.putString(KEY_AVATAR, user.getAvatar());
        editor.apply();
    }

    //checar se o usuario esta logado
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_TOKEN, null) != null;
    }

    //retornar o usuario que esta logado
    public Usuario getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Usuario (sharedPreferences.getString(KEY_EMAIL,null)
                , sharedPreferences.getString(KEY_PASSWORD, null)
                , sharedPreferences.getString(KEY_TOKEN, null)
                , sharedPreferences.getString(KEY_PROVIDER, null)
                , sharedPreferences.getString(KEY_PROVIDER_ID, null)
                , sharedPreferences.getString(KEY_AVATAR, null));
    }

    //deslogar o usuario
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        LoginManager.getInstance().logOut(); // desconectar do facebook
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}