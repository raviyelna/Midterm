//code thu thach 5 - hoatd
//start
package com.example.exercise1_team;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    private CategoryAdapter categoryAdapter;
    private final List<Category> categories = new ArrayList<>();

    private TextView tvHello;
    private EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // ánh xạ view
        tvHello = findViewById(R.id.tvHello);
        edtSearch = findViewById(R.id.edtSearch);

        RecyclerView rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        categoryAdapter = new CategoryAdapter(categories);
        rvCategories.setAdapter(categoryAdapter);

        // ====== LẤY USERNAME TỪ LOGIN ======
        String fullName = getIntent().getStringExtra("FULL_NAME");
        if (fullName == null || fullName.isEmpty()) {
            fullName = "Người dùng";
        }
        tvHello.setText("Hi! " + fullName);

        // ====== SEARCH PRODUCTS ======
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                categoryAdapter.getFilter().filter(s);   // gọi filter trong adapter
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        // gọi API lấy dữ liệu
        loadCategoriesFromApi();
    }

    private void loadCategoriesFromApi() {
        CategoryApi api = ApiClient.getCategoryApi();
        Call<CategoryResponse> call = api.getCategories();

        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryResponse body = response.body();

                    if (body.isSuccess() && body.getData() != null) {
                        // Lọc trùng theo tên category
                        List<Category> uniqueCategories = new ArrayList<>();
                        Set<String> seenNames = new HashSet<>();
                        for (Category c : body.getData()) {
                            if (c == null) continue;
                            String catName = c.getCategory();
                            if (catName == null) continue;
                            if (!seenNames.contains(catName)) {
                                seenNames.add(catName);
                                uniqueCategories.add(c);
                            }
                        }

                        categories.clear();
                        categories.addAll(uniqueCategories);

                        // cập nhật lại data cho adapter (đồng bộ categoriesFull để search)
                        categoryAdapter.setData(uniqueCategories);

                    } else {
                        Log.e("CategoryActivity", "API success=false hoặc data null");
                        Toast.makeText(CategoryActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("CategoryActivity", "API error: " + response.code());
                    Toast.makeText(CategoryActivity.this, "API error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e("CategoryActivity", "API failure", t);
                Toast.makeText(CategoryActivity.this, "API failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
//end
