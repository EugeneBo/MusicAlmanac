package eugenebo.com.github.musicalmanac.views.albumlist;

import android.support.annotation.NonNull;

import eugenebo.com.github.musicalmanac.api.RetrofitServiceFactory;
import eugenebo.com.github.musicalmanac.api.Service;
import eugenebo.com.github.musicalmanac.model.AlbumModel;
import eugenebo.com.github.musicalmanac.views.BasePresenter;
import eugenebo.com.github.musicalmanac.views.Contract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class SearchListPresenter extends BasePresenter {

    SearchListPresenter(Contract.View view) {
        super(view);
    }

    public void startService(Object parameter) {
        String searchTerm = (String) parameter;
        Service service = RetrofitServiceFactory.getServiceInstance();  //create Retrofit service

        Call<AlbumModel> trackModelCall = service.getAlbums(searchTerm);    //getting data from network

        trackModelCall.enqueue(new Callback<AlbumModel>() {     //handling with response
            @Override
            public void onResponse(@NonNull Call<AlbumModel> call,
                                   @NonNull Response<AlbumModel> response) {
                AlbumModel albumModel = response.body();

                if (albumModel != null && albumModel.getResultCount() > 0) {
                    getView().displayData(albumModel.getAlbums());
                } else showMessage("No match! Please, try again.");
            }

            @Override
            public void onFailure(@NonNull Call<AlbumModel> call, @NonNull Throwable t) {
                showMessage("Something goes wrong! Please, try again.");
            }
        });
    }

}


