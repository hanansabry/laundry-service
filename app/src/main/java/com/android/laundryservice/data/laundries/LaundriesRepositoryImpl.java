package com.android.laundryservice.data.laundries;

import android.graphics.Region;

import com.android.laundryservice.model.Laundry;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class LaundriesRepositoryImpl implements LaundriesRepository {

    @Override
    public void retrieveLaundriesByRegions(String region, LaundriesRepositoryCallback callback) {
        ArrayList<Laundry> laundries = getLaundries();
        ArrayList<Laundry> regionLaundries = new ArrayList<>();
        for (Laundry  laundry : laundries) {
            if (laundry.getRegion().equalsIgnoreCase(region)) {
                regionLaundries.add(laundry);
            }
        }
        callback.onLaundriesRetrieved(regionLaundries);
    }

    private ArrayList<Laundry> getLaundries() {
        ArrayList<Laundry> laundries = new ArrayList<>();
        Laundry l1 = new Laundry();
        l1.setId("1");
        l1.setName("Laundry Name 1");
        l1.setLocation(new LatLng(29.370571, 47.984290));
        l1.setRegion("Kuwait City");

        Laundry l2 = new Laundry();
        l2.setId("2");
        l2.setName("Laundry Name 2");
        l2.setLocation(new LatLng(29.371929, 47.977399));
        l2.setRegion("Kuwait City");

        Laundry l3 = new Laundry();
        l3.setId("3");
        l3.setName("Laundry Name 3");
        l3.setLocation(new LatLng(29.373518, 47.975275));
        l3.setRegion("Kuwait City");
        ////////////////////////////////////////////////////////////////////////////////////////////
        Laundry l4 = new Laundry();
        l4.setId("4");
        l4.setName("Laundry Name 4");
        l4.setLocation(new LatLng(29.388224, 47.999825));
        l4.setRegion("Dasmān");

        Laundry l5 = new Laundry();
        l5.setId("5");
        l5.setName("Laundry Name 5");
        l5.setLocation(new LatLng(29.389586, 48.002515));
        l5.setRegion("Dasmān");

        Laundry l6 = new Laundry();
        l6.setId("6");
        l6.setName("Laundry Name 6");
        l6.setLocation(new LatLng(29.391420, 47.998825));
        l6.setRegion("Dasmān");

        ////////////////////////////////////////////////////////////////////////////////////////////
        Laundry l7 = new Laundry();
        l7.setId("7");
        l7.setName("Laundry Name 7");
        l7.setLocation(new LatLng(29.350737, 48.000923));
        l7.setRegion("Qadsiya");

        Laundry l8 = new Laundry();
        l8.setId("8");
        l8.setName("Laundry Name 8");
        l8.setLocation(new LatLng(29.350066, 48.004285));
        l8.setRegion("Qadsiya");

        Laundry l9 = new Laundry();
        l9.setId("9");
        l9.setName("Laundry Name 9");
        l9.setLocation(new LatLng(29.349960, 47.996599));
        l9.setRegion("Qadsiya");

        laundries.add(l1);
        laundries.add(l2);
        laundries.add(l3);
        laundries.add(l4);
        laundries.add(l5);
        laundries.add(l6);
        laundries.add(l7);
        laundries.add(l8);
        laundries.add(l9);

        return laundries;
    }
}
