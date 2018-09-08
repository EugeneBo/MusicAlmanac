package eugenebo.com.github.musicalmanac.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

import eugenebo.com.github.musicalmanac.views.albumdetail.ItemView;

/*This POJO class represents a Song objects
which are created as a result of the service response.*/

public class Song implements Serializable, ItemView {

    @Expose
    private String trackName;

    @Expose
    private Integer trackNumber;

    @Expose
    private Integer trackTimeMillis;

    public String getTrackName() {
        return trackName;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public Integer getTrackTimeMillis() {
        return trackTimeMillis;
    }


}