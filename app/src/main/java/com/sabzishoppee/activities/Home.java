package com.sabzishoppee.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sabzishoppee.R;

import java.util.ArrayList;

import Controls.DataParser;
import Controls.DatabaseHelper;
import Controls.InternetCheck;
import Controls.ProductRecyclerAdapter;
import Controls.Session;
import Controls.UrlReader;
import model.ProductModel;

/**
 * Created by JAY JADIA.
 */
public class Home extends AppCompatActivity implements GestureDetector.OnGestureListener {


    private static final float SWIPE_THRESHOLD =100 ;
    private static final float SWIPE_VELOCITY_THRESHOLD =100 ;
    GestureDetectorCompat detector;
    DatabaseHelper helper ;
    FrameLayout frameLayout, veg, fruit;
    boolean doubleBackToExitPressedOnce = false;
    BottomNavigationView navigation;
    ProductRecyclerAdapter adapter;
    String responseData;
    ProductModel productModel;
    private Fragment fragment;
    private Session session;
    private InternetCheck internet;
    private int count = 0;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        session = new Session(this);
        internet = new InternetCheck(this);

        helper = new DatabaseHelper(this);
        detector = new GestureDetectorCompat(this,this);

        frameLayout = findViewById(R.id.container);
        veg = findViewById(R.id.veg_underline);
        fruit = findViewById(R.id.fruit_underline);
        navigation =  findViewById(R.id.navigation);
        productModel = new ProductModel();

        session.setCost(0);
        if(internet.isInternetOn())
        {
            frameLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    detector.onTouchEvent(motionEvent);
                    return true;
                }
            });
            changeScreen(R.id.navigation_vegetable);
        }
        else
        {
            Toast.makeText(this, "Check Network Connection", Toast.LENGTH_SHORT).show();
        }

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                changeScreen(item.getItemId());
                return true;
            }
        });
    }

    void changeScreen(int id) {
        switch (id) {
            case R.id.navigation_vegetable:
                fragment = new VegetableFragment();
                veg.setVisibility(View.VISIBLE);
                fruit.setVisibility(View.INVISIBLE);

                break;

            case R.id.navigation_fruit:
                fragment = new FruitFragment();
                fruit.setVisibility(View.VISIBLE);
                veg.setVisibility(View.INVISIBLE);



                break;


        }
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.product_container, fragment);
            ft.commit();
        }
    }


    @Override
    public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                    System.exit(0);
                    finish();
                }
            }, 1500);

    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();

            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        if(count==1)
                        {
                            changeScreen(R.id.navigation_vegetable);
                            navigation.setSelectedItemId(R.id.navigation_vegetable);
                            count=0;
                        }
                    } else {
                        if(count==0)
                        {
                            changeScreen(R.id.navigation_fruit);
                            navigation.setSelectedItemId(R.id.navigation_fruit);
                            count=1;
                        }
                    }
                    result = true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

}
