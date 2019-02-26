package fr.athanase.deezer.coroutine.api

import fr.athanase.deezer.model.realm.User
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerApi {
    @GET("user/{userId}")
    suspend fun getUserAsync(@Path("userId") user: Int): User
}