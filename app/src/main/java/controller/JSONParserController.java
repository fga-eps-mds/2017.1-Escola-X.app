package controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.AlumnDao;
import dao.ParentDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import model.Alumn;
import model.Parent;

public class JSONParserController extends Activity {

    private ProgressDialog pDialog;
    AlumnDao alumnDao;
    ParentDao parentDao;
    List<Alumn> alumnList = new ArrayList<Alumn>();
    private static String url = "http://api.androidhive.info/contacts/";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        alumnDao = AlumnDao.getInstance(getApplicationContext());
        parentDao = ParentDao.getInstance(getApplicationContext());

        alumnList = alumnDao.getAllAlumns();

        for(int aux = 0; aux < alumnList.size(); aux ++) {
            Log.d("NOME : ",alumnList.get(aux).getName());
            Log.d("ID: ",String.valueOf(alumnList.get(aux).getIdAlumn()));
            Log.d("REGISTRO: ",String.valueOf(alumnList.get(aux).getRegistry()));
        }

        new GetAlumns().execute();
        new GetParents().execute();
    }

    private class GetAlumns extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (alumnDao.isDbEmpty() == false) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 0);
                finish();
            } else {
                pDialog = new ProgressDialog(JSONParserController.this);
                pDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();
            String jsonStr = httpHandlerHelper.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray alumns = jsonObj.getJSONArray("contacts");

                    for (int aux = 0; aux < alumns.length(); aux ++) {
                        JSONObject alumnsJson = alumns.getJSONObject(aux);

                        Alumn alumn = new Alumn();

                        /*alumn.setIdAlumn();
                        alumn.setName();
                        alumn.setRegistry();*/

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
            //Colocar a página de login
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }

    private class GetParents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (parentDao.isDbEmpty() == false) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivityForResult(intent, 0);
                finish();
            } else {
                pDialog = new ProgressDialog(JSONParserController.this);
                pDialog.show();
            }
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();
            String jsonStr = httpHandlerHelper.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray parents = jsonObj.getJSONArray("contacts");

                    for (int aux = 0; aux < parents.length(); aux ++) {
                        JSONObject c = parents.getJSONObject(aux);

                        Parent parent = new Parent();
                        Alumn alumn = new Alumn();

                        /*parent.setIdParent();
                        parent.setNameParent();
                        parent.setPhoneParent();
                        alumn.setIdAlumn();

                        parentDao.insertParent(parent,alumn);*/
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
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }
}
