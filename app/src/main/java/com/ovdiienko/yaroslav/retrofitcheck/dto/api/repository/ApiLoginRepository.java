package com.ovdiienko.yaroslav.retrofitcheck.dto.api.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.ovdiienko.yaroslav.retrofitcheck.dto.api.SourceApi;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.interfaces.ApiLogin;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.LogIn;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.LoginResult;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.UserResult;
import com.ovdiienko.yaroslav.retrofitcheck.dto.db.models.User;
import com.ovdiienko.yaroslav.retrofitcheck.utils.AppExecutors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public class ApiLoginRepository {
    private static final boolean DEBUG = false;
    private static final String TAG = ApiLoginRepository.class.getSimpleName();

    private AppExecutors mExecutors;

    private ApiLogin mApiLogin;

    public ApiLoginRepository(AppExecutors executors) {
        mExecutors = executors;
        mApiLogin = SourceApi.getLoginApi();
    }

    public LiveData<UserResult> getUserResult(LogIn logIn) {
        final MutableLiveData<UserResult> apiResponse = new MutableLiveData<>();
        Call<LoginResult> call = mApiLogin.sendLoginDetails(logIn);

        mExecutors.networkIO().execute(() -> call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    LoginResult result = response.body();
                    UserResult userResult;

                    if (result.isStatusSuccess()) {
                        User user = new User();
                        user.setLogin(logIn.getLogin());
                        user.setPassword(logIn.getPassword());
                        user.setToken(response.body().getToken());

                        userResult = new UserResult(user, null, result.isStatusSuccess());
                        apiResponse.postValue(userResult);

                        if (DEBUG) {
                            Log.d(TAG, user.toString());
                        }
                    } else {
                        userResult = new UserResult(null, result.getDescription(), result.isStatusSuccess());
                        apiResponse.postValue(userResult);
                    }


                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
            }
        }));

        return apiResponse;
    }
}
