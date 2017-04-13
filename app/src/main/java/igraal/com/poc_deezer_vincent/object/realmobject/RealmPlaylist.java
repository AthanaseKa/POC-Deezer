package igraal.com.poc_deezer_vincent.object.realmobject;

import igraal.com.poc_deezer_vincent.object.jsonobject.PlaylistJson;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vincent on 07/04/2017.
 */

public class RealmPlaylist extends RealmObject{
    @PrimaryKey
    private int id;
    private String title;
    private String picture;
    private int nb_tracks;

    public RealmPlaylist() {

    }

    public RealmPlaylist(PlaylistJson playlist) {
        this.id = playlist.getId();
        this.title = playlist.getTitle();
        this.picture = playlist.getPicture();
        this.nb_tracks = playlist.getNb_tracks();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getNb_tracks() {
        return nb_tracks;
    }

    public void setNb_tracks(int nb_tracks) {
        this.nb_tracks = nb_tracks;
    }
}
