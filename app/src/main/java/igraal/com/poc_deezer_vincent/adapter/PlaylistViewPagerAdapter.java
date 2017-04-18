package igraal.com.poc_deezer_vincent.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import igraal.com.poc_deezer_vincent.Tools;
import igraal.com.poc_deezer_vincent.fragment.DisplayAlbumsFragment;
import igraal.com.poc_deezer_vincent.fragment.DisplayPlaylistFragment;

/**
 * Created by vincent on 13/04/2017.
 */

public class PlaylistViewPagerAdapter extends FragmentStatePagerAdapter {

    private Bundle bundle;

    public PlaylistViewPagerAdapter(FragmentManager fragmentManager, int userId) {
        super(fragmentManager);
        bundle = new Bundle();
        bundle.putInt(Tools.BUNDLE_USER_ID, userId);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = new DisplayPlaylistFragment();
                break;
            case 1:
                fragment = new DisplayAlbumsFragment();
                break;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String pageTitle = new String();

        switch (position) {
            case 0:
                pageTitle = "Playlists";
                break;
            case 1:
                pageTitle = "Albums";
        }
        return pageTitle;
    }
}
