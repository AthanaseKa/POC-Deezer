package fr.athanase.deezer.model.realm

import fr.athanase.deezer.model.json.PlaylistJson
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Playlist: RealmObject {
    @PrimaryKey
    var id: Long = 0
    var title: String = ""
    var picture: String = ""
    var nbTracks: Int = 0
    var description: String = ""
    var creatorName: String = ""

    var titleRealmList: RealmList<Title> = RealmList()

    constructor()

    constructor(playlist: PlaylistJson) : this() {
        this.id = playlist.id
        this.title = playlist.title?.let { playlist.title } ?: ""
        this.picture = playlist.picture?.let { playlist.picture } ?: ""
        this.nbTracks = playlist.nb_tracks
        this.description = playlist.description?.let { playlist.description } ?: ""
        this.creatorName = playlist.creator?.name?.let { playlist.creator?.name } ?: ""

        val list = playlist.tracks?.data?.let { playlist.tracks?.data } ?: ArrayList()
        for (i in list.indices) {
            val realmTitle = Title(list[i])
            this.titleRealmList.add(realmTitle)
        }
    }
}
