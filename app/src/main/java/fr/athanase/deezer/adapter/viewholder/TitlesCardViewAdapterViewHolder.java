package fr.athanase.deezer.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.athanase.deezer.R;

/**
 * Created by vincent on 18/04/2017.
 */

public class TitlesCardViewAdapterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.display_playlist_titles_cardview_title)
    public TextView titleTitle;
    @BindView(R.id.display_playlist_titles_cardview_tracks)
    public TextView titleDuration;

    public View cardview;

    public TitlesCardViewAdapterViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        cardview = view;
    }
}