package fr.athanase.deezer.model.realm

import fr.athanase.deezer.model.json.UserJson
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class User: RealmObject {
    @PrimaryKey
    var id: Int = 0
    var name: String = ""
    var country: String = ""
    var picture: String? = null

    var playlists: RealmList<Playlist> = RealmList()

    constructor()

    constructor(user: UserJson) {
        this.id = user.id ?: 0
        this.name = user.name?.let{ user.name } ?: ""
        this.country = user.country?.let{ user.country } ?: ""
        this.picture = user.picture
    }
}
