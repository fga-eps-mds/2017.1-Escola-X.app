package controller;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import model.Alumn;
import model.Notification;
import model.Parent;
import model.ParentAlumn;
import model.Strike;
import model.Suspension;

public class SMSController extends Activity {

    StrikeDao strikeDao;
    NotificationDao notificationDao;
    SuspensionDao suspensionDao;
    ParentDao parentDao;
    AlumnDao alumnDao;
    TextView smsTextView;
    Button tryAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        strikeDao = StrikeDao.getInstance(getApplicationContext());
        notificationDao = NotificationDao.getInstance(getApplicationContext());
        suspensionDao = SuspensionDao.getInstance(getApplicationContext());
        parentDao = ParentDao.getInstance(getApplicationContext());
        alumnDao = AlumnDao.getInstance(getApplicationContext());
        smsTextView = (TextView) findViewById(R.id.jsonSMS);
        tryAgain = (Button) findViewById(R.id.tryAgain);

        String message = "\t Os SMS's de notificações gerais, advertências e suspensões foram " +
                         "enviados aos devidos responsáveis.";
        smsTextView.setText(message);
        tryAgain.setVisibility(View.INVISIBLE);

        sendMessageStrike();
        sendMessageNotification();
        sendMessageSuspension();
        deleteParent();
        deleteAlumn();
    }

    private void sendMessageStrike(){

        List<ParentAlumn> parentAlumnList = strikeDao.getParentAlumnStrike();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = "";
        String dateStrike = "";
        String messageStrike = "";
        long date = System.currentTimeMillis();
        ArrayList<String> parentAlumns = new ArrayList<String>();

        currentDate = dateFormat.format(date);
        SmsManager smsManager = SmsManager.getDefault();

        if(parentAlumnList.size() > 0) {
            for(int aux = 0; aux < parentAlumnList.size();aux++) {

                if (parentAlumnList.get(aux).getDateStrike().equals(currentDate)) {

                    Strike strike = new Strike();

                    strike.setIdStrike(parentAlumnList.get(aux).getIdStrike());

                    dateStrike = setDate(parentAlumnList.get(aux).getDateStrike());
                    messageStrike = "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                            " o aluno " + parentAlumnList.get(aux).getNameAlumn() +
                            ", foi advertido por " + parentAlumnList.get(aux).getDescriptionStrike() +
                            ". Este ocorrido aconteceu no dia " + dateStrike +
                            ".\n" +
                            "Caso queira mais detalhes compareça ao CENTRO DE ENSINO MÉDIO 01 GAMA";
                    parentAlumns = smsManager.divideMessage(messageStrike);
                    smsManager.sendMultipartTextMessage(parentAlumnList.get(aux).getPhoneParent(),
                            null, parentAlumns, null, null);
                    strikeDao.deleteStrike(strike);
                }
            }
        }
    }

    private void sendMessageNotification() {

        List<ParentAlumn> parentAlumnList = notificationDao.getNotification();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = "";
        String dateNotification = "";
        String messageNotification = "";
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
                    messageNotification =
                    "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                    " , haverá, no dia " + dateNotification +
                    " o evento " + parentAlumnList.get(aux).getNotificationText() +
                    ". \n CENTRO DE ENSINO MÉDIO 01 - GAMA";
                    parentAlumns = smsManager.divideMessage(messageNotification);
                    smsManager.sendMultipartTextMessage(parentAlumnList.get(aux).getPhoneParent(),
                                                        null, parentAlumns, null, null);
                    notificationDao.deleteNotification(notification);
                }
            }
        }
    }

    private void sendMessageSuspension() {

        List<ParentAlumn> parentAlumnList = suspensionDao.getParentAlumnSuspension();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = "";
        String dateSuspension = "";
        String messageSuspension = "";
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
                    messageSuspension = "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                            " o aluno " + parentAlumnList.get(aux).getNameAlumn() +
                            ", foi suspenso por " + parentAlumnList.get(aux).getQuantityDays() +
                            "dias. \n" +
                            "Motivo: " + parentAlumnList.get(aux).getDescription() + "," +
                            " no dia " + dateSuspension + ".\n" +
                            "Caso queira mais detalhes compareça ao CENTRO DE ENSINO MÉDIO 01 GAMA";

                    parentAlumns = smsManager.divideMessage(messageSuspension);
                    smsManager.sendMultipartTextMessage(parentAlumnList.get(aux).getPhoneParent(),
                                                        null, parentAlumns, null, null);
                    suspensionDao.deleteSuspension(suspension);
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

    private void deleteParent() {

        List<Parent> parentList = parentDao.getAllParents();

        for(int aux = 0; aux < parentList.size(); aux ++) {

            Parent parent = new Parent();

            parent.setIdParent(parentList.get(aux).getIdParent());

            parentDao.deleteParent(parent);
        }
    }

    private void deleteAlumn() {

        List<Alumn> alumnList = alumnDao.getAllAlumns();

        for(int aux = 0; aux < alumnList.size(); aux ++) {

            Alumn alumn = new Alumn();

            alumn.setIdAlumn(alumnList.get(aux).getIdAlumn());

            alumnDao.deleteAlumn(alumn);
        }
    }
}