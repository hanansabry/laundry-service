package com.android.laundryservice.services.serviceItems;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.laundryservice.Injection;
import com.android.laundryservice.R;
import com.android.laundryservice.data.serviceItems.ServiceItemsRepository;
import com.android.laundryservice.model.Service;
import com.android.laundryservice.model.ServiceItem;
import com.android.laundryservice.services.HomeActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceItemsFragment extends Fragment {


    private Service selectedService;
    private ServiceItemsPresenter presenter;

    public static ServiceItemsFragment newInstance(Service service) {
        ServiceItemsFragment fragment = new ServiceItemsFragment();
        Bundle selectedServiceBundle = new Bundle();
        selectedServiceBundle.putParcelable(Service.class.getName(), service);
        fragment.setArguments(selectedServiceBundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        selectedService = getArguments().getParcelable(Service.class.getName());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        presenter = new ServiceItemsPresenter(this, Injection.provideServiceItemsRepository(), Injection.provideInvoiceRepository(sharedPreferences));
        return inflater.inflate(R.layout.fragment_service_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeRecyclerView(view);
    }

    private void initializeRecyclerView(View view) {
        RecyclerView serviceItemsRecyclerView = view.findViewById(R.id.service_items_recyclerview);
        serviceItemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final ServiceItemsAdapter adapter = new ServiceItemsAdapter(presenter);
        serviceItemsRecyclerView.setAdapter(adapter);

        presenter.retrieveServiceItems(selectedService.getId(), new ServiceItemsRepository.RetrieveServiceItemsCallback() {
            @Override
            public void onServiceItemsRetrievedSuccessfully(ArrayList<ServiceItem> serviceItems) {
                adapter.bindServiceItems(serviceItems);
            }

            @Override
            public void onServiceItemsRetrievedFailed(String errmsg) {
                Toast.makeText(getContext(), errmsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setServiceItemsTotalCost(double serviceItemsTotalCost) {
        ((HomeActivity) getActivity()).setServiceItemsTotalCost(serviceItemsTotalCost);
    }
}
