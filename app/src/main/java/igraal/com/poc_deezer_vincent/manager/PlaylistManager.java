package igraal.com.poc_deezer_vincent.manager;

import igraal.com.poc_deezer_vincent.database.RealmManager;
import igraal.com.poc_deezer_vincent.network.DeezerClient;
import igraal.com.poc_deezer_vincent.object.jsonobject.PlaylistServiceResponse;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import igraal.com.poc_deezer_vincent.service.DeezerService;
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

    public Observable<RealmPlaylist> getPlaylistById(int playlistId) {
        Observable<PlaylistServiceResponse> playlistServiceResponseObservable = service.getPlaylist(playlistId);
        return playlistServiceResponseObservable.flatMap(playlistServiceResponse ->
                Observable.just(new RealmPlaylist(playlistServiceResponse)));
    }
}
