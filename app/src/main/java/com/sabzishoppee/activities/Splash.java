package com.sabzishoppee.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.sabzishoppee.R;

import Controls.GetProductData;
import Controls.InternetCheck;
import Controls.Session;

/**
 * Created by JAY JADIA.
 */
public class Splash extends AppCompatActivity {

    Session session;
    InternetCheck internet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        session = new Session(this);
        internet = new InternetCheck(this);

        Thread timer = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    if(session.loggedin())
                    {
                        startActivity(new Intent(Splash.this,Home.class));
                        finish();
                    }
                    else
                    {
                        startActivity(new Intent(Splash.this,Login.class));
                        finish();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        if(internet.isInternetOn())
        {
            timer.start();
        }
        else
            {
            Toast.makeText(this, "Check your Internet Connection and restart the app", Toast.LENGTH_SHORT).show();
        }
    }
}
