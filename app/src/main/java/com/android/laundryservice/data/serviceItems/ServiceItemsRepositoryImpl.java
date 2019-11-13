package com.android.laundryservice.data.serviceItems;

import com.android.laundryservice.model.Service;
import com.android.laundryservice.model.ServiceItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class ServiceItemsRepositoryImpl implements ServiceItemsRepository {

    private static final String SERVICE_ITEMS_NODE = "service_items";
    private static final String SERVICES_NODE = "services";
    private final FirebaseDatabase mDatabase;

    public ServiceItemsRepositoryImpl() {
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void retrieveServiceItemsByService(final String serviceId, final RetrieveServiceItemsCallback callback) {

        DatabaseReference servicesRef = mDatabase.getReference(SERVICES_NODE);
        final DatabaseReference serviceItemsRef = mDatabase.getReference(SERVICE_ITEMS_NODE);

        //first get service
        servicesRef.child(serviceId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final Service service = dataSnapshot.getValue(Service.class);
                service.setId(serviceId);

                //get items of this service
                serviceItemsRef.child(serviceId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ArrayList<ServiceItem> serviceItems = new ArrayList<>();
                        for (DataSnapshot serviceItemSnapshot : dataSnapshot.getChildren()) {
                            ServiceItem serviceItem = serviceItemSnapshot.getValue(ServiceItem.class);
                            serviceItem.setId(serviceItemSnapshot.getKey());
                            serviceItem.setService(service);
                            serviceItems.add(serviceItem);
                        }
                        callback.onServiceItemsRetrievedSuccessfully(serviceItems);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onServiceItemsRetrievedFailed(databaseError.getMessage());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onServiceItemsRetrievedFailed(databaseError.getMessage());
            }
        });
    }
}
