package com.ovdiienko.yaroslav.retrofitcheck.dto.api.interfaces;

import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.DataResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public interface ApiContent {

    @GET("api/get_data")
    Call<DataResult> getGalleryPhotos(@Header("X-Auth-Token") String token);
}
