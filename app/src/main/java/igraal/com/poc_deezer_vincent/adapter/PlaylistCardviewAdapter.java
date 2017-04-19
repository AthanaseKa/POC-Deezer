package igraal.com.poc_deezer_vincent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import igraal.com.poc_deezer_vincent.R;
import igraal.com.poc_deezer_vincent.adapter.viewholder.PlaylistCardViewAdapterViewHolder;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import io.realm.RealmList;

/**
 * Created by vincent on 13/04/2017.
 */

public class PlaylistCardViewAdapter extends RecyclerView.Adapter<PlaylistCardViewAdapterViewHolder> {

    private RealmList <RealmPlaylist> playlist;
    private AdapterIdCallBack callBack;
    private Context context;

    public PlaylistCardViewAdapter(RealmList<RealmPlaylist> playlist, AdapterIdCallBack callBack, Context context) {
        this.playlist = playlist;
        this.callBack = callBack;
        this.context = context;
    }

    @Override
    public PlaylistCardViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_cardview, parent, false);
        PlaylistCardViewAdapterViewHolder vh = new PlaylistCardViewAdapterViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PlaylistCardViewAdapterViewHolder holder, int position) {
        holder.playlistTitle.setText(playlist.get(position).getTitle());
        holder.playlistTracksNb.setText(Integer.toString(playlist.get(position).getNb_tracks()));
        Glide
                .with(context)
                .load(playlist.get(position).getPicture())
                .centerCrop()
                .into(holder.playlistImageView);

        holder.cardview.setOnClickListener(v -> {
                    callBack.onCallBack(playlist.get(position).getId());
                }
        );
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }
}

