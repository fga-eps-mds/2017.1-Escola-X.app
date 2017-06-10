package retrofit;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
/**
 * Created by matheusss03 on 10/06/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class RetrofitInit {

    private final Retrofit retrofit;

    public RetrofitInit(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.13:3000/api/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public AlumnService getAlumnService() {
        return retrofit.create(AlumnService.class);
    }
}
