package controller;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import dao.AlumnDao;
import dao.NotificationDao;
import dao.ParentDao;
import dao.StrikeDao;
import dao.SuspensionDao;
import escola_x.escola_x.R;

public class SMSActivity extends Activity {

    StrikeDao strikeDao;
    NotificationDao notificationDao;
    SuspensionDao suspensionDao;
    ParentDao parentDao;
    AlumnDao alumnDao;
    SmsManager smsManager;
    private final int NUMBER_NOTIFICATION = 2;
    private final int DATE_NOTIFICATION = 6;
    private final int NOTIFICATION_TEXT = 4;
    private final int NAME_PARENT_NOTIFICATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        strikeDao = StrikeDao.getInstance(getApplicationContext());
        notificationDao = NotificationDao.getInstance(getApplicationContext());
        suspensionDao = SuspensionDao.getInstance(getApplicationContext());
        parentDao = ParentDao.getInstance(getApplicationContext());
        alumnDao = AlumnDao.getInstance(getApplicationContext());
        smsManager = SmsManager.getDefault();



        //sendMessageStrike();
        sendMessageNotification();
        //sendMessageSuspension();
    }

    /*private void sendMessageStrike(){

        List<ParentAlumn> parentAlumnList = strikeDao.getParentAlumnStrike();
        for(int aux = 0; aux < parentAlumnList.size(); aux ++) {
            Strike strike = new Strike();

            strike.setIdStrike(parentAlumnList.get(aux).getIdStrike());

            smsManager.sendTextMessage(parentAlumnList.get(aux).getPhoneParent(), null,
                        "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                        " o aluno " + parentAlumnList.get(aux).getNameAlumn() +
                        ", foi advertido por " + parentAlumnList.get(aux).getDescriptionStrike() +
                        ". \n Caso queira mais detalhes, compareça à escola.", null, null);
            strikeDao.deleteStrike(strike);
        }
    }*/

    private void sendMessageNotification() {

        Cursor result = notificationDao.getNotification();
        long date = System.currentTimeMillis();
        SimpleDateFormat atualDate = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = atualDate.format(date);

        if (result.getCount() != 0) {
            try {
                while (result.moveToNext()) {
                    if(dateString.equals(result.getString(DATE_NOTIFICATION))) {
                        Toast.makeText(this,"TEXTO DA MENSAGEM: " + result.getString(NOTIFICATION_TEXT),Toast.LENGTH_SHORT).show();
                        /*smsManager.sendTextMessage(result.getString(NUMBER_NOTIFICATION), null,
                                "Caro(a) " + result.getString(NAME_PARENT_NOTIFICATION) +
                                      ", haverá, no dia " + result.getString(DATE_NOTIFICATION) +
                                      ", " + result.getString(NOTIFICATION_TEXT) + ".", null, null);*/
                    } else {
                        Toast.makeText(this,"DATA: " + result.getString(DATE_NOTIFICATION),Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception exception) {
                Toast.makeText(getApplicationContext(), "SMS NÃO ENVIADO", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Nenhuma notificação", Toast.LENGTH_LONG).show();
        }
    }
}


    /*private void sendMessageSuspension() {

        List<ParentAlumn> parentAlumnList = suspensionDao.getParentAlumnSuspension();

        for(int aux = 0; aux < parentAlumnList.size(); aux ++) {
            Suspension suspension = new Suspension();

            suspension.setIdSuspension(parentAlumnList.get(aux).getIdSuspension());

            smsManager.sendTextMessage(parentAlumnList.get(aux).getPhoneParent(), null,
                    "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                            "o aluno " + parentAlumnList.get(aux).getNameAlumn() +
                            ", foi suspenso por " + parentAlumnList.get(aux).getQuantityDays() +
                            ". \n Motivo: " + parentAlumnList.get(aux).getDescription() + "." +
                            "\n Caso queira mais detalhes compareça à escola", null, null);
            suspensionDao.deleteSuspension(suspension);
        }
    }*/
