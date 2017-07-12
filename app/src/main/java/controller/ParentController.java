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

import java.util.ArrayList;
import java.util.List;

import dao.ParentDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import model.Parent;

public class ParentController extends Activity {

    private ProgressDialog pDialog;

    ParentDao parentDao;
    TextView parentTextView;

    private String urlParents = "http://escolax.herokuapp.com/api/parents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        parentDao = ParentDao.getInstance(getApplicationContext());

        String message = "\t Por Favor aguarde enquanto estamos atualizando seu banco de dados" +
                            " em relação aos parentes. Pode ser que demore um pouco, mas por favor" +
                            " não feche o aplicativo";

        parentTextView = (TextView) findViewById(R.id.jsonSMS);
        parentTextView.setText(message);

        new GetParents().execute();
    }

    private class GetParents extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ParentController.this);
            pDialog.setMessage("Por favor aguarde...");
            pDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<Parent> parentList = new ArrayList<Parent>();

            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();

            String jsonParent = httpHandlerHelper.makeServiceCall(urlParents);

            if (jsonParent != null) {
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

            /*

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

            Intent intent = new Intent(getApplicationContext(), AlumnController.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }
}
