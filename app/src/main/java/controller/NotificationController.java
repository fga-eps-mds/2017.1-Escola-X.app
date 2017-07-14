package controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.NotificationDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import model.Notification;

public class NotificationController extends Activity {

    private ProgressDialog pDialog;

    NotificationDao notificationDao;
    TextView notificationTextView;

    private String urlNotifications = "http://escolax.herokuapp.com/api/notifications";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        notificationDao = NotificationDao.getInstance(getApplicationContext());

        String message = "\t Por Favor aguarde enquanto estamos atualizando seu banco de dados" +
                " em relação as notificações gerais. Pode ser que demore um pouco, então pedimos " +
                "que não feche o aplicativo";

        notificationTextView = (TextView) findViewById(R.id.jsonSMS);
        notificationTextView.setText(message);

        new GetNotifications().execute();
    }

    private class GetNotifications extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(NotificationController.this);
            pDialog.setMessage("Por favor aguarde...");
            pDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();

            String jsonNotification = httpHandlerHelper.makeServiceCall(urlNotifications);

            if (jsonNotification != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonNotification);
                    JSONArray notifications = jsonObj.getJSONArray("notifications");

                    for (int aux = 0; aux < notifications.length(); aux++) {
                        JSONObject notificationsJSONObject = notifications.getJSONObject(aux);

                        Notification notification = new Notification();

                        notification.setIdNotification(Integer.parseInt(
                                notificationsJSONObject.getString("id")));
                        notification.setTitle(notificationsJSONObject.getString("title"));
                        notification.setNotification_text(
                                notificationsJSONObject.getString("notification_text"));
                        notification.setNotificaton_date(
                                notificationsJSONObject.getString("notification_date"));
                        notification.setMotive(notificationsJSONObject.getString("motive"));

                        notificationDao.insertNotification(notification);
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent intent = new Intent(getApplicationContext(), SuspensionController.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }
}
