package fr.athanase.deezer.coroutine.api

import fr.athanase.deezer.model.json.UserJson
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DeezerApi {
    @GET("user/{userId}")
    fun getUserAsync(@Path("userId") user: Int): Deferred<Response<UserJson>>
}