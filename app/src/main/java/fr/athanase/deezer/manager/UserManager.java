package fr.athanase.deezer.manager;

import java.util.List;

import fr.athanase.deezer.database.RealmManager;
import fr.athanase.deezer.network.DeezerClient;
import fr.athanase.deezer.object.jsonobject.PlaylistJson;
import fr.athanase.deezer.object.jsonobject.PlaylistListServiceResponse;
import fr.athanase.deezer.object.jsonobject.UserJson;
import fr.athanase.deezer.object.realmobject.RealmPlaylist;
import fr.athanase.deezer.object.realmobject.RealmUser;
import fr.athanase.deezer.service.DeezerService;
import io.realm.RealmList;
import rx.Observable;


/**
 * Created by vincent on 04/04/2017.
 */

public class UserManager {

    private static UserManager instance;

    private DeezerService service;
    private RealmManager realmManager;

    private UserManager() {
        service = DeezerClient.getInstance().getService();
        realmManager = RealmManager.getInstance();
    }

    public Observable<RealmUser> getUser(int input) {
        Observable<RealmUser> user = realmManager.getUserById(input);
        return user.flatMap(realmUser -> {
            if (realmUser == null) {
                Observable<UserJson> userJson = service.getUser(input);
                        return userJson.filter(userJson1 -> userJson1.getName() != null)
                                .flatMap(userJson1 -> realmManager.insertUser(new RealmUser(userJson1)));
            }
            else {
                return user;
            }
        });
    }

    public Observable<RealmUser> getUserById(int userId) {
        return  RealmManager.getInstance().getUserById(userId);
    }

    public Observable <RealmList<RealmPlaylist>> getUserPlaylists(int userId) {
        Observable <PlaylistListServiceResponse> playlistServiceResponseObservable = service.getUserPlaylist(userId);
        return  playlistServiceResponseObservable
                .flatMap(playlistListServiceResponse -> formatUserPlaylist(playlistListServiceResponse.getData()))
                .flatMap(playlists -> realmManager.updateUserPlaylistData(userId, playlists))
                .flatMap(user -> Observable.just(user.getPlaylists()));
    }

    public Observable <RealmList<RealmPlaylist>> getNextPlaylists(int index, int userId) {
        Observable <PlaylistListServiceResponse> playlistListServiceResponseObservable = service.getNextUserPlaylist(userId, index);
        return playlistListServiceResponseObservable.flatMap(playlistListServiceResponse ->
                formatUserPlaylist(playlistListServiceResponse.getData()));
    }

    public Observable<RealmList<RealmPlaylist>> formatUserPlaylist(List<PlaylistJson> playlistList) {
        RealmList<RealmPlaylist> myList = new RealmList<RealmPlaylist>();
        for (int i = 0; i < playlistList.size(); i++) {
            RealmPlaylist realmPlaylist = new RealmPlaylist(playlistList.get(i));
            myList.add(realmPlaylist);
        }
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
