package fr.athanase.deezer.manager

import fr.athanase.deezer.dao.DeezerDao
import fr.athanase.deezer.dao.DeezerQueries
import fr.athanase.deezer.model.json.PlaylistJson
import fr.athanase.deezer.model.realm.Playlist
import fr.athanase.deezer.model.realm.User
import fr.athanase.deezer.network.client.UserClient
import io.realm.RealmList
import rx.Observable

class UserManager {
    companion object {
        fun getUser(userId: Int): Observable<User> {
            val user = DeezerDao.getSingleItemTransaction2 {
                DeezerQueries.getByIdQuery<User>(userId).invoke(this)
            }

            return user.flatMap { realmUser ->
                if (realmUser == null) {
                    UserClient.getUser(userId).filter { userJson1 -> userJson1.name != null }
                            .flatMap<User> { userJson1 -> DeezerDao.saveSingleItemTransaction2(User(userJson1)) }
                } else {
                    Observable.just(realmUser)
                }
            }
        }

        fun getUserById(userId: Int): Observable<User> {
            return DeezerDao.getSingleItemTransaction2 {
                DeezerQueries.getByIdQuery<User>(userId).invoke(this)
            }
        }

        fun getUserPlaylists(userId: Int): Observable<RealmList<Playlist>> {
            return UserClient.getUserPlaylist(userId)
                    .flatMap { playlistListServiceResponse -> formatUserPlaylist(playlistListServiceResponse.data) }
                    .flatMap { playlists -> DeezerDao.updateItemTransaction2(DeezerQueries.getByIdQuery<User>(userId)) {
                        it?.playlists?.addAll(playlists)
                        return@updateItemTransaction2 it
                    }}
                    .flatMap { user ->
                        if (user == null) {
                            return@flatMap Observable.empty<RealmList<Playlist>>()
                        }
                        return@flatMap Observable.just(user.playlists)
                    }
        }
        //.flatMap { playlists -> RealmManager.getInstance().updateUserPlaylistData(userId, playlists) }

        fun getNextPlaylists(index: Int, userId: Int): Observable<RealmList<Playlist>> {
            return UserClient.getNextUserPlaylist(userId, index)
                    .flatMap { playlistListServiceResponse -> formatUserPlaylist(playlistListServiceResponse.data) }
        }

        fun formatUserPlaylist(playlistList: List<PlaylistJson>?): Observable<RealmList<Playlist>> {
            val playlists = playlistList?.let { playlistList } ?: RealmList()
            val myList = RealmList<Playlist>()
            for (i in playlists.indices) {
                val realmPlaylist = Playlist(playlists[i])
                myList.add(realmPlaylist)
            }
            return Observable.just(myList)
        }
    }
}