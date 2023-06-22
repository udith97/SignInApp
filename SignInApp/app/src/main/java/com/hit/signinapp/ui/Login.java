package com.hit.signinapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.hit.signinapp.R;
import com.hit.signinapp.http.Api;
import com.hit.signinapp.http.RetrofitProvider;
import com.hit.signinapp.model.LoginResponseModel;

import org.w3c.dom.Text;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    EditText etName, etPassword;
    TextView tvLogin, tvRegister;
    String name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init () {
        etName = findViewById(R.id.et_name);
        etPassword = findViewById(R.id.et_password);
        tvRegister = findViewById(R.id.tv_to_register);
        tvLogin = findViewById(R.id.tv_login);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                password = etPassword.getText().toString();

                if (name.equals("")) {
                    etName.setError("Please enter an email");
                } else if (password.equals("")) {
                    etPassword.setError("Please enter the password");
                } else {
                    login(name, password);
                }
            }
        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });
    }

    private void login(String name, String password) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("admin_name", name);
        map.put("password", password);
        String json = new Gson().toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        RetrofitProvider.getClient().create(Api.class).login(requestBody).enqueue(new Callback<LoginResponseModel>() {
            @Override
            public void onResponse(Call<LoginResponseModel> call, Response<LoginResponseModel> response) {
                Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponseModel> call, Throwable t) {
                Toast.makeText(Login.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}