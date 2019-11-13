package com.android.laundryservice.summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.laundryservice.R;
import com.android.laundryservice.services.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        //remove old shared preferences
        getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE).edit().clear().apply();
    }

    public void GoToHomeScreen(View view) {
        Intent homeIntent = new Intent(this, HomeActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }
}
