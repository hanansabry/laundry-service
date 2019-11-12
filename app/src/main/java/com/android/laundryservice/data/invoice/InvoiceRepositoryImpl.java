package com.android.laundryservice.data.invoice;

import android.content.SharedPreferences;

import com.android.laundryservice.model.InvoiceItem;
import com.android.laundryservice.model.Service;
import com.android.laundryservice.model.ServiceItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class InvoiceRepositoryImpl implements InvoiceRepository {
    private final SharedPreferences sharedPreferences;

    public InvoiceRepositoryImpl(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }


    @Override
    public void addNewServiceItem(ServiceItem serviceItem) {
        serviceItems.add(serviceItem);
    }

    @Override
    public void removeServiceItem(ServiceItem serviceItem) {
        serviceItems.remove(serviceItem);
    }

    @Override
    public double getServiceItemTotalCost(ServiceItem serviceItem) {
        return serviceItem.getUnitPrice() * getServiceItemQuantity(serviceItem);
    }

    @Override
    public void increaseServiceItemQuantity(ServiceItem serviceItem) {
        int quantity = sharedPreferences.getInt(serviceItem.getId(), 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(serviceItem.getId(), ++quantity);
        editor.apply();

        //add this item to the final invoice
        if (quantity > 0 && !serviceItems.contains(serviceItem)) {
            addNewServiceItem(serviceItem);
        }
    }

    @Override
    public void decreaseServiceItemQuantity(ServiceItem serviceItem) {
        int quantity = getServiceItemQuantity(serviceItem);
        if (quantity > 0) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(serviceItem.getId(), --quantity);
            editor.apply();
        } else {
            removeServiceItem(serviceItem);
        }
    }

    @Override
    public int getServiceItemQuantity(ServiceItem serviceItem) {
        return sharedPreferences.getInt(serviceItem.getId(), 0);
    }

    @Override
    public double getServiceItemsTotalCost() {
        double total = 0;
        for (ServiceItem serviceItem : serviceItems) {
            total += serviceItem.getUnitPrice() * getServiceItemQuantity(serviceItem);
        }
        return total;
    }

    @Override
    public ArrayList<InvoiceItem> getInvoiceItems() {
        ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();
        HashMap<Service, ArrayList<ServiceItem>> serviceItemsMap = new HashMap<>();

        for (ServiceItem serviceItem : InvoiceRepository.serviceItems) {
            ArrayList<ServiceItem> serviceItems = new ArrayList<>();
            Service service = getMapService(serviceItem.getService(), serviceItemsMap);
            if (service == null) {
                service = serviceItem.getService();
                serviceItems = new ArrayList<>();
                serviceItems.add(serviceItem);
            } else {
                serviceItems = serviceItemsMap.get(service);
                if (!isItemAddedBefore(serviceItem, serviceItems)) {
                    serviceItems.add(serviceItem);
                }
            }
            serviceItemsMap.put(service, serviceItems);
        }

        for (Service service : serviceItemsMap.keySet()) {
            ArrayList<ServiceItem> serviceItems = serviceItemsMap.get(service);
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setService(service);
            invoiceItem.setSummary(String.format(Locale.US, "%d items selected", serviceItems.size()));

            double itemTotalCost = 0;
            for (ServiceItem serviceItem : serviceItems) {
                itemTotalCost += getServiceItemTotalCost(serviceItem);
            }
            invoiceItem.setTotalCost(itemTotalCost);
            invoiceItems.add(invoiceItem);
        }
        return invoiceItems;
    }

    private boolean isItemAddedBefore(ServiceItem serviceItem, ArrayList<ServiceItem> serviceItems) {
        for (ServiceItem item : serviceItems) {
            if (item.getId().equalsIgnoreCase(serviceItem.getId())) {
                return true;
            }
        }
        return false;
    }

    private Service getMapService(Service currentService, HashMap<Service, ArrayList<ServiceItem>> serviceItemsMap) {
        for (Service service : serviceItemsMap.keySet()) {
            if (service.getId().equalsIgnoreCase(currentService.getId())) {
                return service;
            }
        }
        return null;
    }

    private boolean isServiceAddedBefore(Service currentService, HashMap<Service, ArrayList<ServiceItem>> serviceArrayListMap) {
        for (Service service : serviceArrayListMap.keySet()) {
            if (service.getId().equalsIgnoreCase(currentService.getId())) {
                return true;
            }
        }
        return false;
    }
}
