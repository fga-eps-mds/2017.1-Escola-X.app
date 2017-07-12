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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dao.AlumnDao;
import dao.NotificationDao;
import dao.ParentDao;
import dao.StrikeDao;
import dao.SuspensionDao;
import escola_x.escola_x.R;
import model.Notification;
import model.ParentAlumn;
import model.Suspension;

public class SMSActivity extends Activity {

    StrikeDao strikeDao;
    NotificationDao notificationDao;
    SuspensionDao suspensionDao;
    ParentDao parentDao;
    AlumnDao alumnDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        strikeDao = StrikeDao.getInstance(getApplicationContext());
        notificationDao = NotificationDao.getInstance(getApplicationContext());
        suspensionDao = SuspensionDao.getInstance(getApplicationContext());
        parentDao = ParentDao.getInstance(getApplicationContext());
        alumnDao = AlumnDao.getInstance(getApplicationContext());

        //sendMessageStrike();
        sendMessageNotification();
        sendMessageSuspension();
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

        List<ParentAlumn> parentAlumnList = notificationDao.getNotification();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = "";
        String dateNotification = "";
        String message = "";
        long date = System.currentTimeMillis();
        ArrayList<String> parentAlumns = new ArrayList<String>();

        currentDate = dateFormat.format(date);
        SmsManager smsManager = SmsManager.getDefault();

        if(parentAlumnList.size() > 0) {
            for(int aux = 0; aux < parentAlumnList.size();aux++) {

                if (parentAlumnList.get(aux).getNotificationDate().equals(currentDate)) {

                    Notification notification = new Notification();

                    notification.setIdNotification(parentAlumnList.get(aux).getIdNotification());
                    dateNotification = setDate(parentAlumnList.get(aux).getNotificationDate());
                    message =
                    "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                    " , haverá, no dia " + dateNotification +
                    ", " + parentAlumnList.get(aux).getNotificationText() +
                    ". CENTRO DE ENSINO MÉDIO 01 - GAMA";
                    parentAlumns = smsManager.divideMessage(message);
                    smsManager.sendMultipartTextMessage(parentAlumnList.get(aux).getPhoneParent(),
                                                        null, parentAlumns, null, null);
                }
            }
        }
    }

    private void sendMessageSuspension() {

        List<ParentAlumn> parentAlumnList = suspensionDao.getParentAlumnSuspension();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = "";
        String dateSuspension = "";
        String message = "";
        long date = System.currentTimeMillis();
        ArrayList<String> parentAlumns = new ArrayList<String>();

        currentDate = dateFormat.format(date);
        SmsManager smsManager = SmsManager.getDefault();

        if(parentAlumnList.size() > 0) {
            for(int aux = 0; aux < parentAlumnList.size();aux++) {

                if (parentAlumnList.get(aux).getDateSuspension().equals(currentDate)) {

                    Suspension suspension = new Suspension();

                    suspension.setIdSuspension(parentAlumnList.get(aux).getIdSuspension());
                    dateSuspension = setDate(parentAlumnList.get(aux).getDateSuspension());

                    message = "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                            " o aluno " + parentAlumnList.get(aux).getNameAlumn() +
                            ", foi suspenso por " + parentAlumnList.get(aux).getQuantityDays() +
                            "dias . \n Motivo: " + parentAlumnList.get(aux).getDescription() + "," +
                            " na data: " + dateSuspension + "." +
                            "\n Caso queira mais detalhes compareça ao Centro de Ensino Médio 01 Gama";

                    parentAlumns = smsManager.divideMessage(message);
                    smsManager.sendMultipartTextMessage(parentAlumnList.get(aux).getPhoneParent(),
                                                        null, parentAlumns, null, null);
                }
            }
        }
    }

    private String setDate(String date) {

        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date myDate = null;
        try {
            myDate = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy");
        String finalDate = timeFormat.format(myDate);
        return finalDate;
    }
}