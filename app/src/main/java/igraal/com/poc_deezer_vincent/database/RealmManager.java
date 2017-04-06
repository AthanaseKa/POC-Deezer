package igraal.com.poc_deezer_vincent.database;

import igraal.com.poc_deezer_vincent.object.User;
import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by vincent on 05/04/2017.
 */

public class RealmManager {
    private static RealmManager instance;

    private RealmManager() {
    }

    public static RealmManager getInstance() {
        if (instance != null)
            return instance;
        else {
            instance = new RealmManager();
            return instance;
        }
    }

    public Observable<User> insertUser(User user) {
        Realm.getDefaultInstance().executeTransaction(realm1 -> {
            realm1.insertOrUpdate(user);
            Timber.e("INSERT :" + user.getName() + " " + user.getId());
        });
        return Observable.just(user);
    }

    public RealmResults<User> getAllUsers() {
        final RealmResults<User> users = Realm.getDefaultInstance().where(User.class).findAll();
        return users;
    }

    public User getUserById(String userId) {
        User user = Realm.getDefaultInstance().where(User.class).equalTo("id", userId).findFirst();
        return user;
    }

}
