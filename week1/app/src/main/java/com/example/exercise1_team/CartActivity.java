package com.example.exercise1_team;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CartActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        TextView totalItems = findViewById(R.id.cart_total_items);
        TextView totalPrice = findViewById(R.id.cart_total_price);

        totalItems.setText("Số sản phẩm: 2");
        totalPrice.setText("Tổng tiền: 500.000₫");
    }
}

