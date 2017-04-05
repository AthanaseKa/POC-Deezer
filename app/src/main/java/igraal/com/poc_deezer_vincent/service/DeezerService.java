package igraal.com.poc_deezer_vincent.service;


import igraal.com.poc_deezer_vincent.object.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vincent on 03/04/2017.
 */

public interface DeezerService {
    @GET("user/{user}")
    Observable<User> getUser(@Path("user") String user);
}
