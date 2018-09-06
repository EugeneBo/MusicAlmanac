package eugenebo.com.github.musicalmanac.views.albumdetail;

import android.support.annotation.NonNull;
import android.util.Log;
import java.util.List;
import eugenebo.com.github.musicalmanac.api.AlbumLookupService;
import eugenebo.com.github.musicalmanac.api.LookupServiceFactory;
import eugenebo.com.github.musicalmanac.model.DetailedAlbumModel;
import eugenebo.com.github.musicalmanac.model.Song;
import eugenebo.com.github.musicalmanac.views.Contract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDetailPresenter implements Contract.Presenter {
    private Contract.View songListView;

    AlbumDetailPresenter(Contract.View songListView) {
        this.songListView = songListView;
    }

    @Override
    public void getData(Object parameter) {
        Integer albumID = (Integer) parameter;

        AlbumLookupService service = LookupServiceFactory.getInstance();
        Call<DetailedAlbumModel> trackModelCall = service.getAlbumInfo(albumID);

        trackModelCall.enqueue(new Callback<DetailedAlbumModel>() {
            @Override
            public void onResponse(@NonNull Call<DetailedAlbumModel> call,
                                   @NonNull Response<DetailedAlbumModel> response) {

                DetailedAlbumModel detailedAlbumModel = response.body();
                List<Song> songs = detailedAlbumModel.getSongs();

                for (Song s : songs) {
                    if (s.getTrackName() != null) {
                        Log.d("AlbumDetailPresenter", s.getTrackName());
                    }
                }

                if (detailedAlbumModel.getResultCount() > 0) {
                    songListView.displayData(detailedAlbumModel.getSongs());
                } else {
                    showFailureMessage();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailedAlbumModel> call, @NonNull Throwable t) {
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        songListView.displayMessage("Something goes wrong! Please, try again.");
    }
}
