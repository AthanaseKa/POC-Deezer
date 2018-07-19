package fr.athanase.deezer.adapter;

import fr.athanase.deezer.model.realm.Playlist;
import io.realm.RealmList;
import rx.Observable;

/**
 * Created by vincent on 19/04/2017.
 */

public interface AdapterLoadMore {
    Observable<RealmList<Playlist>> loadMore(int index, int id);
}
