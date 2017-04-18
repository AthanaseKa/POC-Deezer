package igraal.com.poc_deezer_vincent.object.jsonobject;

import java.util.List;

/**
 * Created by vincent on 14/04/2017.
 */

public class TracksJson {
    List<TitleJson> data;

    public TracksJson() {

    }

    public List<TitleJson> getData() {
        return data;
    }

    public void setData(List<TitleJson> data) {
        this.data = data;
    }
}
