package com.android.laundryservice.data.laundries;

import com.android.laundryservice.model.Laundry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class LaundriesRepositoryImpl implements LaundriesRepository {

    private static final String LAUNDRIES_NODE = "laundries";
    private final DatabaseReference mDatabase;

    public LaundriesRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference(LAUNDRIES_NODE);
    }

    @Override
    public void retrieveLaundriesByRegions(String region, final LaundriesRepositoryCallback callback) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Laundry> laundries = new ArrayList<>();
                for (DataSnapshot laundrySnapshot : dataSnapshot.getChildren()) {
                    Laundry laundry = laundrySnapshot.getValue(Laundry.class);
                    laundry.setId(laundrySnapshot.getKey());
                    laundries.add(laundry);
                }
                callback.onLaundriesRetrieved(laundries);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onLaundriesRetrievedFailed(databaseError.getMessage());
            }
        });
    }
}
