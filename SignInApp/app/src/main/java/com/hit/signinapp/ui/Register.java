package com.hit.signinapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hit.signinapp.R;
import com.hit.signinapp.http.Api;
import com.hit.signinapp.http.RetrofitProvider;
import com.hit.signinapp.model.RegisterResponseModel;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    private Api api;
    private ImageView ivUserImage;
    private Uri selectedImageUri;
    private static final int REQUEST_IMAGE_PICK = 1;
    private EditText etName, etEmail, etPassword;
    private String name, email, password;
    private TextView tvRegister, tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init () {
        ivUserImage = findViewById(R.id.imageView);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        tvRegister = findViewById(R.id.tv_register);
        tvLogin = findViewById(R.id.tv_to_login);

        name = etName.getText().toString();
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString();
                password = etPassword.getText().toString();
                email = etEmail.getText().toString();

                if (name.equals("")) {
                    etName.setError("Please enter an email");
                } else if (password.equals("")) {
                    etPassword.setError("Please enter the password");
                } else if (email.equals("")) {
                  etEmail.setError("Please enter an email");
                }else {
                    register(name, email, password);
                }
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        });

        ivUserImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ivUserImage.setImageURI(selectedImageUri);
        }
    }

    private static File getFileFromUri(Context context, Uri uri) {
        String filePath = null;

        // Check if the Uri scheme is "content"
        if (uri.getScheme().equals("content")) {
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    filePath = cursor.getString(columnIndex);
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        // If the filePath is still null, attempt to retrieve the file path directly from the Uri
        if (filePath == null) {
            filePath = uri.getPath();
        }

        if (filePath != null) {
            return new File(filePath);
        }

        return null;
    }

    public static MultipartBody.Part getMultipartFromUri(Context context, Uri uri, String partName) {
        File file = getFileFromUri(context, uri);

        if (file != null) {
            // Create RequestBody instance from file
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

            // Create MultipartBody.Part using the provided partName and file name
            return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
        }

        return null;
    }

    private void register (String name, String email, String password) {
        String partName = "file"; // The desired part name
        MultipartBody.Part multipart = getMultipartFromUri(getApplicationContext(), selectedImageUri, partName);

        api = RetrofitProvider.getClient().create(Api.class);
        Call<RegisterResponseModel> call = api.register(name, email, multipart, password);
        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
                Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                Toast.makeText(Register.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}