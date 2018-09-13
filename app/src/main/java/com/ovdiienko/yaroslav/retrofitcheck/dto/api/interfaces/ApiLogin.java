package com.ovdiienko.yaroslav.retrofitcheck.dto.api.interfaces;

import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.LogIn;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.LoginResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public interface ApiLogin {

    @POST("api/login")
    Call<LoginResult> sendLoginDetails(@Body LogIn login);
}
