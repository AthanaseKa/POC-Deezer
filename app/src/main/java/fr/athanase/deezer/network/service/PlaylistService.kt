package fr.athanase.deezer.network.service

import fr.athanase.deezer.model.json.PlaylistJson
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

interface PlaylistService {
    companion object {
        val instance: PlaylistService by lazy {
            DeezerService().getService(PlaylistService::class.java)
        }
    }

    @GET("playlist/{playlistId}")
    fun getPlaylist(@Path("playlistId") playlistId: Long): Observable<PlaylistJson>
}
