package igraal.com.poc_deezer_vincent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import igraal.com.poc_deezer_vincent.R;
import igraal.com.poc_deezer_vincent.Tools;
import igraal.com.poc_deezer_vincent.database.RealmManager;
import igraal.com.poc_deezer_vincent.manager.UserManager;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by vincent on 03/04/2017.
 */

public class ResearchUserActivity extends RxAppCompatActivity {

    @BindView(R.id.research_user_editext)
    EditText editText;
    @BindView(R.id.research_user_textview)
    TextView title;
    @BindView(R.id.research_user_button)
    Button button;
    @BindView(R.id.research_user_error_textview)
    TextView error_title;

    Subscription subscription;
    int mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.research_user);
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        RealmManager.getInstance().setConfiguration(config);
        ButterKnife.bind(this);
    }

    private void observeEditText() {
        subscription = RxTextView.textChangeEvents(editText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    userId -> {
                        try {
                            mUserId = Integer.valueOf(editText.getText().toString());
                            button.setEnabled(true);
                            error_title.setVisibility(View.GONE);
                        } catch (NumberFormatException e) {
                            mUserId = -1;
                            button.setEnabled(false);
                            if (editText.getText().length() > 0)
                                error_title.setVisibility(View.VISIBLE);
                        }
                    },
                    error -> {
                        mUserId = -1;
                        button.setEnabled(false);
                        Timber.e(error, error.getMessage());
                    }
                );
    }

    @OnClick(R.id.research_user_button)
    public void retrieveUser() {
        UserManager.getInstance().getUser(mUserId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user -> {
                            Timber.e(user.getName() + "  " + user.getId() + " " + Thread.currentThread().getName());
                            saveUserId(user.getId());
                            switchActivity();
                },
                        error -> {
                            Timber.e(error, error.getMessage());
                        });
   }

    private void switchActivity() {
        Intent intent = new Intent(this, DisplayUserActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveUserId(int user_id) {
        PreferenceManager.getDefaultSharedPreferences(this).edit().putInt(Tools.PREFERENCE_USER_ID, user_id).commit();
    }

    private boolean checkIfUserAlreadyConnected() {
        int user_id = PreferenceManager.getDefaultSharedPreferences(this).getInt(Tools.PREFERENCE_USER_ID, -1);
        return user_id > -1;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkIfUserAlreadyConnected()) {
            switchActivity();
        }
        else {
            observeEditText();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
