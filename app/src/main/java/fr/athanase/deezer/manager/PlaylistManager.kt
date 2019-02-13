package fr.athanase.deezer.manager

import fr.athanase.deezer.model.realm.Playlist
import fr.athanase.deezer.network.client.PlaylistClient
import rx.Observable

class PlaylistManager {
    companion object {
        fun getPlaylistById(playlistId: Long): Observable<Playlist> {
            return getClient().getPlaylist(playlistId)
                    .flatMap {
                        playlistJson -> Observable.just(Playlist(playlistJson))
                    }
        }

        fun getClient(): PlaylistClient.Companion {
            return PlaylistClient
        }
    }
}