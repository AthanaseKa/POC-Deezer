package igraal.com.poc_deezer_vincent.object.realmobject;

import igraal.com.poc_deezer_vincent.object.jsonobject.TitleJson;
import io.realm.RealmObject;

/**
 * Created by vincent on 14/04/2017.
 */

public class RealmTitle extends RealmObject {
    int id;
    int duration;
    String title;
    String creatorName;

    public RealmTitle() {

    }

    public RealmTitle(TitleJson albumJson) {
        this.id = albumJson.getId();
        this.duration = albumJson.getDuration();
        this.title = albumJson.getTitle();
        this.creatorName = albumJson.getArtist().getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
