package igraal.com.poc_deezer_vincent.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import igraal.com.poc_deezer_vincent.R;
import igraal.com.poc_deezer_vincent.adapter.viewholder.TitlesCardViewAdapterViewHolder;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmTitle;
import io.realm.RealmList;

/**
 * Created by vincent on 18/04/2017.
 */

public class TitlesCardViewAdapter extends RecyclerView.Adapter<TitlesCardViewAdapterViewHolder>{

    private RealmList<RealmTitle> titles;

    public TitlesCardViewAdapter(RealmList<RealmTitle> titles) {
        this.titles = titles;
    }

    @Override
    public TitlesCardViewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.titles_cardview, parent, false);
        TitlesCardViewAdapterViewHolder vh = new TitlesCardViewAdapterViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TitlesCardViewAdapterViewHolder holder, int position) {
        holder.titleTitle.setText(titles.get(position).getTitle());
        holder.titleDuration.setText(Integer.toString(titles.get(position).getDuration()));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
