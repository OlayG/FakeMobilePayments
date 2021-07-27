package com.imobile3.groovypayments.ui.checkout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.imobile3.groovypayments.R;
import com.imobile3.groovypayments.manager.CartManager;
import com.imobile3.groovypayments.ui.BaseActivity;

import java.util.Locale;

public class CheckoutCompleteActivity extends BaseActivity {

    public static final String EXTRA_CHANGE = "ChangeOwed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_complete_activity);
        setUpMainNavBar();
        setUpViews();
    }

    @Override
    public void onBackPressed() {
        // Do nothing!
    }

    @Override
    protected void setUpMainNavBar() {
        super.setUpMainNavBar();
        mMainNavBar.showLogo();
        mMainNavBar.showSubtitle(getString(R.string.checkout_complete_subtitle));
    }

    @Override
    protected void initViewModel() {
        // No view model needed.
    }

    private void setUpViews() {
        TextView lblAmount = findViewById(R.id.label_amount);
        lblAmount.setText(CartManager.getInstance().getFormattedGrandTotal(Locale.getDefault()));

        TextView lblChange = findViewById(R.id.label_change);
        String change = getIntent().getStringExtra(EXTRA_CHANGE);
        if (change != null && !change.isEmpty()) {
            lblChange.setText(getString(R.string.checkout_change_left, change));
            lblChange.setVisibility(View.VISIBLE);
        } else lblChange.setVisibility(View.GONE);


        Button btnGroovy = findViewById(R.id.btn_groovy);
        btnGroovy.setOnClickListener(v -> completeCheckout());
    }

    private void completeCheckout() {
        CartManager.getInstance().clearCurrentCart();
        // Remove this activity from the stack.
        finish();
    }
}
