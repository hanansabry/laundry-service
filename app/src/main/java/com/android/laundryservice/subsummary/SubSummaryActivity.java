package com.android.laundryservice.subsummary;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.laundryservice.Injection;
import com.android.laundryservice.R;
import com.android.laundryservice.select_location.MapsActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SubSummaryActivity extends AppCompatActivity {

    private SummaryPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_summary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new SummaryPresenter(Injection.provideInvoiceRepository(getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)));
        initializeInvoiceItemsRecyclerView();

        TextView totalCostTextView = findViewById(R.id.total_cost_textview);
        totalCostTextView.setText(presenter.getTotalCost() + " KWD");
    }

    private void initializeInvoiceItemsRecyclerView() {
        RecyclerView invoiceItemsRecyclerView = findViewById(R.id.invoice_items_summary_recylerview);
        invoiceItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        SubSummaryAdapter subSummaryAdapter = new SubSummaryAdapter(presenter);
        invoiceItemsRecyclerView.setAdapter(subSummaryAdapter);
        subSummaryAdapter.setInvoiceItems();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    public void onAddMoreClicked(View view) {
        onBackPressed();
    }

    public void onSelectLocationClicked(View view) {
        startActivity(new Intent(this, MapsActivity.class));
    }
}
