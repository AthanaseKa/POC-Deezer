package fr.athanase.deezer.model.realm

import fr.athanase.deezer.model.json.TrackJson
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Title: RealmObject {
    @PrimaryKey
    var id: Int = 0
    var duration: Int = 0
    var title: String = ""
    var creatorName: String = ""

    constructor()

    constructor(track: TrackJson) {
        this.id = track.id
        this.duration = track.duration
        this.title = track.title?.let{ track.title } ?: ""
        this.creatorName = track.artist?.name?.let{ track.artist?.name } ?: ""
    }
}