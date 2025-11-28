package com.example.exercise1_team;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageView avatar = findViewById(R.id.profile_avatar);
        TextView username = findViewById(R.id.profile_username);
        TextView email = findViewById(R.id.profile_email);
        TextView createdDate = findViewById(R.id.profile_created_date);

        UserHeader.displayUserInfo(this, avatar, username, email, null);
        createdDate.setText(formatCreatedDate(UserHeader.getUserCreatedAt(this)));
    }

    private String formatCreatedDate(long millis) {
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return "Ngày khởi tạo: " + formatter.format(date);
    }
}

