package com.android.laundryservice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.laundryservice.login.LoginActivity;
import com.android.laundryservice.services.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-ServiceListActivity. */
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    Intent mainIntent = new Intent(SplashScreen.this, HomeActivity.class);
                    startActivity(mainIntent);
                } else {
                    Intent mainIntent = new Intent(SplashScreen.this, LoginActivity.class);
                    startActivity(mainIntent);
                }
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
