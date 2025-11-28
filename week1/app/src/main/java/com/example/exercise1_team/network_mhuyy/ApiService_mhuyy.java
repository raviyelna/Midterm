package com.example.exercise1_team.network_mhuyy;


import com.example.exercise1_team.network_mhuyy.models_mhuyy.ForgotPasswordRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.GenericResponse_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.ResetPasswordRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.VerifyOtpRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.VerifyOtpResponse_mhuyy;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface  ApiService_mhuyy {

    @POST("auth/forgot")
    Call<GenericResponse_mhuyy> forgotPassword(@Body ForgotPasswordRequest_mhuyy body);

    @POST("auth/verify-otp")
    Call<VerifyOtpResponse_mhuyy> verifyOtp(@Body VerifyOtpRequest_mhuyy body);

    @POST("auth/reset-password")
    Call<GenericResponse_mhuyy> resetPassword(@Body ResetPasswordRequest_mhuyy body);
}
