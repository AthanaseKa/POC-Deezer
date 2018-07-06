package igraal.com.poc_deezer_vincent.object.jsonobject;

/**
 * Created by vincent on 14/04/2017.
 */

public class TitleServiceResponse {
    int id;
    int duration;
    String title;
    ArtistJson artist;

    public TitleServiceResponse() {

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

    public ArtistJson getArtist() {
        return artist;
    }

    public void setArtist(ArtistJson artist) {
        this.artist = artist;
    }
}
