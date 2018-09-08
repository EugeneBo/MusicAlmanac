package eugenebo.com.github.musicalmanac.api;

import eugenebo.com.github.musicalmanac.model.AlbumModel;
import eugenebo.com.github.musicalmanac.model.DetailedAlbumModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Service {
    /*
    The request is structured according to the following documentation
    https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/
    */
    @GET("lookup?entity=song")
    /*
    For content-Type "application/x-www-form-urlencoded"
    body of the HTTP message sent to the server
    in name/value pairs are separated by the ampersand (&),
    and names are separated from values by the equals (=) symbol.
    */
    @Headers("Content-Type: application/x-www-form-urlencoded")
    /*
    "id" parameter represents an album id with the help of which
    we can get detailed album information and track list by "lookup" request./
    */
    Call<DetailedAlbumModel> getAlbumInfo(@Query("id") Integer id);


    /*
    The request is structured according to the following documentation
    https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/
    */
    @GET("search?entity=album&attribute=albumTerm&limit=200")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    //"term" represents user search request
    Call<AlbumModel> getAlbums(@Query("term") String term);






}
