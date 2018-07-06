package igraal.com.poc_deezer_vincent.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import igraal.com.poc_deezer_vincent.R;
import igraal.com.poc_deezer_vincent.Tools;
import igraal.com.poc_deezer_vincent.adapter.TitlesCardViewAdapter;
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
    TextView tvTitle;
    @BindView(R.id.display_playlist_header_imageview)
    ImageView ivHeaderPicture;
    @BindView(R.id.display_playlist_creatorname_textview)
    TextView tvCreatorName;
    @BindView(R.id.display_playlist_tracksnumber_textview)
    TextView tvTracksNumber;

    @BindView(R.id.display_playlist_recyclerview)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_playlist_activity);
        ButterKnife.bind(this);
        retrievePlaylist();
    }

    private void retrievePlaylist() {
        PlaylistManager.getInstance()
                .getPlaylistById(getIntent().getExtras().getLong(Tools.INTENT_PLAYLIST_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(playlist -> {
                    Timber.e(playlist.toString());
                    loadPlaylist(playlist);
                    initRecyclerView(playlist);
                }, error -> {
                    Timber.e(error, error.getMessage());
                });
    }

    private void loadPlaylist(RealmPlaylist playlist) {
        tvTitle.setText(playlist.getTitle());
        tvCreatorName.setText(playlist.getCreatorName());
        tvTracksNumber.setText(Integer.toString(playlist.getNb_tracks()));
        Glide.with(this).load(playlist.getPicture()).centerCrop().into(ivHeaderPicture);

    }

    private void initRecyclerView(RealmPlaylist realmPlaylist) {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TitlesCardViewAdapter(realmPlaylist.getTitleRealmList());
        mRecyclerView.setAdapter(mAdapter);
    }
}
