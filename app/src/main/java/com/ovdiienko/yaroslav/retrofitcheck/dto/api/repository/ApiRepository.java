package com.ovdiienko.yaroslav.retrofitcheck.dto.api.repository;

import com.ovdiienko.yaroslav.retrofitcheck.utils.AppExecutors;

/**
 * Created by Yaroslav Ovdiienko on 9/8/18.
 * RetrofitCheck
 */

public final class ApiRepository {
    private static ApiRepository sInstance;

    private ApiLoginRepository mLoginRepository;
    private ApiMainRepository mMainRepository;

    public ApiRepository(AppExecutors executors) {
        mLoginRepository = new ApiLoginRepository(executors);
        mMainRepository = new ApiMainRepository(executors);
    }

    public static ApiRepository getInstance(final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (ApiRepository.class) {
                if (sInstance == null) {
                    sInstance = new ApiRepository(executors);
                }
            }
        }
        return sInstance;
    }

    public ApiLoginRepository provideLoginRepository() {
        return mLoginRepository;
    }

    public ApiMainRepository provideMainRepository() {
        return mMainRepository;
    }
}