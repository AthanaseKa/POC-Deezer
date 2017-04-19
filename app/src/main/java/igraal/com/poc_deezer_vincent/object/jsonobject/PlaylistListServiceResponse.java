package igraal.com.poc_deezer_vincent.object.jsonobject;

import java.util.List;

/**
 * Created by vincent on 07/04/2017.
 */

public class PlaylistListServiceResponse {

    List<PlaylistJson> data;
    int total;

    PlaylistListServiceResponse() {

    }

    public List<PlaylistJson> getData() {
        return data;
    }

    public void setData(List<PlaylistJson> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
