package api;

import retrofit.AlumnSync;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AlumnService {

    @GET("alumns")
    Call<AlumnSync> list();
}
