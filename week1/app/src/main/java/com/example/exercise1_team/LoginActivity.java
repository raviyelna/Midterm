
package com.example.exercise1_team;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//23162016 Dinh Quoc Dat
public class LoginActivity extends AppCompatActivity {

    TextInputEditText editEmail, editPassword;
    Button btnLogin;
    TextView textRegister;
<<<<<<< Updated upstream
=======
    TextView textForgotPassword;
>>>>>>> Stashed changes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.editText_TextEmailAddress);
        editPassword = findViewById(R.id.editText_TextPassword);
        btnLogin = findViewById(R.id.button_Login);
        textRegister = findViewById(R.id.textView_Register);
<<<<<<< Updated upstream
=======
        textForgotPassword = findViewById(R.id.textView_ForgotPassword);
>>>>>>> Stashed changes

        btnLogin.setOnClickListener(v -> doLogin());

        textRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void doLogin() {
        // account chính là username bạn muốn hiển thị
        String account  = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (account.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            return;
        }

        // tạo request (LoginRequest nằm trong cùng package com.example.exercise1_team)
        LoginRequest req = new LoginRequest(account, password);

        // ApiClient và ApiService cũng ở cùng package -> không cần import thêm
        ApiService api = ApiClient.getClient().create(ApiService.class);
        api.login(req).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse res = response.body();
                    Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();

                    // nếu server trả token, lưu vào SharedPreferences
                    // getSharedPreferences("App", MODE_PRIVATE).edit().putString("token", res.token).apply();
                    // ❗❗ DÙNG LUÔN ACCOUNT ĐÃ LOGIN LÀM "FULL_NAME"
                    String fullName = account;

                    // chuyển sang MainActivity (hoặc HomeActivity)
                    Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                    intent.putExtra("FULL_NAME", fullName);   // truyền sang CategoryActivity
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
