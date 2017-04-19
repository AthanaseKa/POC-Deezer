package igraal.com.poc_deezer_vincent.object.realmobject;

import java.util.List;

import igraal.com.poc_deezer_vincent.object.jsonobject.PlaylistJson;
import igraal.com.poc_deezer_vincent.object.jsonobject.PlaylistServiceResponse;
import igraal.com.poc_deezer_vincent.object.jsonobject.TitleJson;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import timber.log.Timber;

/**
 * Created by vincent on 07/04/2017.
 */

public class RealmPlaylist extends RealmObject{
    @PrimaryKey
    private int id;
    private String title;
    private String picture;
    private int nb_tracks;
    private String description;
    private String creatorName;

    private RealmList<RealmTitle> titleRealmList;

    public RealmPlaylist() {

    }

    public RealmPlaylist(PlaylistJson playlist) {
        this.id = playlist.getId();
        this.title = playlist.getTitle();
        this.picture = playlist.getPicture();
        this.nb_tracks = playlist.getNb_tracks();
    }

    public RealmPlaylist(PlaylistServiceResponse playlistServiceResponse) {
        this.id = playlistServiceResponse.getId();
        this.title = playlistServiceResponse.getTitle();
        this.picture = playlistServiceResponse.getPicture();
        this.nb_tracks = playlistServiceResponse.getNbTracks();
        this.description = playlistServiceResponse.getDescription();
        this.titleRealmList = transformTitleList(playlistServiceResponse.getTracks().getData());
        this.creatorName = playlistServiceResponse.getCreator().getName();
    }

    private RealmList<RealmTitle> transformTitleList(List<TitleJson> titleJson) {
        RealmList<RealmTitle> myList = new RealmList<RealmTitle>();
        for (int i = 0; i < titleJson.size(); i++) {
            RealmTitle realmTitle = new RealmTitle(titleJson.get(i));
            myList.add(realmTitle);
            Timber.e("PlaylistJson, element " + Integer.valueOf(i) + " " + realmTitle.getTitle());
        }
        return myList;
    }

    public String toString() {
        return ("id: " + Integer.toString(id) +
                " title: " + title +
                " picture : " + picture +
                " nb_tracks : " + Integer.toString(nb_tracks) +
                " description: " + description +
                " titleRealmlist: " + titleRealmList.toString() +
                " creatorName : " + creatorName
        );
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RealmList<RealmTitle> getTitleRealmList() {
        return titleRealmList;
    }

    public void setTitleRealmList(RealmList<RealmTitle> titleRealmList) {
        this.titleRealmList = titleRealmList;
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
