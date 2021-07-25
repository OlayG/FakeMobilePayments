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
import com.imobile3.groovypayments.data.model.Cart;
import com.imobile3.groovypayments.databinding.CartListItemBinding;
import com.imobile3.groovypayments.databinding.ProductListItemBinding;
import com.imobile3.groovypayments.rules.CartRules;
import com.imobile3.groovypayments.rules.ProductRules;
import com.imobile3.groovypayments.utils.StateListHelper;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartListAdapter
        extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    private Context mContext;
    private AdapterCallback mCallbacks;
    private List<Cart> mItems;

    public interface AdapterCallback {

        void onCartClick(Cart cart);
    }

    public CartListAdapter(
            @NonNull Context context,
            @NonNull List<Cart> carts,
            @NonNull AdapterCallback callback) {
        mContext = context;
        mCallbacks = callback;
        mItems = carts;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        return new CartListAdapter.ViewHolder(CartListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

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

        CartListItemBinding cartListItemBinding;

        ViewHolder(CartListItemBinding cartListItemBinding) {
            super(cartListItemBinding.getRoot());
            this.cartListItemBinding = cartListItemBinding;
        }

        public void bind(Cart item){
            CartRules rules = new CartRules(item);

            // Configure the icon and background circle.
            cartListItemBinding.icon.setImageResource(GroovyIcon.Bookmarklet.drawableRes);
            cartListItemBinding.icon.setBackground(
                    ContextCompat.getDrawable(mContext, GroovyColor.Orange.colorRes));

            cartListItemBinding.description.setText(rules.getOrderHistoryDescription());
            cartListItemBinding.description.setTextColor(
                    StateListHelper.getTextColorSelector(mContext, R.color.gray_down_pour));

            cartListItemBinding.labelDate.setText(rules.getFormattedDate(Locale.US));
            cartListItemBinding.labelTotal.setText(rules.getCartTotalDisplay(Locale.US));
        }

        @Override
        public void onClick(View v) {
                mCallbacks.onCartClick(mItems.get(getAdapterPosition()));
        }
    }

    public List<Cart> getItems() {
        return mItems;
    }

    public void setItems(@Nullable List<Cart> items) {
        mItems = items != null ? items : new ArrayList<>();
        notifyDataSetChanged();
    }
}
