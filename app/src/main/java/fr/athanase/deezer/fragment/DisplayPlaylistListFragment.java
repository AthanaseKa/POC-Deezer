package fr.athanase.deezer.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.athanase.deezer.R;
import fr.athanase.deezer.Tools;
import fr.athanase.deezer.activity.DisplayPlaylistActivity;
import fr.athanase.deezer.adapter.AdapterIdCallBack;
import fr.athanase.deezer.adapter.AdapterLoadMore;
import fr.athanase.deezer.adapter.PlaylistCardViewAdapter;
import fr.athanase.deezer.manager.UserManager;
import fr.athanase.deezer.model.realm.Playlist;
import fr.athanase.deezer.model.realm.User;
import io.realm.RealmList;
import rx.Observable;

/**
 * Created by vincent on 12/04/2017.
 */

public class DisplayPlaylistListFragment extends Fragment implements AdapterIdCallBack, AdapterLoadMore {

    @BindView(R.id.display_user_playlist_recyclerview)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    public Observable<RealmList<Playlist>> loadMore(int index, int id) {
        return UserManager.Companion.getNextPlaylists(index, id);
    }

    @Override
    public void onCallBack(long id) {
        Intent intent = new Intent(getActivity(), DisplayPlaylistActivity.class);
        intent.putExtra(Tools.INTENT_PLAYLIST_ID, id);
        getActivity().startActivity(intent);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_user_playlist_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        retrieveUser();
    }

    private void retrieveUser() {
        int userId = this.getArguments().getInt(Tools.BUNDLE_USER_ID);

        /*UserManager.Companion.getUserById(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .subscribe(
                        this::initRecyclerView,
                        error -> {
                            Timber.e(error, error.getMessage());
                        });*/
    }

    private void initRecyclerView(User realmUser) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PlaylistCardViewAdapter(realmUser, this, getContext(), this, mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
    }
}
