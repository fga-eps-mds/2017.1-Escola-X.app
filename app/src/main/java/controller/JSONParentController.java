package controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import dao.AlumnDao;
import dao.NotificationDao;
import dao.ParentDao;
import dao.StrikeDao;
import dao.SuspensionDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import model.Alumn;
import model.Notification;
import model.Parent;
import model.Strike;
import model.Suspension;

public class JSONParentController extends Activity {

    private ProgressDialog pDialog;

    ParentDao parentDao;
    AlumnDao alumnDao;
    NotificationDao notificationDao;
    StrikeDao strikeDao;
    SuspensionDao suspensionDao;

    private String urlParents = "http://escolax.herokuapp.com/api/parents";
    private String urlAlumns = "http://escolax.herokuapp.com/api/alumns";
    private String urlNotifications =
            "http://escolax.herokuapp.com/api/notifications";
    private String urlStrike = "http://escolax.herokuapp.com/api/strikes";
    private String urlSuspension = "http://escolax.herokuapp.com/api/suspensions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentDao = ParentDao.getInstance(getApplicationContext());
        alumnDao = AlumnDao.getInstance(getApplicationContext());
        notificationDao = NotificationDao.getInstance(getApplicationContext());
        strikeDao = StrikeDao.getInstance(getApplicationContext());
        suspensionDao = SuspensionDao.getInstance(getApplicationContext());

        new GetDatas().execute();
    }

    private class GetDatas extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(JSONParentController.this);
            pDialog.setMessage("Por favor aguarde...");
            pDialog.setCancelable(false);
//            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<Parent> parentList = new ArrayList<Parent>();
            List<Alumn> alumnList = new ArrayList<Alumn>();

            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();

            String jsonParent = httpHandlerHelper.makeServiceCall(urlParents);
            String jsonAlumn = httpHandlerHelper.makeServiceCall(urlAlumns);
            String jsonNotification = httpHandlerHelper.makeServiceCall(urlNotifications);
            String jsonStrike = httpHandlerHelper.makeServiceCall(urlStrike);
            String jsonSuspension = httpHandlerHelper.makeServiceCall(urlSuspension);

            Parent parent = new Parent();
            Alumn alumn = new Alumn();
            Notification notification = new Notification();
            Strike strike = new Strike();
            Suspension suspension = new Suspension();

            parent.setIdParent(1);
            parent.setName("Victor Pai");
            parent.setPhone("61983104981");
            parentDao.insertParent(parent);
            parentList.add(parent);

            alumn.setIdAlumn(1);
            alumn.setName("Victor");
            alumn.setRegistry(123456);
            alumn.setIdParent(1);
            alumnList.add(alumn);
            alumnDao.insertAlumn(alumn);

            parent.setIdParent(2);
            parent.setName("Matheus Batista");
            parent.setPhone("61985027449");
            parentList.add(parent);
            parentDao.insertParent(parent);

            alumn.setIdAlumn(2);
            alumn.setName("Matheus");
            alumn.setRegistry(12345);
            alumn.setIdParent(2);
            alumnList.add(alumn);
            alumnDao.insertAlumn(alumn);

            parent.setIdParent(3);
            parent.setName("Princesa");
            parent.setPhone("61991907175");
            parentList.add(parent);
            parentDao.insertParent(parent);

            alumn.setIdAlumn(3);
            alumn.setName("Pedrinho");
            alumn.setRegistry(54321);
            alumn.setIdParent(3);
            alumnList.add(alumn);
            alumnDao.insertAlumn(alumn);

            notification.setIdNotification(1);
            notification.setTitle("Esse é um motivo de teste");
            notification.setNotification_text("Luz que banha a noite e faz o sol adormecer");
            notification.setNotificaton_date("2017-07-12");
            notification.setMotive("Esse é um motivo de teste");
            notificationDao.insertNotification(notification);

            suspension.setIdSuspension(1);
            suspension.setDescription("I'M GOING BACK TO THE START");
            suspension.setQuantity_days(42);
            suspension.setTitle("The Scientist");
            suspension.setDateSuspension("2017-07-12");
            suspension.setIdAlumn(2);

            suspensionDao.insertSuspension(suspension);

            strike.setIdStrike(2);
            strike.setDescription_strike("FALTA APENAS ALGUNS DETALHES");
            strike.setDate_strike("2017-07-12");
            strike.setIdAlumn(1);
            strikeDao.insertStrike(strike);


            /*if (jsonParent != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonParent);
                    JSONArray parents = jsonObj.getJSONArray("parents");

                    for (int aux = 0; aux < parents.length(); aux++) {
                        JSONObject parentsJSONObject = parents.getJSONObject(aux);

                        Parent parent = new Parent();

                        parent.setIdParent(Integer.parseInt(parentsJSONObject.getString("id")));
                        parent.setName(parentsJSONObject.getString("name"));
                        parent.setPhone(parentsJSONObject.getString("phone"));

                        parentList.add(parent);
                        parentDao.syncronParent(parentList);
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

            if (jsonAlumn != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonAlumn);
                    JSONArray alumns = jsonObj.getJSONArray("alumns");

                    for (int aux = 0; aux < alumns.length(); aux++) {
                        JSONObject alumnsJSONObject = alumns.getJSONObject(aux);

                        Alumn alumn = new Alumn();

                        alumn.setIdAlumn(Integer.parseInt(alumnsJSONObject.getString("id")));
                        alumn.setName(alumnsJSONObject.getString("name"));
                        alumn.setRegistry(Integer.parseInt(alumnsJSONObject.getString("registry")));

                        JSONObject parentJSONObject = alumnsJSONObject.getJSONObject("parent");
                        alumn.setIdParent(Integer.parseInt(parentJSONObject.getString("id")));

                        //ææalumnList.add(alumn);
                        alumnDao.insertAlumn(alumn);
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

            if (jsonStrike != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrike);
                    JSONArray strikes = jsonObj.getJSONArray("strikes");

                    for (int aux = 0; aux < strikes.length(); aux++) {
                        JSONObject strikesJSONObject = strikes.getJSONObject(aux);

                        Strike strike = new Strike();

                        strike.setIdStrike(Integer.parseInt(strikesJSONObject.getString("id")));
                        strike.setDescription_strike(strikesJSONObject.getString(
                                "description_strike"));
                        strike.setDate_strike(strikesJSONObject.getString("date_strike"));

                        JSONObject alumnJSONObject = strikesJSONObject.getJSONObject("alumn");
                        strike.setIdAlumn(Integer.parseInt(alumnJSONObject.getString("id")));

                        strikeDao.insertStrike(strike);
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

            if (jsonSuspension != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonSuspension);
                    JSONArray suspensions = jsonObj.getJSONArray("suspensions");

                    for (int aux = 0; aux < suspensions.length(); aux++) {
                        JSONObject suspensionsJSONObject = suspensions.getJSONObject(aux);

                        Suspension suspension = new Suspension();

                        suspension.setIdSuspension(Integer.parseInt(
                                suspensionsJSONObject.getString("id")));
                        suspension.setDescription(suspensionsJSONObject.getString("description"));
                        suspension.setQuantity_days(Integer.parseInt(
                                suspensionsJSONObject.getString("quantity_days")));
                        suspension.setTitle(suspensionsJSONObject.getString("title"));

                        JSONObject alumnJSONObject = suspensionsJSONObject.getJSONObject("alumn");
                        suspension.setIdAlumn(Integer.parseInt(alumnJSONObject.getString("id")));

                        suspensionDao.insertSuspension(suspension);
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
            }*/
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent intent = new Intent(getApplicationContext(), SMSActivity.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }
}
