package br.ufrpe.projetao.rotatour.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import br.ufrpe.projetao.rotatour.R;
import br.ufrpe.projetao.rotatour.SharedPrefManager;
import br.ufrpe.projetao.rotatour.Usuario;
import br.ufrpe.projetao.rotatour.requests_volley.VolleySingleton;

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

        final String email = mTxtEmail.getText().toString();
        final String password = mTxtSenha.getText().toString();

        VolleySingleton.getInstance(this).postLogin(new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    response = jsonObject.getString("token");
                    Log.i("LoginRotaActivity", response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(new Usuario(email, password, response));
                setResult(RESULT_OK);
                killActivity();
                startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                mBtnLogin.setEnabled(true);
                progressDialog.dismiss();
                mTxtEmail.setError(getString(R.string.loginRT_erro_emailOuSenha));
                mTxtSenha.setError(getString(R.string.loginRT_erro_emailOuSenha));
                mTxtEmail.requestFocus();
            }
        }, email, password);

        /*new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                       // onLoginFailed();
                        progressDialog.dismiss();
                        killActivity();
                    }
                }, 5000); */
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), R.string.loginRT_login_falhou, Toast.LENGTH_LONG).show();

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

    private void killActivity() {
        finish();
    }
}
