package controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import escola_x.escola_x.R;

public class ConnectionController extends Activity {

    Button tryAgain;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        tryAgain = (Button) findViewById(R.id.tryAgain);

        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
