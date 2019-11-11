package com.android.laundryservice.services.services_list;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.laundryservice.R;
import com.android.laundryservice.model.Service;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServicesAdapter extends RecyclerView.Adapter {

    private ServicesPresenter servicesPresenter;
    private int selectedPosition = 0;

    public ServicesAdapter(ServicesPresenter servicesPresenter) {
        this.servicesPresenter = servicesPresenter;
    }

    public void bindServices(ArrayList<Service> services) {
        servicesPresenter.bindServices(services);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item_layout, parent, false);
        return new ServiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        holder.itemView.setSelected(selectedPosition == position);
        servicesPresenter.onBindServiceItemRowViewAtPosition((ServiceViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return servicesPresenter.getServicesSize();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView serviceImage;
        private TextView serviceNameTextView;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceImage = itemView.findViewById(R.id.service_imageview);
            serviceNameTextView = itemView.findViewById(R.id.service_name_textview);
            itemView.setOnClickListener(this);
        }

        public void setServiceImage(String imageUri) {
            Picasso.get()
                    .load(imageUri)
                    .placeholder(R.drawable.logo)
                    .into(serviceImage);
        }

        public void setServiceName(String name) {
            serviceNameTextView.setText(name);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedPosition);
            selectedPosition = getLayoutPosition();
            notifyItemChanged(selectedPosition);
            servicesPresenter.onServiceClicked(getAdapterPosition());
        }
    }
}
