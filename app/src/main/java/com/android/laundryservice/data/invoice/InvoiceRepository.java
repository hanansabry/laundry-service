package com.android.laundryservice.data.invoice;

import com.android.laundryservice.model.ServiceItem;

import java.util.ArrayList;

public interface InvoiceRepository {

    void addNewServiceItem(ServiceItem serviceItem);

    void removeServiceItem(ServiceItem serviceItem);

    double getServiceItemTotalCost(ServiceItem serviceItem);

    void increaseServiceItemQuantity(ServiceItem serviceItem);

    void decreaseServiceItemQuantity(ServiceItem serviceItem);

    int getServiceItemQuantity(ServiceItem serviceItem);

    double getServiceItemsTotalCost();
}
