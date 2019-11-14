package com.android.laundryservice.services.serviceItems;

import com.android.laundryservice.data.invoice.InvoiceRepository;
import com.android.laundryservice.data.serviceItems.ServiceItemsRepository;
import com.android.laundryservice.model.ServiceItem;

import java.util.ArrayList;

public class ServiceItemsPresenter {

    private ArrayList<ServiceItem> serviceItems = new ArrayList<>();
    private final ServiceItemsRepository serviceItemsRepository;
    private final InvoiceRepository invoiceRepository;
    private final ServiceItemsFragment fragment;

    public ServiceItemsPresenter(ServiceItemsFragment fragment, ServiceItemsRepository serviceItemsRepository, InvoiceRepository invoiceRepository) {
        this.serviceItemsRepository = serviceItemsRepository;
        this.invoiceRepository = invoiceRepository;
        this.fragment = fragment;
    }

    public void bindServices(ArrayList<ServiceItem> serviceItems) {
        this.serviceItems = serviceItems;
    }

    public void onBindServiceItemRowViewAtPosition(ServiceItemsAdapter.ServiceItemViewHolder holder, int position) {
        ServiceItem serviceItem = serviceItems.get(position);
        holder.setServiceItemName(serviceItem.getName());
        holder.setItemUnitPrice(serviceItem.getUnitPrice());
        holder.setItemQuantity(invoiceRepository.getServiceItemQuantity(serviceItem));
        holder.setItemTotalCost(invoiceRepository.getServiceItemTotalCost(serviceItem));
    }

    public int getServiceItemsSize() {
        return serviceItems.size();
    }

    public void retrieveServiceItems(String serviceId, ServiceItemsRepository.RetrieveServiceItemsCallback callback) {
        serviceItemsRepository.retrieveServiceItemsByService(serviceId, callback);
    }

    public void increaseQuantity(ServiceItemsAdapter.ServiceItemViewHolder holder, int itemPosition) {
        ServiceItem serviceItem = serviceItems.get(itemPosition);
        invoiceRepository.increaseServiceItemQuantity(serviceItem);
        holder.setItemQuantity(invoiceRepository.getServiceItemQuantity(serviceItem));
        setItemTotalCost(holder, serviceItem);
        updateTotalServiceItemsCost();
        fragment.enableDisableDoneButton(InvoiceRepository.serviceItems.size() > 0);
    }

    public void decreaseQuantity(ServiceItemsAdapter.ServiceItemViewHolder holder, int itemPosition) {
        ServiceItem serviceItem = serviceItems.get(itemPosition);
        invoiceRepository.decreaseServiceItemQuantity(serviceItem);
        holder.setItemQuantity(invoiceRepository.getServiceItemQuantity(serviceItem));
        setItemTotalCost(holder, serviceItem);
        updateTotalServiceItemsCost();
        fragment.enableDisableDoneButton(InvoiceRepository.serviceItems.size() > 0);
    }

    public void setItemTotalCost(ServiceItemsAdapter.ServiceItemViewHolder holder, ServiceItem serviceItem) {
        holder.setItemTotalCost(invoiceRepository.getServiceItemTotalCost(serviceItem));
    }

    private void updateTotalServiceItemsCost() {
        fragment.setServiceItemsTotalCost(invoiceRepository.getServiceItemsTotalCost());
    }
}
