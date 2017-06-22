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

import dao.NotificationDao;
import dao.StrikeDao;
import escola_x.escola_x.R;
import model.ParentAlumn;

public class SMSActivity extends Activity {

    StrikeDao strikeDao;
    NotificationDao notificationDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        strikeDao = StrikeDao.getInstance(getApplicationContext());
        notificationDao = NotificationDao.getInstance(getApplicationContext());
        Toast.makeText(this,"Tamanho = " + strikeDao.getParentAlumn().size(),Toast.LENGTH_LONG).show();
        //sendMessageStrike();
    }

    private void sendMessageStrike(){

        boolean sucess = true;
        List<ParentAlumn> parentAlumnList = strikeDao.getParentAlumn();
        for(int aux = 0; aux < parentAlumnList.size(); aux ++) {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(parentAlumnList.get(aux).getPhoneParent(), null, "Nome :" + parentAlumnList.get(aux).getNameParent(), null, null);
            Toast.makeText(getApplicationContext(), "SMS enviado para " + parentAlumnList.get(aux).getNameParent(),
                    Toast.LENGTH_LONG).show();
        }
    }


}