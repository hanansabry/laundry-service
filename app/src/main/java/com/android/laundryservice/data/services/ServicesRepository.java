package com.android.laundryservice.data.services;

import com.android.laundryservice.model.Service;

import java.util.ArrayList;

public interface ServicesRepository {

    interface RetrieveServicesCallback {
        void onServiceRetrievedSuccessfully(ArrayList<Service> services);

        void onServiceRetrievedFailed(String errmsg);
    }

    void retrieveServices(RetrieveServicesCallback callback);
}
