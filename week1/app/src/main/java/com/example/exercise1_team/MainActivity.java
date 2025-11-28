package com.example.exercise1_team;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.nn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView avatar = findViewById(R.id.header_avatar);
        TextView greeting = findViewById(R.id.header_greeting);
        TextView name = findViewById(R.id.header_name);
        TextView email = findViewById(R.id.header_email);
        Button profileButton = findViewById(R.id.button_open_profile);
        Button cartButton = findViewById(R.id.button_view_cart);
        Button orderHistoryButton = findViewById(R.id.button_order_history);

        UserHeader.saveUserInfo(this, "demo.user@example.com", "Demo User");
        UserHeader.displayUserInfo(this, avatar, name, email, greeting);

        profileButton.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ProfileActivity.class))
        );

        cartButton.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, CartActivity.class))
        );

        orderHistoryButton.setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class))
        );
    }
}