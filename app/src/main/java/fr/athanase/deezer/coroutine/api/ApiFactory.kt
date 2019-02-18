package fr.athanase.deezer.coroutine.api

object ApiFactory{
    val deezerUser : DeezerApi = RetrofitFactory.retrofit()
            .create(DeezerApi::class.java)
}
