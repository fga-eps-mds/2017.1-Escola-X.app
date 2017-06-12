package controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

import dao.AlumnDao;
import escola_x.escola_x.R;
import model.Alumn;
import retrofit.AlumnSync;
import retrofit.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSActivity extends AppCompatActivity{

    TextView sms;
    AlumnDao alumnDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        alumnDao = AlumnDao.getInstance(this);

    }

    private void carregaList(Context applicationContext) {
        alumnDao = AlumnDao.getInstance(getApplicationContext());

        List<Alumn> alumns = alumnDao.getAllAlumns();

        for(int aux = 0;aux < alumns.size(); aux ++) {
            Log.d("NOME: ",alumns.get(aux).getName());
        }
        Log.d("Tamanho = ", String.valueOf(alumns.size()));

        for (Alumn alumn :
                alumns) {
            Log.i("carrega List", String.valueOf(alumn.getIdAlumn()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        alumnDao = AlumnDao.getInstance(getApplicationContext());
        Call<AlumnSync> call = new RetrofitInit().getAlumnService().list();

        call.enqueue(new Callback<AlumnSync>() {
            @Override
            public void onResponse(Call<AlumnSync> call, Response<AlumnSync> response) {
                AlumnSync alumnSync = response.body();
                //alumnDao.syncronAlumn(alumnSync.getAlumns());
                //carregaList(getApplicationContext());

            }

            @Override
            public void onFailure(Call<AlumnSync> call, Throwable t) {
                Log.e("Falha chamada", t.getMessage());
            }
            });

        carregaList(getApplicationContext());
    }
}
