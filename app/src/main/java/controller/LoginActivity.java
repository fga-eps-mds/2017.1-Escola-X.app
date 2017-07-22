package controller;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.*;
import escola_x.escola_x.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final int MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE = 0;
    private final int MINIMUM_CARACTERE = 4;
    private final int MAXIMUM_CARACTERE = 10;

    @BindView(R.id.input_registry)
    EditText _registryText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        sendSMSMessage();
        permission();
        readPhoneState();
        _loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    protected void sendSMSMessage() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    protected void permission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    protected void readPhoneState() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        MY_PERMISSION_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Autenticando...");
        progressDialog.show();

        String registry = _registryText.getText().toString();
        String password = _passwordText.getText().toString();

        if (registry.equals("123456") && password.equals("12345678")) {

            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            onLoginSucess();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void onLoginSucess() {
        _loginButton.setEnabled(true);

        Intent jsonParserController = new Intent();
        jsonParserController.setClass(getBaseContext(), ParentController.class);
        startActivity(jsonParserController);
        finish();
    }

    private void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login falhou", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    private boolean validate() {
        boolean valid = true;

        String registry = _registryText.getText().toString();
        String password = _passwordText.getText().toString();

        if (registry.isEmpty()) {
            _registryText.setError("Coloque um registro válido");
            valid = false;
        } else {
            _registryText.setError(null);
        }

        if (password.isEmpty() ||
                password.length() < MINIMUM_CARACTERE ||
                password.length() > MAXIMUM_CARACTERE) {
            _passwordText.setError("Senha entre 4 e 10 caracteres");
            valid = false;
        } else {
            _passwordText.setError(null);
        }
        return valid;
    }

    public boolean verificConnection() {

        boolean connection = true;
        ConnectivityManager conectivtyManager = (ConnectivityManager) getSystemService(
                                                                    Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            connection = true;
        } else {
            connection = false;
        }
        return connection;
    }

    private void loginConnection () {

        if(verificConnection() == true) {
            login();
        } else {
            Intent intent = new Intent(getApplicationContext(), AlumnController.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }
}