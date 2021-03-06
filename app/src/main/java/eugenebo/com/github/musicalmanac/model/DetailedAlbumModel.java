package eugenebo.com.github.musicalmanac.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class DetailedAlbumModel{

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;

    @SerializedName("results")
    @Expose
    private List<Song> songs = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public List<Song> getSongs() {
        return songs;
    }

}
