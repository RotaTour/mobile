package br.ufrpe.projetao.rotatour;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginRotaActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText mTxtEmail;
    private EditText mTxtSenha;
    private Button mBtnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_rota);

        mTxtEmail = (EditText) findViewById(R.id.loginRT_edit_email);
        mTxtSenha = (EditText) findViewById(R.id.loginRT_edit_senha);
        mBtnLogin = (Button) findViewById(R.id.loginRT_button_login);

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

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
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
}
