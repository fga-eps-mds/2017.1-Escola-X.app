package api;

import retrofit.ParentSync;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ParentService {

    @GET("aluno")
    Call<ParentSync> list();
}
