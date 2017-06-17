package controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import dao.ParentDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import model.Parent;

public class JSONParserController extends Activity {

    private ProgressDialog pDialog;

    ParentDao parentDao;
    private static String urlParents = "http://murmuring-mountain-86195.herokuapp.com/api/parents";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentDao = ParentDao.getInstance(getApplicationContext());
        new GetParents().execute();

        List<Parent> parentList = new ArrayList<Parent>();
        parentList = parentDao.getAllParents();

        for (int aux = 0; aux < parentList.size(); aux ++) {
            Log.d("ID DO PAI: ",String.valueOf(parentList.get(aux).getIdParent()));
            Log.d("NOME DO PAI: ",parentList.get(aux).getName());
            Log.d("PHONE DO PAI: ",parentList.get(aux).getPhone());
        }
    }

    private class GetParents extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(JSONParserController.this);
            pDialog.setMessage("Por favor aguarde...");
            pDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            List<Parent> parentList = new ArrayList<Parent>();
            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();

            String jsonStr = httpHandlerHelper.makeServiceCall(urlParents);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
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
