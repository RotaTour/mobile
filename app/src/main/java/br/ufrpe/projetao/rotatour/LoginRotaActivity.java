package br.ufrpe.projetao.rotatour;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginRotaActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText mTxtEmail;
    private EditText mTxtSenha;
    private Button mBtnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_rota);

        mTxtEmail = findViewById(R.id.loginRT_edit_email);
        mTxtSenha = findViewById(R.id.loginRT_edit_senha);
        mBtnLogin = findViewById(R.id.loginRT_button_login);

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        mBtnLogin.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginRotaActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loginRT_autenticando));
        progressDialog.show();

        String email = mTxtEmail.getText().toString();
        String password = mTxtSenha.getText().toString();

        try {
            userLogin(email, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 5000);
    }

    public void onLoginSuccess() {
        mBtnLogin.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), R.string.loginRT_login_falou, Toast.LENGTH_LONG).show();

        mBtnLogin.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = mTxtEmail.getText().toString();
        String password = mTxtSenha.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mTxtEmail.setError(getString(R.string.loginRT_erro_email));
            valid = false;
        } else {
            mTxtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mTxtSenha.setError(getString(R.string.loginRT_erro_senha));
            valid = false;
        } else {
            mTxtSenha.setError(null);
        }

        return valid;
    }

    private void userLogin(final String email, final String password) throws JSONException {
        //TODO SUBSTITUIR PELA CLASSE URLs
        String url = "https://jsonplaceholder.typicode.com/posts";
        VolleySingleton.getInstance(this).postLogin(url, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.loginRT_conect_error, Toast.LENGTH_LONG).show();
            }
        },email, password);

        Intent intent = new Intent(this, PerfilActivity.class);
        startActivity(intent);
    }
}
