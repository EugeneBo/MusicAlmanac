package eugenebo.com.github.musicalmanac.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AlbumModel {

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;

    @SerializedName("results")
    @Expose
    private List<Album> albums = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public List<Album> getAlbums() {

        //sorting collection by album name (collection name)
        Collections.sort(albums, new Comparator<Album>() {
            @Override
            public int compare(Album o1, Album o2) {
                return o1.getCollectionName().compareTo(o2.getCollectionName());
            }
        });
        return albums;
    }

}
