package com.android.laundryservice.select_location;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.laundryservice.Injection;
import com.android.laundryservice.R;
import com.android.laundryservice.data.laundries.LaundriesRepository;
import com.android.laundryservice.data.regions.RegionRepository;
import com.android.laundryservice.model.Laundry;
import com.android.laundryservice.summary.InvoiceSummary;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, RegionRepository.RegionRepositoryCallback, LaundriesRepository.LaundriesRepositoryCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private MapsPresenter presenter;
    private ArrayList<Laundry> laundries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        presenter = new MapsPresenter(Injection.provideMapsUseCaseHandler());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Move camera to kuwait
        LatLng Kuwait = new LatLng(29.380813, 48.012446);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Kuwait, 10));
        mMap.setOnMarkerClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.retrieveRegions(this);
    }

    @Override
    public void onRegionsRetrieved(ArrayList<String> regions) {
        RegionsAdapter regionsAdapter = new RegionsAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        regionsAdapter.add("Select Region");
        for (String region : regions) {
            regionsAdapter.add(region);
        }
        Spinner regionsSpinner = findViewById(R.id.regions_spinner);
        regionsSpinner.setAdapter(regionsAdapter);
        regionsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedRegion = (String) parent.getItemAtPosition(position);
                if (position > 0) {
                    Toast.makeText(getApplicationContext(), "Selected : " + selectedRegion, Toast.LENGTH_SHORT).show();
                    presenter.retrieveLaundriesByRegion(selectedRegion, MapsActivity.this);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onRegionsRetrievedFailed(String errmsg) {
        Toast.makeText(this, "Can't retrieve available regions\n" + errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLaundriesRetrieved(ArrayList<Laundry> laundries) {
        this.laundries = laundries;
        if (laundries != null && laundries.size() > 0) {
            LatLng latLng = null;
            for (Laundry laundry : laundries) {
                latLng = new LatLng(laundry.getLocation().getLatitude(), laundry.getLocation().getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(laundry.getName()));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        } else {
            Toast.makeText(this, "This region has no laundries available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLaundriesRetrievedFailed(String errmsg) {
        Toast.makeText(this, errmsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        final String laundryName = marker.getTitle();
        new AlertDialog.Builder(this)
                .setTitle("Select Laundry")
                .setMessage(String.format(Locale.US,"You are going to select %s", laundryName))
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        goToInvoiceSummaryScreen(laundryName);
                    }
                })
                .setNegativeButton("Change Laundry", null)
                .show();
        return false;
    }

    private void goToInvoiceSummaryScreen(String laundryName) {
        Laundry selectedLaundry = getSelectedLaundry(laundryName);
        Intent intent = new Intent(this, InvoiceSummary.class);
        intent.putExtra(Laundry.class.getName(), selectedLaundry);
        startActivity(intent);
    }

    private Laundry getSelectedLaundry(String laundryName) {
        for (Laundry laundry : laundries) {
            if (laundry.getName().equalsIgnoreCase(laundryName)) {
                return laundry;
            }
        }
        return null;
    }

    class RegionsAdapter extends ArrayAdapter<String> {

        public RegionsAdapter(@NonNull Context context, int resource) {
            super(context, resource);
        }

        @Override
        public boolean isEnabled(int position) {
            if (position == 0) {
                return false;
            } else {
                return true;
            }
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if (position == 0) {
                // Set the hint text color gray
                tv.setTextColor(Color.GRAY);
            } else {
                tv.setTextColor(Color.BLACK);
            }
            return view;
        }
    }
}
