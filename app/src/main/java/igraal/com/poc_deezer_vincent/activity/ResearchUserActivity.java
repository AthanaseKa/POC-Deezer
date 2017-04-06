package igraal.com.poc_deezer_vincent.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import igraal.com.poc_deezer_vincent.manager.UserManager;
import io.realm.Realm;
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

    Subscription subscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(getApplicationContext());
        setContentView(R.layout.research_user);
        ButterKnife.bind(this);
    }

    private void observeEditText() {
        subscription = RxTextView.textChanges(editText)
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    charSequence -> {
                            button.setEnabled(charSequence.length() > 0);
                    },
                        error -> {
                            Timber.e(error, error.getMessage());
                        }
                );
    }

    @OnClick(R.id.research_user_button)
    public void retrieveUser() {
        UserManager.getInstance().getUser(editText.getText().toString())
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

    private void saveUserId(String user_id) {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit().putString(Tools.PREFERENCE_USER_ID, user_id).commit();
    }

    private boolean checkIfUserAlreadyConnected() {
        String user_id = PreferenceManager.getDefaultSharedPreferences(this).getString(Tools.PREFERENCE_USER_ID, "");
        return user_id.length() > 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkIfUserAlreadyConnected())
            switchActivity();
        else
            observeEditText();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (subscription != null)
            subscription.unsubscribe();
    }
}
