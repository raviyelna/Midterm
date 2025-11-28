//code thu thach 5 - hoatd
//start
package com.example.giuaki3;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_URL = "https://midtermhehe.azurewebsites.net/"; // TODO: change to real URL
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static CategoryApi getCategoryApi() {
        return getClient().create(CategoryApi.class);
    }
}
//end
