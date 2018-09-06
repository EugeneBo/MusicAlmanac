package eugenebo.com.github.musicalmanac.views;

import java.util.List;

public interface Contract {

    interface View {
        void displayMessage(String message);

        void setLoadingIndicator(boolean isLoading);

        <T> void displayData(List<T> data);
    }

    interface Presenter<T> {
        void getData(T parameter);
    }
}
