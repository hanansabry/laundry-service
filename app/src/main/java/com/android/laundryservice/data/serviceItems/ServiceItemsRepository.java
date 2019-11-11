package com.android.laundryservice.data.serviceItems;

import com.android.laundryservice.data.services.ServicesRepository;
import com.android.laundryservice.model.Service;
import com.android.laundryservice.model.ServiceItem;

import java.util.ArrayList;

public interface ServiceItemsRepository {

    interface RetrieveServiceItemsCallback {
        void onServiceItemsRetrievedSuccessfully(ArrayList<ServiceItem> serviceItems);

        void onServiceItemsRetrievedFailed(String errmsg);
    }

    void retrieveServiceItemsByService(String serviceId, ServiceItemsRepository.RetrieveServiceItemsCallback callback);
}
