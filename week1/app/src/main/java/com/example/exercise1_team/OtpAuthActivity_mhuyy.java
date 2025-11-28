package com.example.exercise1_team;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exercise1_team.R;
import com.example.exercise1_team.network_mhuyy.ApiClient_mhuyy;
import com.example.exercise1_team.network_mhuyy.ApiErrorUtils_mhuyy;
import com.example.exercise1_team.network_mhuyy.ApiService_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.ForgotPasswordRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.GenericResponse_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.VerifyOtpRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.VerifyOtpResponse_mhuyy;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpAuthActivity_mhuyy extends AppCompatActivity {

    private TextInputEditText editOtp;
    private Button buttonVerify;
    private TextView textResend, textChangeEmail;
    private ProgressDialog progressDialog;
    private ApiService_mhuyy apiService;
    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_auth);

        editOtp = findViewById(R.id.editText_Otp);
        buttonVerify = findViewById(R.id.button_VerifyOtp);
        textResend = findViewById(R.id.textView_Resend);
        textChangeEmail = findViewById(R.id.textView_ChangeEmail);

        apiService = ApiClient_mhuyy.getApiService();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        email = getIntent().getStringExtra("email");

        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = editOtp.getText() != null ? editOtp.getText().toString().trim() : "";
                if (TextUtils.isEmpty(otp) || otp.length() < 4) {
                    editOtp.setError("Enter OTP");
                    return;
                }
                verifyOtp(email, otp);
            }
        });

        textResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resendOtp();
            }
        });

        textChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // quay lại nhập email
            }
        });
    }

    private void verifyOtp(final String email, String otp) {
        progressDialog.show();
        VerifyOtpRequest_mhuyy req = new VerifyOtpRequest_mhuyy(email, otp);
        apiService.verifyOtp(req).enqueue(new Callback<VerifyOtpResponse_mhuyy>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse_mhuyy> call, Response<VerifyOtpResponse_mhuyy> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    VerifyOtpResponse_mhuyy body = response.body();
                    if (body.isSuccess()) {
                        String resetToken = body.getResetToken();
                        if (resetToken != null && !resetToken.isEmpty()) {
                            Intent i = new Intent(OtpAuthActivity_mhuyy.this, ResetPasswordActivity_mhuyy.class);
                            i.putExtra("resetToken", resetToken);
                            i.putExtra("email", email);
                            startActivity(i);
                        } else {
                            Toast.makeText(OtpAuthActivity_mhuyy.this, "Missing reset token from server", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(OtpAuthActivity_mhuyy.this, body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    ApiErrorUtils_mhuyy.GenericError err = ApiErrorUtils_mhuyy.parseError(response);
                    String msg = err != null && err.message != null ? err.message : "Invalid OTP";
                    Toast.makeText(OtpAuthActivity_mhuyy.this, msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse_mhuyy> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OtpAuthActivity_mhuyy.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void resendOtp() {
        if (email == null || email.isEmpty()) {
            Toast.makeText(this, "No email to resend to", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();
        apiService.forgotPassword(new ForgotPasswordRequest_mhuyy(email)).enqueue(new Callback<GenericResponse_mhuyy>() {
            @Override
            public void onResponse(Call<GenericResponse_mhuyy> call, Response<GenericResponse_mhuyy> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    GenericResponse_mhuyy body = response.body();
                    Toast.makeText(OtpAuthActivity_mhuyy.this, body.getMessage() != null ? body.getMessage() : "OTP resent", Toast.LENGTH_SHORT).show();
                } else {
                    ApiErrorUtils_mhuyy.GenericError err = ApiErrorUtils_mhuyy.parseError(response);
                    String msg = err != null && err.message != null ? err.message : "Failed to resend";
                    Toast.makeText(OtpAuthActivity_mhuyy.this, msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse_mhuyy> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(OtpAuthActivity_mhuyy.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}