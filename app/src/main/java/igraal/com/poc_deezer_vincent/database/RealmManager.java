package igraal.com.poc_deezer_vincent.database;

import igraal.com.poc_deezer_vincent.object.User;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmUser;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by vincent on 05/04/2017.
 */

public class RealmManager {
    private static RealmManager instance;
    private RealmConfiguration configuration;

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

    public Observable<RealmUser> insertUser(User user) {
        return Observable.create(subscriber -> {
            Realm.getInstance(configuration).executeTransaction(realm1 -> {
                RealmResults<RealmUser> realmResults = realm1.where(RealmUser.class).equalTo("id", user.getId()).findAll();
                if (realmResults.size() == 0) {
                    RealmUser realmUser = realm1.createObject(RealmUser.class, user.getId());
                    realmUser.createFromUser(user);
                    Timber.e("INSERT :" + realmUser.getName() + " " + realmUser.getId());
                    subscriber.onNext(realm1.copyFromRealm(realmUser));
                } else {
                    subscriber.onNext(realm1.copyFromRealm(realmResults.get(0)));
                }
            });
        });
    }

    public Observable<RealmUser> getUserById(int userId) {
        return Observable.create(subscriber -> {
            Realm.getInstance(configuration).executeTransaction(realm1 -> {
                RealmUser user = realm1.copyFromRealm(
                        realm1.where(RealmUser.class).equalTo("id", userId).findFirst());
                subscriber.onNext(user);
            });
        });
    }

    public Observable<RealmUser> updateCurrentUserPlaylist(int userId, RealmList<RealmPlaylist> playlists) {
        return Observable.create(subscriber -> {
            Realm.getInstance(configuration).executeTransaction(realm -> {
                RealmUser user = realm.copyFromRealm(realm.where(RealmUser.class).equalTo("id", userId).findFirst());
                user.setPlaylists(playlists);
                realm.insertOrUpdate(user);
                subscriber.onNext(user);
            });
        });
    }

    public RealmConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(RealmConfiguration configuration) {
        this.configuration = configuration;
    }
}
