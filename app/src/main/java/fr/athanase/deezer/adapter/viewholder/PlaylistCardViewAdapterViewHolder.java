package fr.athanase.deezer.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.athanase.deezer.R;

/**
 * Created by vincent on 18/04/2017.
 */


public class PlaylistCardViewAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.display_user_playlist_cardview_title)
    public TextView playlistTitle;
    @BindView(R.id.display_user_playlist_cardview_tracks)
    public TextView playlistTracksNb;
    @BindView(R.id.display_user_playlist_cardview_image)
    public ImageView playlistImageView;

    public View cardview;

    public PlaylistCardViewAdapterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        cardview = view;
    }
}
