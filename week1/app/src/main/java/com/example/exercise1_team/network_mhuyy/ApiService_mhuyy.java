package com.example.exercise1_team.network_mhuyy;

import com.example.exercise1_team.network_mhuyy.models_mhuyy.ForgotPasswordRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.GenericResponse_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.ResetPasswordRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.VerifyOtpRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.VerifyOtpResponse_mhuyy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService_mhuyy {

    // Thêm query "action" để phù hợp với api.php?action=...
    @POST("api.php")
    Call<GenericResponse_mhuyy> forgotPassword(@Query("action") String action, @Body ForgotPasswordRequest_mhuyy body);

    @POST("api.php")
    Call<VerifyOtpResponse_mhuyy> verifyOtp(@Query("action") String action, @Body VerifyOtpRequest_mhuyy body);

    @POST("api.php")
    Call<GenericResponse_mhuyy> resetPassword(@Query("action") String action, @Body ResetPasswordRequest_mhuyy body);
}