package fr.athanase.deezer.coroutine

import fr.athanase.deezer.coroutine.api.ApiFactory
import fr.athanase.deezer.coroutine.dao.DeezerCoroutineDao
import fr.athanase.deezer.model.realm.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

fun <T> doInBackgroundAsync(method: () -> T): Deferred<T> {
    return CoroutineScope(Dispatchers.IO).async {
        return@async method()
    }
}

class DeezerManager {
    companion object {
        suspend fun fetchUser(id: Int): User? {
            val user = ApiFactory.deezerUser.getUserAsync(id).await()
            if (user.isSuccessful) {
                user.body()?.let {
                    val realmUser = User(it)
                    return DeezerCoroutineDao.saveUserAsync(realmUser).await()
                }
            }
            else {
                throw Exception("API error")
            }
            throw Exception("Failed to fetch")
        }
    }
}