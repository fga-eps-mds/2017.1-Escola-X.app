package controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dao.ParentDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import helper.MaskHelper;
import model.Parent;

public class ParentController extends Activity {

    private ProgressDialog pDialog;

    ParentDao parentDao;
    TextView parentTextView;
    Button tryAgain;

    private String urlParents = "http://escolax.herokuapp.com/api/parents";
    private final int MINIMUM_NUMBERS = 10;
    private final int MAXIMUM_NUMBERS = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        parentDao = ParentDao.getInstance(getApplicationContext());
        parentTextView = (TextView) findViewById(R.id.jsonSMS);
        tryAgain = (Button) findViewById(R.id.tryAgain);

        String message = "\t Por Favor aguarde enquanto estamos atualizando seu banco de dados" +
                            " em relação aos responsáveis. Pode ser que demore um pouco, " +
                            "então pedimos que não feche o aplicativo.";
        parentTextView.setText(message);
        tryAgain.setVisibility(View.INVISIBLE);

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

            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();

            String jsonParent = httpHandlerHelper.makeServiceCall(urlParents);

            MaskHelper maskHelper = new MaskHelper();
            String regex = "[A-Z&&[^IVX]]+";

            if (jsonParent != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonParent);
                    JSONArray parents = jsonObj.getJSONArray("parents");

                    for (int aux = 0; aux < parents.length(); aux++) {
                        JSONObject parentsJSONObject = parents.getJSONObject(aux);

                        if(parentsJSONObject.getString("phone").equals("null") ||
                           parentsJSONObject.getString("phone").contains(regex)) {

                        } else {

                            if(parentsJSONObject.getString("phone").length() == MINIMUM_NUMBERS) {

                                if(parentsJSONObject.getString("id").equals("null")) {

                                } else {

                                    Parent parent = new Parent();

                                    parent.setIdParent(Integer.parseInt(
                                            parentsJSONObject.getString("id")));
                                    parent.setName(parentsJSONObject.getString("name"));
                                    parent.setPhone(maskHelper.phoneMask(
                                            parentsJSONObject.getString("phone")));

                                    parentDao.insertParent(parent);
                                }

                            } else if(parentsJSONObject.getString("phone").length() ==
                                                                                MAXIMUM_NUMBERS) {

                                if(parentsJSONObject.getString("id").equals("null")) {

                                } else {

                                    Parent parent = new Parent();

                                    parent.setIdParent(Integer.parseInt(
                                            parentsJSONObject.getString("id")));
                                    parent.setName(parentsJSONObject.getString("name"));
                                    parent.setPhone(parentsJSONObject.getString("phone"));

                                    parentDao.insertParent(parent);
                                }
                            }
                        }
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

            Intent intent = new Intent(getApplicationContext(), AlumnController.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }
}