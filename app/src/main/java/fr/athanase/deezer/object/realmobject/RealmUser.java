package fr.athanase.deezer.object.realmobject;

import fr.athanase.deezer.object.jsonobject.UserJson;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vincent on 07/04/2017.
 */

public class RealmUser extends RealmObject {
    @PrimaryKey
    private int id;
    private String name;
    private String country;
    private String picture;

    public RealmList<RealmPlaylist> playlists;

    public RealmUser() {

    }

    public RealmUser(UserJson user) {
        this.id = user.getId();
        this.name = user.getName();
        this.country = user.getCountry();
        this.picture = user.getPicture();
    }

    public RealmList<RealmPlaylist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(RealmList<RealmPlaylist> playlists) {
        this.playlists = playlists;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
