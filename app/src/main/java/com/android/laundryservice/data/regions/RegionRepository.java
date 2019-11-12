package com.android.laundryservice.data.regions;

import android.graphics.Region;

import java.util.ArrayList;

public interface RegionRepository {

    interface RegionRepositoryCallback {
        void onRegionsRetrieved(ArrayList<String> regions);

        void onRegionsRetrievedFailed(String errmsg);
    }

    void retrieveRegions(RegionRepositoryCallback callback);
}
