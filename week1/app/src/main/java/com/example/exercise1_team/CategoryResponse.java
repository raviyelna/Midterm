//code thu thach 5 - hoatd
//start
package com.example.giuaki3;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("count")
    private int count;

    @SerializedName("data")
    private List<Category> data;

    public boolean isSuccess() {
        return success;
    }

    public int getCount() {
        return count;
    }

    public List<Category> getData() {
        return data;
    }
}
//end
