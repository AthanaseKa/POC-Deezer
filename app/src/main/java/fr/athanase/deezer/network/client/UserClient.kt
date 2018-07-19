package fr.athanase.deezer.network.client

import fr.athanase.deezer.model.json.PlaylistListJson
import fr.athanase.deezer.model.json.UserJson
import fr.athanase.deezer.network.service.UserService
import rx.Observable

class UserClient {
    companion object {
        fun getUser(user: Int): Observable<UserJson> {
            return UserService.instance.getUser(user)
        }

        fun getUserPlaylist(userId: Int): Observable<PlaylistListJson> {
            return UserService.instance.getUserPlaylist(userId)
        }

        fun getNextUserPlaylist(userId: Int, index: Int): Observable<PlaylistListJson> {
            return UserService.instance.getNextUserPlaylist(userId, index)
        }
    }
}