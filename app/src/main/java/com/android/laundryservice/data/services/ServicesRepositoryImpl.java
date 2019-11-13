package com.android.laundryservice.data.services;

import com.android.laundryservice.model.Service;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ServicesRepositoryImpl implements ServicesRepository {

    private static final String SERVICES_NODE = "services";
    private final DatabaseReference mDatabase;

    public ServicesRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance().getReference(SERVICES_NODE);
    }

    @Override
    public void retrieveServices(final RetrieveServicesCallback callback) {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Service> services = new ArrayList<>();
                for (DataSnapshot serviceSnapshot : dataSnapshot.getChildren()) {
                    Service service = serviceSnapshot.getValue(Service.class);
                    service.setId(serviceSnapshot.getKey());
                    services.add(service);
                }
                callback.onServiceRetrievedSuccessfully(services);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onServiceRetrievedFailed(databaseError.getMessage());
            }
        });
    }
}
