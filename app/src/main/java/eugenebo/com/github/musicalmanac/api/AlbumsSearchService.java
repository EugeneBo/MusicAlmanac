package eugenebo.com.github.musicalmanac.api;

import eugenebo.com.github.musicalmanac.model.AlbumModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AlbumsSearchService {
    /*
    The request is structured according to the following documentation
    https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/
    */
    @GET("search?entity=album&attribute=albumTerm&limit=200")

    /*
    For content-Type "application/x-www-form-urlencoded"
    body of the HTTP message sent to the server
    in name/value pairs are separated by the ampersand (&),
    and names are separated from values by the equals (=) symbol.
    */
    @Headers("Content-Type: application/x-www-form-urlencoded")

    //"term" represents user search request
    Call<AlbumModel> getAlbums(@Query("term") String term);
}