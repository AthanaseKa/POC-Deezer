package fr.athanase.deezer.network.service

import fr.athanase.deezer.model.json.PlaylistListJson
import fr.athanase.deezer.model.json.UserJson
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface UserService {
    companion object {
        val instance: UserService by lazy {
            DeezerService().getService(UserService::class.java)
        }
    }

    @GET("user/{userId}")
    fun getUser(@Path("userId") user: Int): Observable<UserJson>

    @GET("user/{userid}/playlists")
    fun getUserPlaylist(@Path("userid") userId: Int): Observable<PlaylistListJson>

    @GET("user/{userid}/playlists")
    fun getNextUserPlaylist(@Path("userid") userId: Int, @Query("index") index: Int): Observable<PlaylistListJson>
}
