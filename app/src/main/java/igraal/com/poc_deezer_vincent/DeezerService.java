package igraal.com.poc_deezer_vincent;

import igraal.com.poc_deezer_vincent.Object.User;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by vincent on 03/04/2017.
 */

public interface DeezerService {
    @GET("user/{user}")
    Call<User> getUser(@Path("user") String user);
}
