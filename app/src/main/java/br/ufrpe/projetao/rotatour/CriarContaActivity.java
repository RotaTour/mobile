package br.ufrpe.projetao.rotatour;

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

import java.net.HttpURLConnection;

public class CriarContaActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private EditText mTxtNome;
    private EditText mTxtEmail;
    private EditText mTxtSenha;
    private Button mBtnCriar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_conta);

        mTxtNome = findViewById(R.id.criarConta_edit_nome);
        mTxtEmail = findViewById(R.id.criarConta_edit_email);
        mTxtSenha = findViewById(R.id.criarConta_edit_senha);
        mBtnCriar = findViewById(R.id.criarConta_button_criar);

        mBtnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mBtnCriar.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(CriarContaActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.criarConta_criando));
        progressDialog.show();

        String name = mTxtNome.getText().toString();
        final String email = mTxtEmail.getText().toString();
        final String password = mTxtSenha.getText().toString();

        VolleySingleton.getInstance(this).postRegister(new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(response);
                    response = jsonObject.getString("token");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                SharedPrefManager.getInstance(getApplicationContext()).userLogin(new Usuario(email, password, response));
                setResult(RESULT_OK);
                startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error == null || error.networkResponse == null) {
                    return;
                }
                progressDialog.dismiss();
                mBtnCriar.setEnabled(true);
                //new String(error.networkResponse.data,"UTF-8"));  //opcional pegar mensagem de erro da resposta

                if (error.networkResponse.statusCode == HttpURLConnection.HTTP_CONFLICT) {
                    mTxtEmail.setError(getString(R.string.criarConta_errorEmail));
                    mTxtEmail.requestFocus();
                }
            }
        },name, email, password);
    }
        /*new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);*/

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.loginRT_login_falhou), Toast.LENGTH_LONG).show();
        mBtnCriar.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mTxtNome.getText().toString();
        String email = mTxtEmail.getText().toString();
        String password = mTxtSenha.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mTxtNome.setError(getString(R.string.criarConta_erroNome));
            valid = false;
        } else {
            mTxtNome.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mTxtEmail.setError(getString(R.string.loginRT_erro_email));
            valid = false;
        } else {
            mTxtEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6 || password.length() > 10) {
            mTxtSenha.setError(getString(R.string.loginRT_erro_senha));
            valid = false;
        } else {
            mTxtSenha.setError(null);
        }

        return valid;
    }
}
