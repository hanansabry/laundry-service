package com.android.laundryservice.services.serviceItems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.laundryservice.R;
import com.android.laundryservice.model.ServiceItem;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ServiceItemsAdapter extends RecyclerView.Adapter {

    private final ServiceItemsPresenter presenter;

    public ServiceItemsAdapter(ServiceItemsPresenter presenter) {
        this.presenter = presenter;
    }

    public void bindServiceItems(ArrayList<ServiceItem> serviceItems) {
        presenter.bindServices(serviceItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_item_row_layout, parent, false);
        return new ServiceItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        presenter.onBindServiceItemRowViewAtPosition((ServiceItemViewHolder) holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getServiceItemsSize();
    }

    class ServiceItemViewHolder extends RecyclerView.ViewHolder {

        private TextView serviceItemNameTextView, itemUnitPriceTextView, itemTotalCostTextView, quantityTextView;
        private ImageButton increaseButton, decreaseButton;
        private Context context;
        private int quantity;

        public ServiceItemViewHolder(@NonNull final View itemView) {
            super(itemView);
            context = itemView.getContext();

            serviceItemNameTextView = itemView.findViewById(R.id.service_item_name_textview);
            itemUnitPriceTextView = itemView.findViewById(R.id.item_unit_price_textview);
            itemTotalCostTextView = itemView.findViewById(R.id.item_total_cost_textview);
            quantityTextView = itemView.findViewById(R.id.quantity_textview);
            increaseButton = itemView.findViewById(R.id.increase_button);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
            increaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    setItemQuantity(++quantity);
//                    setItemTotalCost();
                    presenter.increaseQuantity(ServiceItemViewHolder.this, getAdapterPosition());
                }
            });
            decreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                        setItemQuantity(--quantity);
//                        setItemTotalCost();
                    presenter.decreaseQuantity(ServiceItemViewHolder.this, getAdapterPosition());
                }
            });
        }

        public void setServiceItemName(String name) {
            serviceItemNameTextView.setText(name);
        }

        public void setItemUnitPrice(double unitPrice) {
            itemUnitPriceTextView.setText(String.format(context.getResources().getString(R.string.unit_price_value), unitPrice));
        }

        public void setItemTotalCost(double itemTotalCostt) {
            itemTotalCostTextView.setText(
                    String.format(context.getResources().getString(R.string.total_cost_value),
                            itemTotalCostt));
        }

        public void setItemQuantity(int quantity) {
            quantityTextView.setText(quantity + "");
        }

    }
}
