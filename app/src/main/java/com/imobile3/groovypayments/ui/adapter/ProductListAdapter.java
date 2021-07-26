package com.imobile3.groovypayments.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.data.enums.GroovyColor;
import com.imobile3.groovypayments.data.enums.GroovyIcon;
import com.imobile3.groovypayments.data.model.Product;
import com.imobile3.groovypayments.databinding.ProductListItemBinding;
import com.imobile3.groovypayments.rules.ProductRules;
import com.imobile3.groovypayments.utils.StateListHelper;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
       return new ViewHolder(ProductListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ProductListItemBinding productListItemBinding;

        public ViewHolder(ProductListItemBinding productListItemBinding) {
            super(productListItemBinding.getRoot());
            this.productListItemBinding = productListItemBinding;
            productListItemBinding.getRoot().setOnClickListener(this);
        }

        public void bind(Product product){
            ProductRules productRules = new ProductRules(product);
            productListItemBinding.label.setText(product.getName());
            productListItemBinding.label.setTextColor(
                    StateListHelper.getTextColorSelector(mContext, R.color.black_space));

            String description = productRules.getDescription(Locale.US);
            productListItemBinding.description.setText(description);
            productListItemBinding.description.setTextColor(StateListHelper.getTextColorSelector(mContext, R.color.gray_pumice));

            //set up background color and image
            productListItemBinding.icon.setBackground(ContextCompat.getDrawable(mContext, GroovyColor.fromId(product.getColorId()).colorRes));
            productListItemBinding.icon.setImageResource(GroovyIcon.fromId(product.getIconId()).drawableRes);
        }


        @Override
        public void onClick(View v) {
                mCallbacks.onProductClick(mItems.get(getAdapterPosition()));
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
