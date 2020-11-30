package Controls;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import model.ProductModel;

/**
 * Created by JAY JADIA.
 */

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context ctx;

    String jsonData;
    JSONArray jsonArray;


    public Session(Context ctx)
    {
        this.ctx = ctx;
        sharedPreferences=ctx.getSharedPreferences("UserInfo",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }
    public void createLoginSession(){

        editor.putBoolean("loggedInmode",true);
        editor.commit();
    }
    public void logoutUser(){

        editor.putBoolean("loggedInmode",false);
        editor.clear();
        editor.commit();
    }
    public boolean loggedin(){

        return  sharedPreferences.getBoolean("loggedInmode",false);
    }

    public void storeProducts(String jsonData)
    {
        this.jsonData = jsonData;
        editor.putString("productsList",jsonData);
        editor.commit();
    }

    public String getProducts()
    {
        return sharedPreferences.getString("productsList",null);
    }


    public void addUser(int id,String username,String address)
    {
        editor.putInt("user_id",id);
        editor.putString("user_name",username);
        editor.putString("address",address);
        editor.commit();
    }

    public int getUserId()
    {
        return sharedPreferences.getInt("user_id",0);
    }
    public String getUserName()
    {
        return sharedPreferences.getString("user_name",null);
    }
    public String getAddress()
    {
        return sharedPreferences.getString("address",null);
    }

    public int getCost() {
        return sharedPreferences.getInt("total_cost", 0);
    }

    public void setCost(int cost) {
        editor.putInt("total_cost", cost);
        editor.commit();
    }
}

