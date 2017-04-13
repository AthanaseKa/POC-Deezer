package igraal.com.poc_deezer_vincent.database;

import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmUser;
import io.realm.Realm;
import io.realm.RealmList;
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

    public Observable<RealmUser> insertUser(RealmUser user) {
        return Observable.create(subscriber -> {
            Timber.e("INSERT : " + user.getId() + " - " + user.getName());
            Realm realmInstance = Realm.getDefaultInstance();
            realmInstance.beginTransaction();
            realmInstance.insertOrUpdate(user);
            realmInstance.commitTransaction();
            realmInstance.close();
            subscriber.onNext(user);
            subscriber.onCompleted();
        });
    }

    public Observable<RealmUser> getUserById(int userId) {
        return Observable.create(subscriber -> {
            Realm realmInstance = Realm.getDefaultInstance();
            realmInstance.beginTransaction();
            RealmUser user = realmInstance.where(RealmUser.class).equalTo("id", userId).findFirst();
            if (user != null) {
                user = realmInstance.copyFromRealm(user);
            }
            realmInstance.commitTransaction();
            realmInstance.close();
            subscriber.onNext(user);
            subscriber.onCompleted();
        });
    }

    public Observable<RealmUser> updateCurrentUserPlaylist(int userId, RealmList<RealmPlaylist> playlists) {
        return Observable.create(subscriber -> {
            Realm realmInstance = Realm.getDefaultInstance();
            RealmUser user = realmInstance.where(RealmUser.class).equalTo("id", userId).findFirst();
            realmInstance.beginTransaction();
            if (user != null) {
                user = realmInstance.copyFromRealm(user);
                user.setPlaylists(playlists);
                realmInstance.insertOrUpdate(user);
            }
            realmInstance.commitTransaction();
            realmInstance.close();
            subscriber.onNext(user);
            subscriber.onCompleted();
        });
    }
}
