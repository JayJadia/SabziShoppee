package Controls;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by JAY JADIA.
 */

public class InternetCheck
{
    Context context;
    public InternetCheck(Context context)
    {
        this.context = context;
    }
    public boolean isInternetOn() {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conn.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }
}
