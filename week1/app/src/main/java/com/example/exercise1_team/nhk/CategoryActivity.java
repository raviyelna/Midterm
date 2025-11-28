//package com.example.exercise1_team.nhk;
//
//import androidx.appcompat.app.AppCompatActivity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import com.example.exercise1_team.R;
//
//public class CategoryActivity extends AppCompatActivity {
//
//    Button btnMen, btnWomen, btnElectronics, btnJewelry;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_category);
//
//        btnMen = findViewById(R.id.btnMen);
//        btnWomen = findViewById(R.id.btnWomen);
//        btnElectronics = findViewById(R.id.btnElectronics);
//        btnJewelry = findViewById(R.id.btnJewelry);
//
//        btnMen.setOnClickListener(v -> openCategory("men's clothing"));
//        btnWomen.setOnClickListener(v -> openCategory("women's clothing"));
//        btnElectronics.setOnClickListener(v -> openCategory("electronics"));
//        btnJewelry.setOnClickListener(v -> openCategory("jewelery"));
//    }
//
//    private void openCategory(String category) {
//        Intent intent = new Intent(this, ProductGridActivity.class);
//        intent.putExtra("categoryName", category);
//        startActivity(intent);
//    }
//}
