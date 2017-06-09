package controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dao.AlumnDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import helper.JSONParser;
import model.Alumn;

public class JSONParserController extends Activity {

    private ProgressDialog pDialog;
    AlumnDao alumnDao;
    List<Alumn> alumnList = new ArrayList<Alumn>();
    private static String url = "http://api.androidhive.info/contacts/";

    ArrayList<HashMap<String, String>> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = new ArrayList<>();
        alumnDao = AlumnDao.getInstance(getApplicationContext());

        alumnList = alumnDao.getAllAlumns();

        for(int aux = 0; aux < alumnList.size(); aux ++) {
            Log.d("NOME : ",alumnList.get(aux).getNameAlumn());
            Log.d("ID: ",String.valueOf(alumnList.get(aux).getIdAlumn()));
            Log.d("REGISTRO: ",String.valueOf(alumnList.get(aux).getRegistryAlumn()));
        }

        new GetAlumns().execute();
        new GetParents().execute();
    }

    private class GetAlumns extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(JSONParserController.this);
            pDialog.show();
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

                        alumn.setNameAlumn(alumnsJson.getString("name"));
                        alumn.setRegistryAlumn(123);

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
                //Log.e(TAG, "Couldn't get json from server.");
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
        }
    }

    private class GetParents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(JSONParserController.this);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();
            String jsonStr = httpHandlerHelper.makeServiceCall(url);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("contacts");

                    // looping through All Contacts
                    for (int aux = 0; aux < contacts.length(); aux ++) {
                        JSONObject c = contacts.getJSONObject(aux);

                        Alumn alumn = new Alumn();


                        alumn.setNameAlumn("VICTOR");
                        alumn.setRegistryAlumn(42);

                        alumnDao.insertAlumn(alumn);

                        String id = c.getString("id");
                        String name = c.getString("name");
                        String email = c.getString("email");
                        String address = c.getString("address");
                        String gender = c.getString("gender");

                        // Phone node is JSON Object
                        JSONObject phone = c.getJSONObject("phone");
                        String mobile = phone.getString("mobile");
                        String home = phone.getString("home");
                        String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("id", id);
                        contact.put("name", name);
                        contact.put("email", email);
                        contact.put("mobile", mobile);

                        // adding contact to contact list
                        contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    //Log.e(TAG, "Json parsing error: " + e.getMessage());
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
                //Log.e(TAG, "Couldn't get json from server.");
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
        }
    }
}
