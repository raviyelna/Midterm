package com.example.exercise1_team;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnStart = findViewById(R.id.btnStart);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gọi backend tách riêng
        new StartButtonHandler(this, btnStart);
    }
}
