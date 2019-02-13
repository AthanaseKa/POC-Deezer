package fr.athanase.deezer.network.client

import fr.athanase.deezer.model.json.PlaylistJson
import fr.athanase.deezer.network.service.PlaylistService
import rx.Observable

class PlaylistClient {
    companion object {
        fun getPlaylist(playlistId: Long) : Observable<PlaylistJson> {
            return PlaylistService.instance.getPlaylist(playlistId)
        }
    }
}
