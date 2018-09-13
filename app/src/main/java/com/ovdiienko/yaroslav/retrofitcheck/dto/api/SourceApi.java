package com.ovdiienko.yaroslav.retrofitcheck.dto.api;

import com.ovdiienko.yaroslav.retrofitcheck.dto.api.interfaces.ApiContent;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.interfaces.ApiLogin;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yaroslav Ovdiienko on 9/4/18.
 * RetrofitCheck
 */

public final class SourceApi {
    private static final String BASE_URL = "http://mob.dev.stearling.net/";

    private static ApiLogin mApiLogin;
    private static ApiContent mApiContent;

    public static ApiLogin getLoginApi() {
        if (mApiLogin == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();

            mApiLogin = retrofit.create(ApiLogin.class);
        }

        return mApiLogin;

    }

    public static ApiContent getContentApi() {
        if (mApiContent == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();

            mApiContent = retrofit.create(ApiContent.class);
        }

        return mApiContent;

    }
}
