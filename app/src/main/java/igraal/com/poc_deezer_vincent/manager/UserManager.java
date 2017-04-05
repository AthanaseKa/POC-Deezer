package igraal.com.poc_deezer_vincent.manager;

import igraal.com.poc_deezer_vincent.database.RealmManager;
import igraal.com.poc_deezer_vincent.object.User;
import igraal.com.poc_deezer_vincent.service.DeezerService;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.Observable;


/**
 * Created by vincent on 04/04/2017.
 */

public class UserManager {

    private final String URL = "http://api.deezer.com/";
    private static UserManager instance;
    private Retrofit retrofit;
    private DeezerService service;
    private RealmManager realmManager;

    private UserManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        service = retrofit.create(DeezerService.class);
        realmManager = RealmManager.getInstance();
    }

    public Observable<User> getUser(String input) {
        return service.getUser(input)
                .flatMap(user -> realmManager.insertUser(user));
    }

    public static UserManager getInstance() {
        if (instance != null)
            return instance;
        else {
            instance = new UserManager();
            return instance;
        }
    }
}
