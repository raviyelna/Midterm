//code thu thach 5 - hoatd
//start
package com.example.exercise1_team;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryApi {

    @GET("api.php?action=products")
    Call<CategoryResponse> getCategories();
}
//end