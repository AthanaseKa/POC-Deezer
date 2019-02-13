package fr.athanase.deezer.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.athanase.deezer.R;
import fr.athanase.deezer.Tools;
import fr.athanase.deezer.adapter.TitlesCardViewAdapter;
import fr.athanase.deezer.manager.PlaylistManager;
import fr.athanase.deezer.model.realm.Playlist;
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
        PlaylistManager.Companion.getPlaylistById(getIntent().getExtras().getLong(Tools.INTENT_PLAYLIST_ID))
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(playlist -> {
                    Timber.e(playlist.toString());
                    loadPlaylist(playlist);
                    initRecyclerView(playlist);
                }, error -> {
                    Timber.e(error, error.getMessage());
                });
    }

    private void loadPlaylist(Playlist playlist) {
        tvTitle.setText(playlist.getTitle());
        tvCreatorName.setText(playlist.getCreatorName());
        tvTracksNumber.setText(Integer.toString(playlist.getNbTracks()));
        Glide.with(this).load(playlist.getPicture()).centerCrop().into(ivHeaderPicture);

    }

    private void initRecyclerView(Playlist realmPlaylist) {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TitlesCardViewAdapter(realmPlaylist.getTitleRealmList());
        mRecyclerView.setAdapter(mAdapter);
    }
}
