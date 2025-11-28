package com.example.exercise1_team.nhk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.exercise1_team.R;
import com.example.exercise1_team.nhk.adapter.ProductAdapter;
import com.example.exercise1_team.nhk.api.ProductApiService;
import com.example.exercise1_team.nhk.api.RetrofitClient;
import com.example.exercise1_team.nhk.model.Product;
import com.example.exercise1_team.nhk.model.ProductItem;
import com.example.exercise1_team.nhk.model.ProductResponse;

import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductGridActivity extends AppCompatActivity {

    private RecyclerView rvProducts;
    private ProductAdapter adapter;

    // List để hiển thị cho RecyclerView
    private List<Product> productList = new ArrayList<>();

    // List dữ liệu gốc từ API
    private List<Product> productsFull = new ArrayList<>();

    // Lazy load config
    private int currentIndex = 0;
    private final int PAGE_SIZE = 10;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_grid);

        rvProducts = findViewById(R.id.rvProducts);
        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));

        adapter = new ProductAdapter(productList);
        rvProducts.setAdapter(adapter);

        loadProductsFromApi();
        setupLazyLoad();
    }

    private void loadProductsFromApi() {
        ProductApiService api = RetrofitClient.getClient().create(ProductApiService.class);

        api.getProducts().enqueue(new Callback<ProductResponse>() {

            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    List<ProductItem> items = response.body().getData();

                    for (ProductItem p : items) {
                        productsFull.add(convert(p));
                    }

                    loadMore();
                }
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.e("API_ERR", "Failed: " + t.getMessage());
            }
        });
    }


    private void setupLazyLoad() {
        rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading && layoutManager != null &&
                        layoutManager.findLastVisibleItemPosition() >= productList.size() - 1) {

                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        if (isLoading) return;
        isLoading = true;

        int nextIndex = Math.min(currentIndex + PAGE_SIZE, productsFull.size());

        for (int i = currentIndex; i < nextIndex; i++) {
            productList.add(productsFull.get(i));
        }

        currentIndex = nextIndex;
        adapter.notifyDataSetChanged();

        isLoading = false;
    }

    private Product convert(ProductItem p) {
        return new Product(
                p.getID(),
                p.getProductName(),
                0,
                p.getImageURL(),
                p.getCategory()
        );
    }

}
