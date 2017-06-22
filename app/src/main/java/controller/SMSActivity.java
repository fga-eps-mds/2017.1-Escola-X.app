package controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.List;

import dao.StrikeDao;
import escola_x.escola_x.R;
import model.ParentAlumn;

public class SMSActivity extends Activity {

    private static final int MY_PERMISSION = 0;

    StrikeDao strikeDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        strikeDao = StrikeDao.getInstance(getApplicationContext());
        //Toast.makeText(this,"Tamanho = " + strikeDao.getParentAlumnContact().getCount(),Toast.LENGTH_LONG).show();
        //sendMessage();
    }

    /*protected void sendMessage(){
        List<ParentAlumn> parentAlumnList = strikeDao.getParentAlumn();
        for(int aux = 0; aux < parentAlumnList.size(); aux ++) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(parentAlumnList.get(aux).getPhoneParent(), null, "SE FUNCIONAR, ME DA UM TOQUE?!?!?", null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.",
                    Toast.LENGTH_LONG).show();
        }
    }*/

    /*@Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        List<ParentAlumn> parentAlumnList = strikeDao.getParentAlumn();
        switch (requestCode) {
            case MY_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    for(int aux = 0; aux < parentAlumnList.size(); aux ++) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(parentAlumnList.get(aux).getPhoneParent(), null, "SE FUNCIONAR, ME DA UM TOQUE?!?!?", null, null);
                        Toast.makeText(getApplicationContext(), "SMS sent.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }*/
}