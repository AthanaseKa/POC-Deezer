package igraal.com.poc_deezer_vincent.manager;

import igraal.com.poc_deezer_vincent.database.RealmManager;
import igraal.com.poc_deezer_vincent.network.UserClient;
import igraal.com.poc_deezer_vincent.object.User;
import igraal.com.poc_deezer_vincent.service.DeezerService;
import rx.Observable;


/**
 * Created by vincent on 04/04/2017.
 */

public class UserManager {

    private static UserManager instance;

    private DeezerService service;
    private RealmManager realmManager;

    private UserManager() {
        service = UserClient.getInstance().getService();
        realmManager = RealmManager.getInstance();
    }

    public Observable<User> getUser(String input) {
        return service.getUser(input)
                .flatMap(user -> realmManager.insertUser(user));
    }

    public Observable<User> getUserById(String userId) {
        return  RealmManager.getInstance().getUserById(userId).asObservable().filter(user -> user != null);
    }

    public static UserManager getInstance() {
        if (instance != null)
            return instance;
        else {
            instance = new UserManager();
            return instance;
        }
    }
}
