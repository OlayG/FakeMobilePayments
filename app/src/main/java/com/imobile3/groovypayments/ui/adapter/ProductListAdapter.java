package com.imobile3.groovypayments.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.model.Product;
import com.imobile3.groovypayments.utils.StateListHelper;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter
        extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private Context mContext;
    private AdapterCallback mCallbacks;
    private List<Product> mItems;

    public interface AdapterCallback {

        void onProductClick(Product product);
    }

    public ProductListAdapter(
            @NonNull Context context,
            @NonNull List<Product> products,
            @NonNull AdapterCallback callback) {
        mContext = context;
        mCallbacks = callback;
        mItems = products;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.product_list_item, parent, false);
        return new ViewHolder(view);
    }

    private String longToPrice(long l){
        int priceLength = Long.toString(l).length();
        String cost = Long.toString(l);
        String price = "$" + cost.substring(0, priceLength - 2) + "." + cost.substring(priceLength - 2, priceLength);
        return price;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product item = mItems.get(position);
        holder.label.setText(item.getName());
        holder.label.setTextColor(
                StateListHelper.getTextColorSelector(mContext, R.color.black_space));
        String divider = item.getNote().length() > 0 ? "|" : ""; //Question for Olay: Should this and the next line be a helper function? I feel like this sort of boolean logic doesn't belong here, as the only purpose of this function should be to bind to the viewholder

        //format note and price string
        String description = String.format("%s %s %s",longToPrice(item.getUnitPrice()), divider, item.getNote());
        holder.description.setText(description);
        holder.description.setTextColor(StateListHelper.getTextColorSelector(mContext, R.color.gray_pumice));

        //set up background color and image
        holder.icon.setBackground(ContextCompat.getDrawable(mContext, GroovyColor.fromId(item.getColorId()).colorRes));
        holder.icon.setImageResource(GroovyIcon.fromId(item.getIconId()).drawableRes);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewGroup container;
        TextView label;
        TextView description;
        ImageView icon;

        ViewHolder(View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            label = itemView.findViewById(R.id.label);
            icon = itemView.findViewById(R.id.icon);
            description = itemView.findViewById(R.id.description);
            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == container) {
                mCallbacks.onProductClick(mItems.get(getAdapterPosition()));
            }
        }
    }

    public List<Product> getItems() {
        return mItems;
    }

    public void setItems(@Nullable List<Product> items) {
        mItems = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }
}
