package igraal.com.poc_deezer_vincent.object.jsonobject;

/**
 * Created by vincent on 03/04/2017.
 */

public class UserJson {
    private int id;
    private String name;
    private String country;
    private String picture;

    public UserJson() {

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
