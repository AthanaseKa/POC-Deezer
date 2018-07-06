package igraal.com.poc_deezer_vincent.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.components.support.RxFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import igraal.com.poc_deezer_vincent.R;
import igraal.com.poc_deezer_vincent.Tools;
import igraal.com.poc_deezer_vincent.adapter.PlaylistCardViewAdapter;
import igraal.com.poc_deezer_vincent.manager.UserManager;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmUser;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by vincent on 12/04/2017.
 */

public class DisplayPlaylistFragment extends RxFragment {

    @BindView(R.id.display_user_playlist_recyclerview)
    RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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

        UserManager.getInstance().getUserById(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(bindToLifecycle())
                .subscribe(
                        this::initRecyclerView,
                        error -> {
                            Timber.e(error, error.getMessage());
                        });
    }

    private void initRecyclerView(RealmUser realmUser) {
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PlaylistCardViewAdapter(realmUser.getPlaylists(), getContext());
        mRecyclerView.setAdapter(mAdapter);
    }
}
