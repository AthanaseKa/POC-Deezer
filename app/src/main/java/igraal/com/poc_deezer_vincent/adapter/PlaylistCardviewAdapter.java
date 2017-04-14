package igraal.com.poc_deezer_vincent.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import igraal.com.poc_deezer_vincent.R;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import io.realm.RealmList;

/**
 * Created by vincent on 13/04/2017.
 */

public class PlaylistCardViewAdapter extends RecyclerView.Adapter<PlaylistCardViewAdapter.ViewHolder> {

    private RealmList <RealmPlaylist> playlist;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.display_user_playlist_cardview_title)
        public TextView playlistTitle;
        @BindView(R.id.display_user_playlist_cardview_tracks)
        public TextView playlistTracksNb;
        @BindView(R.id.display_user_playlist_cardview_image)
        public ImageView playlistImageView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public PlaylistCardViewAdapter(RealmList<RealmPlaylist> playlist, Context context) {
        this.playlist = playlist;
        this.context = context;
    }

    @Override
    public PlaylistCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_cardview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.playlistTitle.setText(playlist.get(position).getTitle());
        holder.playlistTracksNb.setText(Integer.toString(playlist.get(position).getNb_tracks()));
        Glide
                .with(context)
                .load(playlist.get(position).getPicture())
                .centerCrop()
                .into(holder.playlistImageView);
    }

    @Override
    public int getItemCount() {
        return playlist.size();
    }
}

