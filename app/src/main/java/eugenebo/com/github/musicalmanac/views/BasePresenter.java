package eugenebo.com.github.musicalmanac.views;

import android.support.v4.app.Fragment;

import eugenebo.com.github.musicalmanac.utils.NetworkCheckUtilClass;

public abstract class BasePresenter implements Contract.Presenter {

    private Contract.View view;

    public BasePresenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void getData(Object parameter) {
        if (NetworkCheckUtilClass.isOnline(((Fragment) view).getContext())) {
            startService(parameter);
        } else showMessage("Check internet connection!");
    }

    public abstract void startService(Object parameter);

    protected void showMessage(String message) {
        view.displayMessage(message);
    }

    public Contract.View getView() {
        return view;
    }
}
