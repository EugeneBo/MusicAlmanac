package eugenebo.com.github.musicalmanac.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*Simple utility class to provide
  connection state of the device to the network.*/
public class NetworkCheckUtilClass {

    public static boolean isOnline(Context context){
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectManager != null) networkInfo = connectManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}
