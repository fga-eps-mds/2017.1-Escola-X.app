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
import dao.NotificationDao;
import dao.ParentDao;
import dao.StrikeDao;
import dao.SuspensionDao;
import escola_x.escola_x.R;
import model.Alumn;
import model.Parent;
import retrofit.AlumnSync;
import retrofit.ParentSync;
import retrofit.RetrofitInit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSActivity extends AppCompatActivity{

    AlumnDao alumnDao;
    ParentDao parentDao;
    NotificationDao notificationDao;
    SuspensionDao suspensionDao;
    StrikeDao strikeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        alumnDao = AlumnDao.getInstance(getApplicationContext());
        parentDao = ParentDao.getInstance(getApplicationContext());
        notificationDao = NotificationDao.getInstance(getApplicationContext());
        suspensionDao = SuspensionDao.getInstance(getApplicationContext());
        strikeDao = StrikeDao.getInstance(getApplicationContext());
    }

    private void carregaList() {
        alumnDao = AlumnDao.getInstance(getApplicationContext());

        List<Alumn> alumns = alumnDao.getAllAlumns();


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
        parentDao = ParentDao.getInstance(getApplicationContext());

        Call<ParentSync> callParent = new RetrofitInit().getParentService().list();
        Call<AlumnSync> call = new RetrofitInit().getAlumnService().list();

        callParent.enqueue(new Callback<ParentSync>() {
            @Override

            public void onResponse(Call<ParentSync> call, Response<ParentSync> response) {
                Log.i("Chamada do sucesso", response.message());
            }

            @Override
            public void onFailure(Call<ParentSync> call, Throwable t) {
                Log.e("Falha chamada Parent", t.getMessage());
            }
        });
//        carregaList();
    }

//        call.enqueue(new Callback<AlumnSync>() {
//            @Override
//            public void onResponse(Call<AlumnSync> call, Response<AlumnSync> response) {
//                AlumnSync alumnSync = response.body();
//                alumnDao.syncronAlumn(alumnSync.getAlumns(),);
//                carregaList();
//
//            }
//
//            @Override
//            public void onFailure(Call<AlumnSync> call, Throwable t) {
//                Log.e("Falha chamada", t.getMessage());
//            }
//            });
//
    }
