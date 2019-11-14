package com.android.laundryservice.data.regions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class RegionRepositoryImpl implements RegionRepository {

    private static final String REGIONS_NODE = "regions";
    private final DatabaseReference mDatabase;

    public RegionRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference(REGIONS_NODE);
    }

    @Override
    public void retrieveRegions(final RegionRepositoryCallback callback) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> regions = new ArrayList<>();
                for (DataSnapshot regionSnapshot : dataSnapshot.getChildren()) {
                    String regionName = regionSnapshot.getValue(String.class);
                    regions.add(regionName);
                }
                callback.onRegionsRetrieved(regions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onRegionsRetrievedFailed(databaseError.getMessage());
            }
        });
    }
}
