package igraal.com.poc_deezer_vincent.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import igraal.com.poc_deezer_vincent.R;

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

    public PlaylistCardViewAdapterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
