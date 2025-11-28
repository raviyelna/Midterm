//code thu thach 5 - hoatd
//start
package com.example.giuaki3;

import android.os.Bundle;
import android.util.Log;
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

        RecyclerView rvCategories = findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );

        categoryAdapter = new CategoryAdapter(categories);
        rvCategories.setAdapter(categoryAdapter);

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
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("MainActivity", "API success=false hoặc data null");
                        Toast.makeText(CategoryActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("MainActivity", "API error: " + response.code());
                    Toast.makeText(CategoryActivity.this, "API error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.e("MainActivity", "API failure", t);
                Toast.makeText(CategoryActivity.this, "API failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
//end