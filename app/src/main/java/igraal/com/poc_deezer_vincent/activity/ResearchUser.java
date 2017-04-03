package igraal.com.poc_deezer_vincent.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import igraal.com.poc_deezer_vincent.service.DeezerService;
import igraal.com.poc_deezer_vincent.object.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by vincent on 03/04/2017.
 */

public class ResearchUser extends Activity {

    final String URL = "http://api.deezer.com/";
    @BindView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        retrieveUser();
    }

    private void retrieveUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        DeezerService service = retrofit.create(DeezerService.class);

        Call<User> userCall = service.getUser("5");
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.e("OnResponse", response.body().getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("OnFailure", t.getMessage());
            }
        });
    }

}
