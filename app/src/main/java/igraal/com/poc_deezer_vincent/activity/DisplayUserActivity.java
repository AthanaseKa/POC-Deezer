package igraal.com.poc_deezer_vincent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import igraal.com.poc_deezer_vincent.R;
import igraal.com.poc_deezer_vincent.Tools;
import igraal.com.poc_deezer_vincent.adapter.PlaylistViewPagerAdapter;
import igraal.com.poc_deezer_vincent.manager.UserManager;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmPlaylist;
import igraal.com.poc_deezer_vincent.object.realmobject.RealmUser;
import io.realm.RealmList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by vincent on 05/04/2017.
 */

public class DisplayUserActivity extends RxAppCompatActivity {

    @BindView(R.id.display_user_country_textview)
    TextView userCountry;
    @BindView(R.id.display_user_header_imageview)
    ImageView userPicture;
    @BindView(R.id.display_user_name_textview)
    TextView userName;
    @BindView(R.id.display_user_tablayout)
    TabLayout tableLayout;
    @BindView(R.id.display_user_viewpager)
    ViewPager viewPager;

    private Observable <RealmUser> user;
    private int userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_user_activity);
        ButterKnife.bind(this);
        loadUser();
    }

    private void loadUser() {
         userId = PreferenceManager.getDefaultSharedPreferences(this).getInt(Tools.PREFERENCE_USER_ID, -1);
        if (userId == -1)
            noUserToLoad();
        else {
            user = UserManager.getInstance().getUserById(userId);
            user.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            user1 -> {
                                if (user1 != null) {
                                    loadHeader(user1);
                                    retrievePlaylist(userId);
                                }
                            },
                            error -> {
                                Timber.e(error, error.getMessage());
                            });
        }
    }

    private void loadHeader(RealmUser user) {
        userCountry.setText(user.getCountry());
        userName.setText(user.getName());
        Glide
                .with(this)
                .load(user.getPicture())
                .centerCrop()
                .into(userPicture);
    }

    private void loadLists() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        PlaylistViewPagerAdapter playlistViewPagerAdapter = new PlaylistViewPagerAdapter(fragmentManager, userId);
        viewPager.setAdapter(playlistViewPagerAdapter);
        tableLayout.setupWithViewPager(viewPager);
    }

    private void noUserToLoad() {
        Intent intent = new Intent(this, ResearchUserActivity.class);
        startActivity(intent);
        finish();
        Toast.makeText(this, "No user found", Toast.LENGTH_LONG).show();
    }

    private void retrievePlaylist(int userId) {
        Observable<RealmList<RealmPlaylist>> playlist = UserManager.getInstance().getUserPlaylists(userId);
        playlist.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(playlists -> {
                    loadLists();
                },
                    error -> {
                        Timber.e(error, error.getMessage());
                });
    }

    private void switchUser() {
        deleteUser();
        Intent intent = new Intent(this, ResearchUserActivity.class);
        startActivity(intent);
        finish();
    }

    private void deleteUser() {
        PreferenceManager.getDefaultSharedPreferences(this).edit().remove(Tools.PREFERENCE_USER_ID).commit();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_menu_deconnection:
                switchUser();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
