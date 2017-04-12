package igraal.com.poc_deezer_vincent.service;


import igraal.com.poc_deezer_vincent.object.PlaylistServiceResponse;
import igraal.com.poc_deezer_vincent.object.User;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by vincent on 03/04/2017.
 */

public interface DeezerService {
    @GET("user/{userId}")
    Observable<User> getUser(@Path("userId") int user);

    @GET("user/{userid}/playlists")
    Observable <PlaylistServiceResponse> getUserPlaylist(@Path("userid") int userId);
}
