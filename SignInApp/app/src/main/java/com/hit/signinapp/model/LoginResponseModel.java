package com.hit.signinapp.model;

import com.google.gson.annotations.SerializedName;

public class LoginResponseModel {
    @SerializedName("success")
    private boolean success;

    @SerializedName("code")
    private int code;

    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    @SerializedName("token")
    private String token;

    @SerializedName("expiresIn")
    private String expiresIn;

    @SerializedName("sub")
    private SubData sub;

    public boolean isSuccess() {
        return success;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public SubData getSub() {
        return sub;
    }

    public class SubData {
        @SerializedName("id")
        private int id;

        @SerializedName("admin_name")
        private String adminName;

        @SerializedName("email")
        private String email;

        public int getId() {
            return id;
        }

        public String getAdminName() {
            return adminName;
        }

        public String getEmail() {
            return email;
        }
    }
}
