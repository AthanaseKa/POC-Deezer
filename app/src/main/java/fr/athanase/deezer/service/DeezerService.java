package fr.athanase.deezer.service;


import fr.athanase.deezer.object.jsonobject.PlaylistListServiceResponse;
import fr.athanase.deezer.object.jsonobject.PlaylistServiceResponse;
import fr.athanase.deezer.object.jsonobject.UserJson;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by vincent on 03/04/2017.
 */

public interface DeezerService {
    @GET("user/{userId}")
    Observable<UserJson> getUser(@Path("userId") int user);

    @GET("user/{userid}/playlists")
    Observable <PlaylistListServiceResponse> getUserPlaylist(@Path("userid") int userId);

    @GET("playlist/{playlistId}")
    Observable <PlaylistServiceResponse> getPlaylist(@Path("playlistId") long playlistId);

    @GET("user/{userid}/playlists")
    Observable<PlaylistListServiceResponse> getNextUserPlaylist(@Path("userid") int userId, @Query("index") int index);
}
