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
        String email = mTxtEmail.getText().toString();
        String password = mTxtSenha.getText().toString();

        // TODO: Implement your own signup logic here.
        try {
            userRegister(name, email, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        mBtnCriar.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), getString(R.string.loginRT_login_falou), Toast.LENGTH_LONG).show();

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

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mTxtSenha.setError(getString(R.string.loginRT_erro_senha));
            valid = false;
        } else {
            mTxtSenha.setError(null);
        }

        return valid;
    }

    private void userRegister(final String nome, final String email, final String password) throws JSONException {
        //TODO SUBSTITUIR PELA CLASSE URLs
        String url = "https://jsonplaceholder.typicode.com/posts";
        VolleySingleton.getInstance(this).postRegister(url, new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                startActivity(new Intent(getApplicationContext(), PerfilActivity.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), R.string.loginRT_conect_error, Toast.LENGTH_LONG).show();
            }
        },nome, email, password);
    }
}
