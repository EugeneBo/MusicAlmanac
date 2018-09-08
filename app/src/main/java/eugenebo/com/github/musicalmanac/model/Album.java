package eugenebo.com.github.musicalmanac.model;

import eugenebo.com.github.musicalmanac.views.albumdetail.ItemView;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/*This POJO class represents an Album objects
which are created as a result of the service response.*/
public class Album implements Serializable, ItemView {

    @Expose
    private Integer collectionId;

    @Expose
    private String artistName;

    @Expose
    private String collectionName;

    @Expose
    private String artworkUrl100;

    @Expose
    private Double collectionPrice;

    @Expose
    private String copyright;

    @Expose
    private String country;

    @Expose
    private String releaseDate;

    @Expose
    private String primaryGenreName;

    public Integer getCollectionId() {
        return collectionId;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public Double getCollectionPrice() {
        return collectionPrice;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getCountry() {
        return country;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }
}
