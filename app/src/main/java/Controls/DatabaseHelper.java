package Controls;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JAY JADIA.
 */

public class DatabaseHelper  extends SQLiteOpenHelper{

    public final static String DB_NAME = "DB_NAME";
    public final static String TABLE_NAME = "PRODUCT";
    public final static String COL_ID = "id";
    public final static String COL_NAME = "name";
    public final static String COL_TYPE = "product_type" ;
    public final static String COL_RATE_TYPE = "rate_type";
    public final static String COL_QUANTITY = "quantity";
    public final static String COL_MIN_QUANT = "min_quantity";
    public final static String COL_IMG_URL = "image_url";
    public final static String COL_RATE = "rate" ;
    public final static int DB_VERSION = 1;

    String CREATE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+" ("  +COL_ID+" INTEGER ,"+COL_NAME+" VARCHAR(100), "+ COL_RATE +
            " INTEGER , "+ COL_RATE_TYPE+" VARCHAR(10), " +COL_TYPE +" VARCHAR(10), "+ COL_QUANTITY + " INTEGER ,"
            + COL_MIN_QUANT + " INTEGER ," +COL_IMG_URL+" VARCHAR(500));";

    Context context;
    SQLiteDatabase database;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.database = sqLiteDatabase;
        database.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
        this.onCreate(database);
    }

    public void insertProduct(int id, String name, int rate, int quantity, int minQuantity, String rateType,
                              String productType,String imageUrl)
    {

        if(checkItem(id,quantity).size()==0) {
            database = getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(COL_ID, id);
            values.put(COL_NAME, name);
            values.put(COL_RATE, rate);
            values.put(COL_RATE_TYPE, rateType);
            values.put(COL_QUANTITY, quantity);
            values.put(COL_MIN_QUANT, minQuantity);
            values.put(COL_TYPE, productType);
            values.put(COL_IMG_URL, imageUrl);

            database.insert(TABLE_NAME, null, values);

            database.close();
        }

    }

    public ArrayList<ArrayList<String>> readProducts()
    {
        database = getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+";";

        ArrayList<ArrayList<String>> productList = new ArrayList<>();

        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> product = new ArrayList<>();

                for(int i=0;i<8;i++)
                {
                    product.add(i,cursor.getString(i));
                }
                productList.add(product);
            }
            while (cursor.moveToNext());
        }

        database.close();
        return productList;
    }

    public void deleteItem(int id,String imgUrl, int quantity)
    {
        String query = "DELETE FROM "+ TABLE_NAME+" WHERE "+COL_ID+ " = "+id+" AND "+COL_IMG_URL+" = '"+imgUrl+"' AND "
                +COL_QUANTITY+" = "+ quantity+";";

        database = this.getWritableDatabase();
        database.execSQL(query);
        database.close();
    }

    public void deleteAll()
    {
            database= this.getWritableDatabase();
            String query = "DELETE FROM "+ TABLE_NAME+";";
            database.execSQL(query);
            database.close();
    }

    public void updateQuantity(int id, int quantity)
    {
        database = this.getWritableDatabase();
        String query =  "UPDATE "+TABLE_NAME+" SET " +COL_QUANTITY + " = " +quantity+" WHERE "+COL_ID+" = "+ id+";" ;
        database.execSQL(query);
        database.close();
    }

    public ArrayList<String> checkItem(int id, int quantity)
    {
        database = this.getReadableDatabase();
        String query = "SELECT * FROM "+TABLE_NAME+" WHERE "+COL_ID+" = "+id+";";

        ArrayList<String> product = new ArrayList<>();

        Cursor cursor = database.rawQuery(query,null);

        if (cursor.moveToFirst()) {
            do {
                product = new ArrayList<>();

                for(int i=0;i<8;i++)
                {
                    product.add(i,cursor.getString(i));
                }
                updateQuantity(id,quantity);

            }
            while (cursor.moveToNext());
        }

     return product;
    }

}
