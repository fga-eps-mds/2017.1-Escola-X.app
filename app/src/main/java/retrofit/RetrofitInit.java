package retrofit;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import api.AlumnService;
import api.ParentService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrofitInit {

    private final Retrofit retrofit;

    public RetrofitInit(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
//        client.connectTimeout(60, TimeUnit.SECONDS);
//        client.readTimeout(60, TimeUnit.SECONDS);
//        client.writeTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("192.168.43.36:3000/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public AlumnService getAlumnService() {
        return retrofit.create(AlumnService.class);
    }

    public ParentService getParentService(){
        return retrofit.create(ParentService.class);
    }
}
