package fr.athanase.deezer.database;

import fr.athanase.deezer.model.realm.Playlist;
import fr.athanase.deezer.model.realm.User;
import io.realm.Realm;
import io.realm.RealmList;
import rx.Observable;
import timber.log.Timber;

/**
 * Created by vincent on 05/04/2017.
 */

public class RealmManager {
    //private static RealmManager instance;

    private RealmManager() {
    }

    public static RealmManager getInstance() {
        /*if (instance != null)
            return instance;
        else {
            instance = new RealmManager();
            Realm.setDefaultConfiguration(new RealmConfiguration.Builder()
                    .deleteRealmIfMigrationNeeded()
                    .build());
            return instance;
        }*/
        return null;
    }

    public Observable<User> insertUser(User user) {
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

    public Observable<User> getUserById(int userId) {
        return Observable.create(subscriber -> {
            Realm realmInstance = Realm.getDefaultInstance();
            realmInstance.beginTransaction();
            User user = realmInstance.where(User.class).equalTo("id", userId).findFirst();
            if (user != null) {
                user = realmInstance.copyFromRealm(user);
            }
            realmInstance.commitTransaction();
            realmInstance.close();
            subscriber.onNext(user);
            subscriber.onCompleted();
        });
    }

    public Observable<User> updateUserPlaylistData(int userId, RealmList<Playlist> playlists) {
        return Observable.create(subscriber -> {
           Realm realmInstance = Realm.getDefaultInstance();
            User user = realmInstance.where(User.class).equalTo("id", userId).findFirst();
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
