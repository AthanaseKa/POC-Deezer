package igraal.com.poc_deezer_vincent.object.jsonobject;

/**
 * Created by vincent on 14/04/2017.
 */

public class PlaylistServiceResponse {

    TracksJson tracks;
    CreatorJson creator;

    int id;
    String title;
    String description;
    int nbTracks;
    String picture;

    public PlaylistServiceResponse() {

    }

    public TracksJson getTracks() {
        return tracks;
    }

    public void setTracks(TracksJson tracks) {
        this.tracks = tracks;
    }

    public CreatorJson getCreator() {
        return creator;
    }

    public void setCreator(CreatorJson creator) {
        this.creator = creator;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getNbTracks() {
        return nbTracks;
    }

    public void setNbTracks(int nbTracks) {
        this.nbTracks = nbTracks;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
