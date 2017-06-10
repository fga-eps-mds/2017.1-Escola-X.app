package controller;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by matheusss03 on 10/06/17.
 */

public interface AlumnService {

    @GET("alumn")
    Call<AlumnSync> list();
}
