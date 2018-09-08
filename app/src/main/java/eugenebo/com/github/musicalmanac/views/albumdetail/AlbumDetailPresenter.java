package eugenebo.com.github.musicalmanac.views.albumdetail;

import android.support.annotation.NonNull;
import eugenebo.com.github.musicalmanac.api.Service;
import eugenebo.com.github.musicalmanac.api.RetrofitServiceFactory;
import eugenebo.com.github.musicalmanac.model.DetailedAlbumModel;
import eugenebo.com.github.musicalmanac.views.BasePresenter;
import eugenebo.com.github.musicalmanac.views.Contract;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumDetailPresenter extends BasePresenter {

    AlbumDetailPresenter(Contract.View view) {
        super(view);
    }

    public void startService(Object parameter) {
        Integer albumID = (Integer) parameter;
        Service service = RetrofitServiceFactory.getServiceInstance();  //create Retrofit service
        Call<DetailedAlbumModel> trackModelCall = service.getAlbumInfo(albumID); //getting data from network

        trackModelCall.enqueue(new Callback<DetailedAlbumModel>() {    //handling with response
            @Override
            public void onResponse(@NonNull Call<DetailedAlbumModel> call,
                                   @NonNull Response<DetailedAlbumModel> response) {

                DetailedAlbumModel detailedAlbumModel = response.body();

                if (detailedAlbumModel!=null && detailedAlbumModel.getResultCount() > 0) {
                    getView().displayData(detailedAlbumModel.getSongs());
                } else {
                    showMessage("No match! Please, try again.");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DetailedAlbumModel> call, @NonNull Throwable t) {
                showMessage("Something goes wrong! Please, try again.");
            }
        });
    }

}
