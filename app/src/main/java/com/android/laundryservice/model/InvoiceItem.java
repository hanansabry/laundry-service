package com.android.laundryservice.model;

public class InvoiceItem {

    private String id;
    private Service service;
    private String summary;
    private double totalCost;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
