package com.android.laundryservice.summary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.laundryservice.Injection;
import com.android.laundryservice.R;
import com.android.laundryservice.model.Laundry;
import com.android.laundryservice.subsummary.SubSummaryAdapter;
import com.android.laundryservice.subsummary.SummaryPresenter;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InvoiceSummary extends AppCompatActivity {

    private SummaryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_summary);

        presenter = new SummaryPresenter(Injection.provideInvoiceRepository(getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)));

        Laundry selectedLaundry = getIntent().getExtras().getParcelable(Laundry.class.getName());
        setLaundryLocationImage(selectedLaundry);
        setLaundryName(selectedLaundry);

        initializeInvoiceItemsRecyclerView();
        setTotalCost();
    }

    private void initializeInvoiceItemsRecyclerView() {
        RecyclerView invoiceItemsRecyclerView = findViewById(R.id.invoice_items_summary_recylerview);
        invoiceItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SubSummaryAdapter subSummaryAdapter = new SubSummaryAdapter(presenter);
        invoiceItemsRecyclerView.setAdapter(subSummaryAdapter);
        subSummaryAdapter.setInvoiceItems();
    }

    private void setLaundryName(Laundry selectedLaundry) {
        TextView laundryName = findViewById(R.id.laundry_name_textview);
        laundryName.setText(selectedLaundry.getName());
    }

    private void setTotalCost() {
        TextView totalCostTextView = findViewById(R.id.total_cost_textview);
        totalCostTextView.setText(presenter.getTotalCost() + " KWD");
    }

    public void onFinishClicked(View view) {
        Intent homeIntent = new Intent(this, PaymentActivity.class);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(homeIntent);
    }

    private void setLaundryLocationImage(Laundry laundry) {
        ImageView mapImage = findViewById(R.id.map_image);
        LatLng location = laundry.getLocation();
        String mapImageUrl = "https://maps.googleapis.com/maps/api/staticmap?key=" + getString(R.string.google_api_key)
                + "&size=360x180&path=" + location.latitude + "," + location.longitude + "&zoom=18";
        Picasso.get()
                .load(mapImageUrl)
                .into(mapImage);
    }
}
