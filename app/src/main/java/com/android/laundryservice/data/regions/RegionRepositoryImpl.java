package com.android.laundryservice.data.regions;

import android.graphics.Region;

import java.util.ArrayList;

public class RegionRepositoryImpl implements RegionRepository {

    @Override
    public void retrieveRegions(RegionRepositoryCallback callback) {
        ArrayList<String> regions = new ArrayList<>();
        regions.add("Kuwait City");
        regions.add("Dasmān");
        regions.add("Sharq");
        regions.add("Mirgāb");
        regions.add("Jibla");
        regions.add("Qadsiya");
        regions.add("Mansūriya");
        regions.add("Salhiya");
        regions.add("Qurtuba");

        callback.onRegionsRetrieved(regions);
    }
}
