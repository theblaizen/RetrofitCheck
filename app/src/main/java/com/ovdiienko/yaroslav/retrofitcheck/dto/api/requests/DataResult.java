package com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests;

import com.google.gson.annotations.SerializedName;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.model.Photo;

import java.util.List;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public class DataResult {
    @SerializedName("data")
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }
}
