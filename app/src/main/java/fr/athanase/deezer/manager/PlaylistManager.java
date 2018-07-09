package fr.athanase.deezer.manager;

import fr.athanase.deezer.database.RealmManager;
import fr.athanase.deezer.network.DeezerClient;
import fr.athanase.deezer.object.jsonobject.PlaylistServiceResponse;
import fr.athanase.deezer.object.realmobject.RealmPlaylist;
import fr.athanase.deezer.service.DeezerService;
import rx.Observable;

/**
 * Created by vincent on 14/04/2017.
 */

public class PlaylistManager {

    private static PlaylistManager instance;

    private DeezerService service;
    private RealmManager realmManager;

    private PlaylistManager() {
        service = DeezerClient.getInstance().getService();
        realmManager = RealmManager.getInstance();
    }

    public static PlaylistManager getInstance() {
        if (instance != null) {
            return instance;
        } else {
            return instance = new PlaylistManager();
        }
    }

    public Observable<RealmPlaylist> getPlaylistById(long playlistId) {
        Observable<PlaylistServiceResponse> playlistServiceResponseObservable = service.getPlaylist(playlistId);
        return playlistServiceResponseObservable.flatMap(playlistServiceResponse ->
                Observable.just(new RealmPlaylist(playlistServiceResponse)));
    }
}
