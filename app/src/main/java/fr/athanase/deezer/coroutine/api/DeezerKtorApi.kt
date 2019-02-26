package fr.athanase.deezer.coroutine.api

import fr.athanase.deezer.model.realm.User
import io.ktor.client.HttpClient
import io.ktor.client.features.ExpectSuccess
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.request.get
import io.ktor.http.takeFrom

class DeezerKtorApi {
    companion object {
        const val endPoint = "http://api.deezer.com/"

        private val client = HttpClient {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
            install(ExpectSuccess)
        }

        suspend fun getUser(id: Int): User = client.get {
            url {
                takeFrom(endPoint)
                path("user/", "$id")
            }
        }
    }
}