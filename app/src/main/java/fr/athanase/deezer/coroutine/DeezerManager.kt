package fr.athanase.deezer.coroutine

import fr.athanase.deezer.coroutine.api.ApiFactory
import fr.athanase.deezer.coroutine.api.DeezerKtorApi
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
            return DeezerKtorApi.getUser(id).let {
                return@let DeezerCoroutineDao.saveUserSync(it)
            }
        }

        suspend fun fetchUser2(id: Int): User? {
            return ApiFactory.deezerUser.getUserAsync(id).let {
                return@let DeezerCoroutineDao.saveUserSync(it)
            }
        }
    }
}