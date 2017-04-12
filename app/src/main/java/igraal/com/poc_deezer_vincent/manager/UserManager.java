package igraal.com.poc_deezer_vincent.manager;

import java.util.List;

import igraal.com.poc_deezer_vincent.database.RealmManager;
import igraal.com.poc_deezer_vincent.network.UserClient;
import igraal.com.poc_deezer_vincent.object.Playlist;
import igraal.com.poc_deezer_vincent.object.PlaylistServiceResponse;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmUser;
import igraal.com.poc_deezer_vincent.service.DeezerService;
import io.realm.Realm;
import io.realm.RealmList;
import rx.Observable;
import timber.log.Timber;


/**
 * Created by vincent on 04/04/2017.
 */

public class UserManager {

    private static UserManager instance;

    private DeezerService service;
    private RealmManager realmManager;

    private UserManager() {
        service = UserClient.getInstance().getService();
        realmManager = RealmManager.getInstance();
    }

    public Observable<RealmUser> getUser(int input) {
        return service.getUser(input)
                .flatMap(user -> realmManager.insertUser(user));
    }

    public Observable<RealmUser> getUserById(int userId) {
        return  RealmManager.getInstance().getUserById(userId).filter(user -> user != null);
    }

    public Observable <RealmList<RealmPlaylist>> getUserPlaylists(int userId) {
        Observable <PlaylistServiceResponse> playlistServiceResponseObservable = service.getUserPlaylist(userId);
        return  playlistServiceResponseObservable
                .flatMap(playlistServiceResponse -> Observable.just(playlistServiceResponse.getData()))
                .flatMap(playlists -> formatUserPlaylist(playlists))
                .flatMap(playlistList -> realmManager.updateCurrentUserPlaylist(userId, playlistList))
                .flatMap(user -> Observable.just(user.getPlaylists()));
    }

    public Observable<RealmList<RealmPlaylist>> formatUserPlaylist(List<Playlist> playlistList) {
        RealmList<RealmPlaylist> myList = new RealmList<RealmPlaylist>();
        Realm.getInstance(realmManager.getConfiguration()).executeTransaction(realm -> {
            for (int i = 0; i < playlistList.size(); i++) {
                //RealmPlaylist tempList = Realm.getInstance(realmManager.getConfiguration()).
                //        createObject(RealmPlaylist.class, playlistList.get(i).getId());
                RealmPlaylist tempList = new RealmPlaylist();
                tempList.createFromPlayList(playlistList.get(i));
                myList.add(tempList);
                Timber.e("Playlist, element " + Integer.valueOf(i) + " " + tempList.getTitle());
            }});
        return Observable.just(myList);
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
