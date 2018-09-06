package eugenebo.com.github.musicalmanac.api;

import eugenebo.com.github.musicalmanac.model.DetailedAlbumModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface AlbumLookupService {

    @GET("lookup?entity=song")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    Call<DetailedAlbumModel> getAlbumInfo(@Query("id") Integer id);
}
