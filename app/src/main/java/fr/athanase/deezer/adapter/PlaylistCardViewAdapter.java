package fr.athanase.deezer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import fr.athanase.deezer.R;
import fr.athanase.deezer.adapter.viewholder.PlaylistCardViewAdapterViewHolder;
import fr.athanase.deezer.model.realm.User;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by vincent on 13/04/2017.
 */

public class PlaylistCardViewAdapter extends RecyclerView.Adapter<PlaylistCardViewAdapterViewHolder> {

    private User realmUser;
    private AdapterIdCallBack callBack;
    private Context context;
    private AdapterLoadMore adapterLoadMore;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    private boolean loading = false;
    private boolean loadMore = true;
    private int index = 25;
    int pastVisiblesItems, visibleItemCount, totalItemCount;


    public PlaylistCardViewAdapter(User realmUser, AdapterIdCallBack callBack,
                                   Context context, AdapterLoadMore loadMore,
                                   RecyclerView recyclerView) {
        this.realmUser = realmUser;
        this.callBack = callBack;
        this.context = context;
        this.adapterLoadMore = loadMore;
        this.recyclerView = recyclerView;
        if (this.recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            this.linearLayoutManager = (LinearLayoutManager) this.recyclerView.getLayoutManager();
        }

    }

    @Override
    public PlaylistCardViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_cardview, parent, false);
        PlaylistCardViewAdapterViewHolder vh = new PlaylistCardViewAdapterViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlaylistCardViewAdapterViewHolder holder, int position) {
        holder.playlistTitle.setText(realmUser.getPlaylists().get(position).getTitle());
        holder.playlistTracksNb.setText(Integer.toString(realmUser.getPlaylists().get(position).getNbTracks()));
        Glide.with(context).load(realmUser.getPlaylists().get(position).getPicture()).centerCrop().into(holder.playlistImageView);
        
        holder.cardview.setOnClickListener(v -> {
                    callBack.onCallBack(realmUser.getPlaylists().get(position).getId());
                }
        );
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy > 0)
                {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount && loadMore)
                    {
                        loading = true;
                        Timber.e(Integer.toString(index));
                        adapterLoadMore.loadMore(index, realmUser.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(playlists -> {
                                    if (playlists.size() > 0) {
                                        realmUser.getPlaylists().addAll(playlists);
                                        notifyItemRangeChanged(index, realmUser.getPlaylists().size());
                                        index = index + 25;
                                    } if (playlists.size() == 0) {
                                        loadMore = false;
                                    }
                                    loading = false;
                                }, error -> {
                                    Timber.e(error, error.getMessage());
                                });
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return realmUser.getPlaylists().size();
    }


}

