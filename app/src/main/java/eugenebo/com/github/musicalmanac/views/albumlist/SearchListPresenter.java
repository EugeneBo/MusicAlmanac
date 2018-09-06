package eugenebo.com.github.musicalmanac.views.albumlist;

import android.support.annotation.NonNull;
import eugenebo.com.github.musicalmanac.api.AlbumsSearchService;
import eugenebo.com.github.musicalmanac.api.SearchServiceFactory;
import eugenebo.com.github.musicalmanac.model.AlbumModel;
import eugenebo.com.github.musicalmanac.views.Contract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SearchListPresenter implements Contract.Presenter {

    private Contract.View albumListView;

    SearchListPresenter(Contract.View albumListView) {
        this.albumListView = albumListView;
    }

    @Override
    public void getData(Object parameter) {
        String searchTerm = (String) parameter;

        AlbumsSearchService service = SearchServiceFactory.getInstance();
        Call<AlbumModel> trackModelCall = service.getAlbums(searchTerm);

        trackModelCall.enqueue(new Callback<AlbumModel>() {
            @Override
            public void onResponse(@NonNull Call<AlbumModel> call,
                                   @NonNull Response<AlbumModel> response) {

                AlbumModel albumModel = response.body();

                if (albumModel != null && albumModel.getResultCount() > 0 ) {
                    albumListView.displayData(albumModel.getAlbums());
                } else showFailureMessage();
            }

            @Override
            public void onFailure(@NonNull Call<AlbumModel> call, @NonNull Throwable t) {
                showFailureMessage();
            }
        });
    }

    private void showFailureMessage() {
        albumListView.displayMessage("No match! Please, try again.");
    }

}