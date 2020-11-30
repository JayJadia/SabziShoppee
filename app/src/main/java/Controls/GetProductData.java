package Controls;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.TextView;

import com.sabzishoppee.activities.Home;

import java.util.ArrayList;

import model.ProductModel;

/**
 * Created by JAY JADIA.
 */

public class GetProductData extends AsyncTask<String,Void,String>
{
    TextView cartText;
    Session session;
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<ProductModel> productModelList;
    String SERVER_URL = "YOUR SERVER URL";
    String suffixUrl;
    TextView totalCost;
    private Context context;
    private String responseData, tag;

    //Dialog load;

    public GetProductData(Context context, String suffixUrl, String tag, RecyclerView view, SearchView searchView, TextView totalCost)
    {
        this.context=context;
        this.suffixUrl = suffixUrl;
        this.tag = tag;
        this.recyclerView = view;
        session = new Session(context);
        this.searchView = searchView;
        this.totalCost = totalCost;
    }


    @Override
    protected void onPreExecute() {
//        load = ProgressDialog.show(context,"","LOADING...");
    }

    @Override
    protected String doInBackground(String... strings)
    {

        UrlReader reader = new UrlReader();
        responseData = reader.readUrl(SERVER_URL+suffixUrl);

        Log.wtf("RESPONSE", responseData);

       // Toast.makeText(context, "RESPONSE\n"+responseData, Toast.LENGTH_SHORT).show();
        return responseData;
    }

    @Override
    protected void onPostExecute(String s) {
        DataParser parser = new DataParser(this.context);

        //load.dismiss();
        if(tag.equals("login"))
        {
            if (parser.parseLoginResponse(s).equals("Login successful"))
            {
                session.createLoginSession();
                Intent intent = new Intent(context,Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        }
        else if(tag.equals("fruit"))
        {
            if(s!=null)
            {
                productModelList = parser.parseProductResponse(s, recyclerView, "fruit", searchView, totalCost);
            }
        }
        else if(tag.equals("vegetable"))
        {
            if(s!=null)
            {
                productModelList = parser.parseProductResponse(s, recyclerView, "vegetable", searchView, totalCost);
            }
        }
    }
}
