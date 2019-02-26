package fr.athanase.deezer.coroutine.dao

import fr.athanase.deezer.coroutine.doInBackgroundAsync
import fr.athanase.deezer.model.realm.User
import io.realm.Realm
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class DeezerCoroutineDao {
    companion object {
        internal fun saveUserSync(user: User): User? {
            Realm.getDefaultInstance().executeTransaction {
                it.copyToRealmOrUpdate(user)
            }
            return user
        }

        fun saveUserAsync(user: User): Deferred<User?> = doInBackgroundAsync {
            Realm.getDefaultInstance().executeTransaction {
                it.copyToRealmOrUpdate(user)
            }
            return@doInBackgroundAsync user
        }

        suspend fun saveUser(user: User): User? = CoroutineScope(Dispatchers.IO).async {
            Realm.getDefaultInstance().executeTransaction {
                it.copyToRealmOrUpdate(user)
            }
            return@async user
        }.await()
    }
}