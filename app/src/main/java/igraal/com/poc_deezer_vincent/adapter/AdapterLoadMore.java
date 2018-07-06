package igraal.com.poc_deezer_vincent.adapter;

import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import io.realm.RealmList;
import rx.Observable;

/**
 * Created by vincent on 19/04/2017.
 */

public interface AdapterLoadMore {

    public Observable<RealmList<RealmPlaylist>> loadMore(int index, int id);

}
