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

import dao.StrikeDao;
import escola_x.escola_x.R;
import helper.HttpHandlerHelper;
import model.Strike;

public class StrikeController extends Activity {

    private ProgressDialog pDialog;

    StrikeDao strikeDao;
    TextView suspensionTextView;
    Button tryAgain;

    private String urlStrike = "http://escolax.herokuapp.com/api/strikes";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        strikeDao = StrikeDao.getInstance(getApplicationContext());

        String message = "\t Por Favor aguarde enquanto estamos atualizando seu banco de dados" +
                " em relação as advertências. Pode ser que demore um pouco, então pedimos que " +
                "não feche o aplicativo.";

        suspensionTextView = (TextView) findViewById(R.id.jsonSMS);
        tryAgain = (Button) findViewById(R.id.tryAgain);

        suspensionTextView.setText(message);
        tryAgain.setVisibility(View.INVISIBLE);

        new GetStrike().execute();
    }

    private class GetStrike extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(StrikeController.this);
            pDialog.setMessage("Por favor aguarde...");
            pDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            HttpHandlerHelper httpHandlerHelper = new HttpHandlerHelper();

            String jsonStrike = httpHandlerHelper.makeServiceCall(urlStrike);

            if (jsonStrike != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStrike);
                    JSONArray strikes = jsonObj.getJSONArray("strikes");

                    for (int aux = 0; aux < strikes.length(); aux++) {
                        JSONObject strikesJSONObject = strikes.getJSONObject(aux);
                        JSONObject alumnJSONObject = strikesJSONObject.getJSONObject("alumn");

                        if(alumnJSONObject.getString("id").equals("null")) {

                        } else {
                            Strike strike = new Strike();

                            strike.setIdStrike(Integer.parseInt(strikesJSONObject.getString("id")));
                            strike.setDescription_strike(
                                    strikesJSONObject.getString("description_strike"));
                            strike.setDate_strike(strikesJSONObject.getString("date_strike"));


                            strike.setIdAlumn(Integer.parseInt(alumnJSONObject.getString("id")));

                            strikeDao.insertStrike(strike);
                        }
                    }
                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Problemas no JSON. Contate os responsáveis pelo app.",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"Não foi encontrada internet. " +
                                "Não será baixado os dados dos alunos " +
                                "enquanto esse problema persistir.",Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            Intent intent = new Intent(getApplicationContext(), SMSController.class);
            startActivityForResult(intent, 0);
            finish();
        }
    }
}
