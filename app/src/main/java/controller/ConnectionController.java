package controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import escola_x.escola_x.R;

public class ConnectionController extends Activity {

    Button tryAgain;
    TextView tryAgainMessage;
    String message;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        tryAgain = (Button) findViewById(R.id.tryAgain);

        message = "\t Oops.. Não foi encontrada conexão com a internet. Ligue a rede Wifi ou " +
                    "verifique se a mesma está funcionando.";

        tryAgainMessage = (TextView) findViewById(R.id.jsonSMS);
        tryAgainMessage.setText(message);

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                existsConnection();
            }
        });
    }

    private void existsConnection() {

        if(verificConnection() == true) {
            Intent intent = new Intent(getApplicationContext(), ParentController.class);
            startActivityForResult(intent, 0);
            finish();
        } else {
            message = "\t Ainda não existe conexão com a internet. Tente novamente " +
            "após conseguir conexão com a mesma.";
            tryAgainMessage.setText(message);
        }
    }

    private boolean verificConnection() {

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
}
