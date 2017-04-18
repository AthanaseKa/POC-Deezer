package igraal.com.poc_deezer_vincent.object.jsonobject;

/**
 * Created by vincent on 14/04/2017.
 */

public class TitleJson {

    int id;
    int duration;
    String title;
    ArtistJson artist;

    public TitleJson() {

    }

    public TitleJson(TitleServiceResponse albumServiceResponse) {
        this.id = albumServiceResponse.getId();
        this.duration = albumServiceResponse.getDuration();
        this.title = albumServiceResponse.getTitle();
        this.artist = albumServiceResponse.getArtist();
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
