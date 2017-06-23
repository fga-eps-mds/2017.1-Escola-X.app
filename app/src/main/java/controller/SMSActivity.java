package controller;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.util.List;

import dao.NotificationDao;
import dao.StrikeDao;
import dao.SuspensionDao;
import escola_x.escola_x.R;
import model.Notification;
import model.ParentAlumn;
import model.Strike;
import model.Suspension;

public class SMSActivity extends Activity {

    StrikeDao strikeDao;
    NotificationDao notificationDao;
    SuspensionDao suspensionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        strikeDao = StrikeDao.getInstance(getApplicationContext());
        notificationDao = NotificationDao.getInstance(getApplicationContext());
        suspensionDao = SuspensionDao.getInstance(getApplicationContext());

        sendMessageStrike();
        sendMessageNotification();
        sendMessageSuspension();
    }

    private void sendMessageStrike(){

        List<ParentAlumn> parentAlumnList = strikeDao.getParentAlumnStrike();
        for(int aux = 0; aux < parentAlumnList.size(); aux ++) {
            Strike strike = new Strike();

            strike.setIdStrike(parentAlumnList.get(aux).getIdStrike());

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(parentAlumnList.get(aux).getPhoneParent(), null,
                        "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                        "o aluno " + parentAlumnList.get(aux).getNameAlumn() +
                        ", foi advertido por " + parentAlumnList.get(aux).getDescriptionStrike() +
                        ". \n Caso queira mais detalhes, compareça à escola.", null, null);
            strikeDao.deleteStrike(strike);
        }
    }

    private void sendMessageNotification() {

        List<ParentAlumn> parentAlumnList = notificationDao.getNotification();

        for(int aux = 0; aux < parentAlumnList.size(); aux ++) {
            Notification notification = new Notification();

            notification.setIdNotification(parentAlumnList.get(aux).getIdNotification());

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(parentAlumnList.get(aux).getPhoneParent(), null,
                        "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                        ", haverá, no dia " + parentAlumnList.get(aux).getNotificationDate() +
                        ", " + parentAlumnList.get(aux).getNotificationText() + ".", null, null);
            notificationDao.deleteNotification(notification);
        }
    }

    private void sendMessageSuspension() {

        List<ParentAlumn> parentAlumnList = suspensionDao.getParentAlumnSuspension();

        for(int aux = 0; aux < parentAlumnList.size(); aux ++) {
            Suspension suspension = new Suspension();

            suspension.setIdSuspension(parentAlumnList.get(aux).getIdSuspension());

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(parentAlumnList.get(aux).getPhoneParent(), null,
                    "Caro(a) " + parentAlumnList.get(aux).getNameParent() +
                            "o aluno " + parentAlumnList.get(aux).getNameAlumn() +
                            ", foi suspenso por " + parentAlumnList.get(aux).getQuantityDays() +
                            ". \n Motivo: " + parentAlumnList.get(aux).getDescription() + "." +
                            "\n Caso queira mais detalhes compareça à escola", null, null);
            suspensionDao.deleteSuspension(suspension);
        }
    }
}