package com.android.laundryservice.data.laundries;

import android.graphics.Region;

import com.android.laundryservice.model.Laundry;

import java.util.ArrayList;

public interface LaundriesRepository {

    interface LaundriesRepositoryCallback {
        void onLaundriesRetrieved(ArrayList<Laundry> laundries);

        void onLaundriesRetrievedFailed(String errmsg);
    }

    void retrieveLaundriesByRegions(String region, LaundriesRepositoryCallback callback);
}
