package igraal.com.poc_deezer_vincent.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import igraal.com.poc_deezer_vincent.R;
import igraal.com.poc_deezer_vincent.Tools;
import igraal.com.poc_deezer_vincent.manager.PlaylistManager;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by vincent on 14/04/2017.
 */

public class DisplayPlaylistActivity extends RxAppCompatActivity {

    @BindView(R.id.display_playlist_title_textview)
    TextView title;
    @BindView(R.id.display_playlist_header_imageview)
    ImageView headerPicture;
    @BindView(R.id.display_playlist_creatorname_textview)
    TextView creatorName;
    @BindView(R.id.display_playlist_tracksnumber_textview)
    TextView tracksNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_playlist_activity);
        ButterKnife.bind(this);
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
                    loadPlaylist(playlist);
                }, error -> {
                    Timber.e(error, error.getMessage());
                });
    }

    private void loadPlaylist(RealmPlaylist playlist) {
        title.setText(playlist.getTitle());
        creatorName.setText(playlist.getCreatorName());
        tracksNumber.setText(Integer.toString(playlist.getNb_tracks()));
        Glide
                .with(this)
                .load(playlist.getPicture())
                .centerCrop()
                .into(headerPicture);

    }

}
