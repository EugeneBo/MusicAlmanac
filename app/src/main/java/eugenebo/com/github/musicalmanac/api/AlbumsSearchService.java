package eugenebo.com.github.musicalmanac.api;

import eugenebo.com.github.musicalmanac.model.AlbumModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AlbumsSearchService {

    @GET("search?entity=album&attribute=albumTerm&limit=200")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<AlbumModel> getAlbums(@Query("term") String term);
}