package igraal.com.poc_deezer_vincent.manager;

import igraal.com.poc_deezer_vincent.object.User;
import igraal.com.poc_deezer_vincent.service.DeezerService;
import io.realm.Realm;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by vincent on 04/04/2017.
 */

public class UserManager {

    private final String URL = "http://api.deezer.com/";
    private static UserManager instance;
    private Retrofit retrofit;
    private DeezerService service;

    private UserManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        service = retrofit.create(DeezerService.class);
    }

    public void getUser(String input, Realm realm) {
        service.getUser(input)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user1 -> {
                            insertUser(realm, user1);
                        },
                        error -> {
                            Timber.e(error, error.getMessage());
                        });
    }

    public static UserManager getInstance() {
        if (instance != null)
            return instance;
        else {
            instance = new UserManager();
            return instance;
        }
    }

    private void insertUser(Realm realm, User user) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(user);
        /*myUser.setName(user.getName());
        myUser.setCountry(user.getCountry());
        myUser.setId(user.getId());
        myUser.setPicture(user.getPicture());*/
        realm.commitTransaction();
    }
}
