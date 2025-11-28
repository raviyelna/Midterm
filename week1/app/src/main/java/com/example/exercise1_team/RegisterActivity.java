package com.example.exercise1_team;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editName, editEmail, editPassword, editConfirm;
    Button btnRegister;
    TextView textBackLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editName = findViewById(R.id.editText_Name);
        editEmail = findViewById(R.id.editText_RegisterEmail);
        editPassword = findViewById(R.id.editText_RegisterPassword);
        editConfirm = findViewById(R.id.editText_ConfirmPassword);
        btnRegister = findViewById(R.id.button_Register);
        textBackLogin = findViewById(R.id.textView_BackToLogin);

        btnRegister.setOnClickListener(v -> doRegister());
        textBackLogin.setOnClickListener(v -> finish());
    }

    private void doRegister() {
        String fullName = editName.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String pass = editPassword.getText().toString().trim();
        String confirm = editConfirm.getText().toString().trim();

        if (fullName.isEmpty() || email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!pass.equals(confirm)) {
            Toast.makeText(this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo request (class nằm trong cùng package)
        RegisterRequest req = new RegisterRequest(
                fullName, // 1. username
                pass,     // 2. password
                email     // 3. email
        );

        ApiService api = ApiClient.getClient().create(ApiService.class);

        api.register(req).enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                    finish();  // quay về trang login
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
