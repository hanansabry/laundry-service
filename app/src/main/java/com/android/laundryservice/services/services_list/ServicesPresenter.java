package com.android.laundryservice.services.services_list;

import com.android.laundryservice.data.services.ServicesRepository;
import com.android.laundryservice.model.Service;
import com.android.laundryservice.services.HomeActivity;

import java.util.ArrayList;


public class ServicesPresenter {

    private ArrayList<Service> services = new ArrayList<>();
    private final ServicesRepository servicesRepository;
    private final HomeActivity homeView;

    public ServicesPresenter(HomeActivity homeView, ServicesRepository servicesRepository) {
        this.servicesRepository = servicesRepository;
        this.homeView = homeView;
    }

    public void bindServices(ArrayList<Service> services) {
        this.services = services;
    }

    public void onBindServiceItemRowViewAtPosition(ServicesAdapter.ServiceViewHolder holder, int position) {
        Service service = services.get(position);
        holder.setServiceName(service.getName());
        holder.setServiceImage(service.getImage());
    }

    public int getServicesSize() {
        return services.size();
    }

    public void retrieveServices(ServicesRepository.RetrieveServicesCallback callback) {
        servicesRepository.retrieveServices(callback);
    }

    public void onServiceClicked(int position) {
        Service service = services.get(position);
        homeView.onServiceClicked(service);
    }
}
