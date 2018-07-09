package fr.athanase.deezer.network;

import fr.athanase.deezer.service.DeezerService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by vincent on 05/04/2017.
 */

public class DeezerClient {

    private static DeezerClient instance;
    private Retrofit retrofit;
    private DeezerService service;
    private final String URL = "http://api.deezer.com/";

    private DeezerClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        service = retrofit.create(DeezerService.class);
    }

    public static DeezerClient getInstance() {
        if (instance != null)
            return instance;
        else {
            instance = new DeezerClient();
            return instance;
        }
    }

    public DeezerService getService() {
        return service;
    }
}
