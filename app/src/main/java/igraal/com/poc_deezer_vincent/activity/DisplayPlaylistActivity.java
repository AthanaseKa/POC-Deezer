package igraal.com.poc_deezer_vincent.activity;

import android.os.Bundle;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import igraal.com.poc_deezer_vincent.Tools;
import igraal.com.poc_deezer_vincent.manager.PlaylistManager;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by vincent on 14/04/2017.
 */

public class DisplayPlaylistActivity extends RxAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrievePlaylist();
    }

    private void retrievePlaylist() {

        PlaylistManager.getInstance()
                .getPlaylistById(getIntent().getExtras().getInt(Tools.INTENT_PLAYLIST_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(playlist -> {
                    Timber.e(playlist.toString());
                }, error -> {
                    Timber.e(error, error.getMessage());
                });
    }

}
