package Controls;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

/**
 * Created by JAY JADIA.
 */

public class OrderAsync extends AsyncTask<String,Void,String> {
    String responseData = "", SERVER_URL = "SERVER_URL";
    String suffixUrl;

    Context context;
    String tag;
    RecyclerView recyclerView;

    public OrderAsync(String suffixUrl, Context context, String tag, RecyclerView recyclerView) {
        this.suffixUrl=suffixUrl;
        this.context= context;
        this.tag = tag;
        this.recyclerView = recyclerView;
    }

    @Override
    protected String doInBackground(String... strings) {
        UrlReader reader = new UrlReader();
        responseData = reader.readUrl(SERVER_URL+suffixUrl);
        return responseData;
    }

    @Override
    protected void onPostExecute(String s) {
        DataParser parser = new DataParser(context);
        parser.parseOrderResponse(s,tag,recyclerView);
    }
}
