package igraal.com.poc_deezer_vincent.network;

import igraal.com.poc_deezer_vincent.service.DeezerService;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by vincent on 05/04/2017.
 */

public class UserClient {

    private static UserClient instance;
    private Retrofit retrofit;
    private DeezerService service;
    private final String URL = "http://api.deezer.com/";

    private UserClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        service = retrofit.create(DeezerService.class);
    }

    public static UserClient getInstance() {
        if (instance != null)
            return instance;
        else {
            instance = new UserClient();
            return instance;
        }
    }

    public DeezerService getService() {
        return service;
    }
}
