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

import dao.AlumnDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import model.Alumn;

public class AlumnController extends Activity {

    private ProgressDialog pDialog;

    AlumnDao alumnDao;
    TextView alumnTextView;

    private String urlAlumns = "http://escolax.herokuapp.com/api/alumns";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        alumnDao = AlumnDao.getInstance(getApplicationContext());

        String message = "\t Por Favor aguarde enquanto estamos atualizando seu banco de dados" +
                " em relação aos alunos. Pode ser que demore um pouco, mas por favor" +
                " não feche o aplicativo";

        alumnTextView = (TextView) findViewById(R.id.jsonSMS);
        alumnTextView.setText(message);

        new GetAlumns().execute();
    }

    private class GetAlumns extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(AlumnController.this);
            pDialog.setMessage("Por favor aguarde...");
            pDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();

            String jsonAlumn = httpHandlerHelper.makeServiceCall(urlAlumns);

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

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent intent = new Intent(getApplicationContext(), NotificationController.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }
}
