package com.android.laundryservice.subsummary;

import com.android.laundryservice.data.invoice.InvoiceRepository;
import com.android.laundryservice.model.InvoiceItem;
import com.android.laundryservice.model.Service;
import com.android.laundryservice.model.ServiceItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class SubSummaryPresenter {

    private ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();
    private final InvoiceRepository invoiceRepository;

    public SubSummaryPresenter(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public void bindServiceItems(ArrayList<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    public void onBindSubSummaryItemRowViewAtPosition(SubSummaryAdapter.SubSummaryItemViewHolder holder, int position) {
        InvoiceItem invoiceItem = invoiceItems.get(position);
        holder.setServiceImageView(invoiceItem.getService().getImage());
        holder.setServiceName(invoiceItem.getService().getName());
        holder.setServiceItemSummary(invoiceItem.getSummary());
        holder.setServiceTotalCost(invoiceItem.getTotalCost());
    }

    public int getServiceItemsSize() {
        return invoiceItems.size();
    }

    public ArrayList<InvoiceItem> getInvoiceItems() {
        HashMap<Service, ArrayList<ServiceItem>> serviceItemsMap = new HashMap<>();

        for (ServiceItem serviceItem : InvoiceRepository.serviceItems) {
            ArrayList<ServiceItem> serviceItems = new ArrayList<>();
            if (!serviceItemsMap.containsKey(serviceItem.getService())) {
                serviceItems = new ArrayList<>();
                serviceItems.add(serviceItem);
                serviceItemsMap.put(serviceItem.getService(), serviceItems);
            } else {
                serviceItems = serviceItemsMap.get(serviceItem.getService());
                serviceItems.add(serviceItem);

            }
            serviceItemsMap.put(serviceItem.getService(), serviceItems);
        }

        for (Service service : serviceItemsMap.keySet()) {
            ArrayList<ServiceItem> serviceItems = serviceItemsMap.get(service);
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setService(service);
            invoiceItem.setSummary(String.format(Locale.US, "%d items selected", serviceItems.size()));

            double itemTotalCost = 0;
            for (ServiceItem serviceItem : serviceItems) {
                itemTotalCost += invoiceRepository.getServiceItemTotalCost(serviceItem);
            }
            invoiceItem.setTotalCost(itemTotalCost);
            invoiceItems.add(invoiceItem);
        }
        return invoiceItems;
    }

    public double getTotalCost() {
        return invoiceRepository.getServiceItemsTotalCost();
    }
}
