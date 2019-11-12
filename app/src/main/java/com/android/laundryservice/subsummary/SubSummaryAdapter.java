package com.android.laundryservice.subsummary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.laundryservice.R;
import com.android.laundryservice.model.InvoiceItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubSummaryAdapter extends RecyclerView.Adapter<SubSummaryAdapter.SubSummaryItemViewHolder> {

    private final SubSummaryPresenter presenter;

    public SubSummaryAdapter(SubSummaryPresenter presenter) {
        this.presenter = presenter;
    }

    public void setInvoiceItems() {
        presenter.getInvoiceItems();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubSummaryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sub_summary_item_layout, parent, false);
        return new SubSummaryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubSummaryItemViewHolder holder, int position) {
        presenter.onBindSubSummaryItemRowViewAtPosition(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getServiceItemsSize();
    }

    class SubSummaryItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView serviceImageView;
        private TextView serviceNameTextView, serviceItemSummaryTextView, serviceTotalCostTextView;

        public SubSummaryItemViewHolder(@NonNull View itemView) {
            super(itemView);

            serviceImageView = itemView.findViewById(R.id.service_imageview);
            serviceNameTextView = itemView.findViewById(R.id.service_name_textview);
            serviceItemSummaryTextView = itemView.findViewById(R.id.service_items_summary_textview);
            serviceTotalCostTextView = itemView.findViewById(R.id.service_items_total_cost_textview);
        }

        public void setServiceImageView(String uri) {
            Picasso.get().load(uri).into(serviceImageView);
        }

        public void setServiceName(String name) {
            serviceNameTextView.setText(name);
        }

        public void setServiceItemSummary(String summary) {
            serviceItemSummaryTextView.setText(summary);
        }

        public void setServiceTotalCost(double totalCost) {
            serviceTotalCostTextView.setText(totalCost + " KWD");
        }
    }
}
