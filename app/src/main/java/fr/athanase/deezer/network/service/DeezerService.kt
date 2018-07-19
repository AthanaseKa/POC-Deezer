package fr.athanase.deezer.network.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class DeezerService {
    fun <Service> getService(`class`: Class<Service>): Service {
        return Retrofit.Builder()
                .baseUrl("http://api.deezer.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
                .create(`class`)
    }
}
