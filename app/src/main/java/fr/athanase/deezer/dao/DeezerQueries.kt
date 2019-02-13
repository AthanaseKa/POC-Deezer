package fr.athanase.deezer.dao

import fr.athanase.deezer.model.realm.Playlist
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmQuery

class DeezerQueries {
    companion object {
        inline fun <reified Class: RealmObject> getByIdQuery(value: Int) : Realm.() -> RealmQuery<Class> {
            return { where(Class::class.java).equalTo("id", value) }
        }

        // this is a sample for iGraal
        fun getByUrlNameQuery(value: String) : Realm.() -> RealmQuery<Playlist> {
            return { where(Playlist::class.java).equalTo("urlName", value) }
        }
    }
}