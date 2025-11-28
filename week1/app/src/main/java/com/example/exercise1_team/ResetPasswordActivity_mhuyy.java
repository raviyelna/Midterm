package com.example.exercise1_team;

import android.app.ProgressDialog;
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
import com.example.exercise1_team.network_mhuyy.models_mhuyy.GenericResponse_mhuyy;
import com.example.exercise1_team.network_mhuyy.models_mhuyy.ResetPasswordRequest_mhuyy;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivity_mhuyy extends AppCompatActivity {

    private TextInputEditText editNew, editConfirm;
    private Button buttonUpdate;
    private TextView textBack;
    private ProgressDialog progressDialog;
    private ApiService_mhuyy apiService;
    private String resetToken;
    private String email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editNew = findViewById(R.id.editText_NewPassword);
        editConfirm = findViewById(R.id.editText_ConfirmNewPassword);
        buttonUpdate = findViewById(R.id.button_ResetPassword);
        textBack = findViewById(R.id.textView_BackToLoginFromReset);

        apiService = ApiClient_mhuyy.getApiService();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        resetToken = getIntent().getStringExtra("resetToken");
        email = getIntent().getStringExtra("email");

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = editNew.getText() != null ? editNew.getText().toString() : "";
                String confirm = editConfirm.getText() != null ? editConfirm.getText().toString() : "";
                if (TextUtils.isEmpty(pass) || pass.length() < 6) {
                    editNew.setError("Password must be at least 6 characters");
                    return;
                }
                if (!pass.equals(confirm)) {
                    editConfirm.setError("Passwords do not match");
                    return;
                }
                performReset(pass);
            }
        });

        textBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void performReset(String newPassword) {
        if (resetToken == null || resetToken.isEmpty()) {
            Toast.makeText(this, "Missing reset token", Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.show();
        ResetPasswordRequest_mhuyy req = new ResetPasswordRequest_mhuyy(resetToken, newPassword);
        // Gọi với action=reset-password
        apiService.resetPassword("reset-password", req).enqueue(new Callback<GenericResponse_mhuyy>() {
            @Override
            public void onResponse(Call<GenericResponse_mhuyy> call, Response<GenericResponse_mhuyy> response) {
                progressDialog.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    GenericResponse_mhuyy body = response.body();
                    if (body.isSuccess()) {
                        Toast.makeText(ResetPasswordActivity_mhuyy.this, "Password updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(ResetPasswordActivity_mhuyy.this, body.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    ApiErrorUtils_mhuyy.GenericError err = ApiErrorUtils_mhuyy.parseError(response);
                    String msg = err != null && err.message != null ? err.message : "Failed to update password";
                    Toast.makeText(ResetPasswordActivity_mhuyy.this, msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse_mhuyy> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(ResetPasswordActivity_mhuyy.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}