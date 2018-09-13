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
    @Expose
    private String description;

    public boolean isStatusSuccess() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
