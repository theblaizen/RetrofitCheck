package com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests;

import com.google.gson.annotations.Expose;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public class LoginResult {
    @Expose
    private boolean status;
    @Expose
    private String token;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
