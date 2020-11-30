package com.sabzishoppee.activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sabzishoppee.R;

import Controls.GetProductData;
import Controls.InternetCheck;
import Controls.Session;
import model.LoginModel;

/**
 * Created by JAY JADIA.
 */
public class Login extends AppCompatActivity {


    EditText login, password;
    Button loginButton;

    InternetCheck internet;
    Session session;
    boolean doubleBackToExitPressedOnce= false;

    public static final String WEB_SERVICE_URL = "login web service URL"; // add your web service url

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }


    void init() {
        login = findViewById(R.id.login_id);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);

        session = new Session(this);
        internet =new InternetCheck(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Login.this, "Login", Toast.LENGTH_SHORT).show();

                LoginModel loginModel = new LoginModel();
                loginModel.setUsername(login.getText().toString().trim());
                loginModel.setPassword(password.getText().toString().trim());

                if(internet.isInternetOn())
                {
                    String suffix = WEB_SERVICE_URL + "?username=" + login.getText().toString().trim()
                            + "&password=" + password.getText().toString().trim();

                    GetProductData data = new GetProductData(getApplicationContext(), suffix, "login", null, null, null);
                    data.execute();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
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
}