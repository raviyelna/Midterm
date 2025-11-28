package com.example.exercise1_team;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.exercise1_team.network_mhuyy.ApiClient_mhuyy;
import com.example.exercise1_team.network_mhuyy.ApiErrorUtils_mhuyy;
import com.example.exercise1_team.network_mhuyy.ApiService_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.ForgotPasswordRequest_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.GenericResponse_mhuyy;
import com.example.exercise1_team.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity_mhuyy extends AppCompatActivity {

    private TextInputEditText editEmail;
    private Button buttonSend;
    private TextView textBack;
    private ProgressDialog progressDialog;
    private ApiService_mhuyy apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        editEmail = findViewById(R.id.editText_ForgotEmail);
        buttonSend = findViewById(R.id.button_SendReset);
        textBack = findViewById(R.id.textView_BackToLoginFromForgot);
        apiService = ApiClient_mhuyy.getApiService();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText() != null ? editEmail.getText().toString().trim() : "";
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    editEmail.setError("Enter a valid email");
                    return;
                }
                sendForgotRequest(email);
            }
        });

        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); // giả sử quay về Login
            }
        });
    }

    private void sendForgotRequest(final String email) {
        progressDialog.show();
        ForgotPasswordRequest_mhuyy req = new ForgotPasswordRequest_mhuyy(email);
        apiService.forgotPassword(req).enqueue(new Callback<GenericResponse_mhuyy>() {
            @Override
            public void onResponse(Call<GenericResponse_mhuyy> call, Response<GenericResponse_mhuyy> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    GenericResponse_mhuyy body = response.body();
                    if (body.isSuccess()) {
                        Toast.makeText(ForgotPasswordActivity_mhuyy.this, "OTP sent to " + email, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ForgotPasswordActivity_mhuyy.this, OtpAuthActivity_mhuyy.class);
                        i.putExtra("email", email);
                        startActivity(i);
                    } else {
                        Toast.makeText(ForgotPasswordActivity_mhuyy.this, body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    ApiErrorUtils_mhuyy.GenericError err = ApiErrorUtils_mhuyy.parseError(response);
                    String msg = err != null && err.message != null ? err.message : "Failed to send OTP";
                    Toast.makeText(ForgotPasswordActivity_mhuyy.this, msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse_mhuyy> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ForgotPasswordActivity_mhuyy.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}