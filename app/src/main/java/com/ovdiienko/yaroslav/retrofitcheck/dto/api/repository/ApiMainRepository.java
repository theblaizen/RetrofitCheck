package com.ovdiienko.yaroslav.retrofitcheck.dto.api.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ovdiienko.yaroslav.retrofitcheck.dto.api.SourceApi;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.interfaces.ApiContent;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.model.Photo;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.DataResult;
import com.ovdiienko.yaroslav.retrofitcheck.utils.AppExecutors;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public class ApiMainRepository {
    private static final boolean DEBUG = false;
    private static final String TAG = ApiMainRepository.class.getSimpleName();

    private AppExecutors mExecutors;

    private ApiContent mApiContent;

    public ApiMainRepository(AppExecutors executors) {
        mExecutors = executors;
        mApiContent = SourceApi.getContentApi();
    }

    public LiveData<List<Photo>> getContent(String userToken) {
        if (DEBUG) {
            Log.e(TAG, userToken);
        }

        final MutableLiveData<List<Photo>> apiResponse = new MutableLiveData<>();
        Call<DataResult> call = mApiContent.getGalleryPhotos(userToken);

        mExecutors.networkIO().execute(() -> call.enqueue(new Callback<DataResult>() {
            @Override
            public void onResponse(@NonNull Call<DataResult> call, @NonNull Response<DataResult> response) {
                if (response.isSuccessful()) {
                    apiResponse.postValue(response.body().getPhotos());

                    if (DEBUG) {
                        Log.d(TAG, "response" + response.body().getPhotos().toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataResult> call, @NonNull Throwable t) {

            }
        }));

        return apiResponse;
    }
}
