package com.ovdiienko.yaroslav.retrofitcheck.view_models.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.ovdiienko.yaroslav.retrofitcheck.BasicApp;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.repository.ApiRepository;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.LogIn;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.UserResult;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class LogInViewModel extends AndroidViewModel {
    private MediatorLiveData<UserResult> mUserResult;
    private ApiRepository mRepository;

    public LogInViewModel(Application application) {
        super(application);

        mUserResult = new MediatorLiveData<>();
        mRepository = ((BasicApp) application).getApiRepository();
    }

    public void getUser(LogIn logIn) {
        mUserResult.addSource(mRepository.provideLoginRepository().getUserResult(logIn), user -> mUserResult.setValue(user));
    }

    public LiveData<UserResult> getUserLiveData() {
        return mUserResult;
    }

}
