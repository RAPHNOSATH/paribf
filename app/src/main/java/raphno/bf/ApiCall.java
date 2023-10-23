package raphno.bf;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiCall {
    @Headers("X-Auth-Token: d2f46e9afbc14aeb80509a18f155c4dd")
    @GET("/v4/matches")
    Call<DataModel>getData();
}
