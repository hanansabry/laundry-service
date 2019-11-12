package com.android.laundryservice.subsummary;

import com.android.laundryservice.data.invoice.InvoiceRepository;
import com.android.laundryservice.model.InvoiceItem;

import java.util.ArrayList;

public class SummaryPresenter {

    private ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();
    private final InvoiceRepository invoiceRepository;

    public SummaryPresenter(InvoiceRepository invoiceRepository) {
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

    public void getInvoiceItems() {
        this.invoiceItems = invoiceRepository.getInvoiceItems();
    }

    public double getTotalCost() {
        return invoiceRepository.getServiceItemsTotalCost();
    }
}
