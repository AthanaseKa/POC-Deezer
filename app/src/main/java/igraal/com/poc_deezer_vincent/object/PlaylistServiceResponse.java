package igraal.com.poc_deezer_vincent.object;

import java.util.List;

/**
 * Created by vincent on 07/04/2017.
 */

public class PlaylistServiceResponse {

    List<Playlist> data;
    int total;

    PlaylistServiceResponse () {

    }

    public List<Playlist> getData() {
        return data;
    }

    public void setData(List<Playlist> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
