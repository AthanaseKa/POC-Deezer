package igraal.com.poc_deezer_vincent.object.jsonobject;

/**
 * Created by vincent on 06/04/2017.
 */

public class PlaylistJson {

    private long id;
    private String title;
    private String picture;
    private int nb_tracks;

    public PlaylistJson() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
