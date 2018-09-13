package com.ovdiienko.yaroslav.retrofitcheck.view_models.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.ovdiienko.yaroslav.retrofitcheck.BasicApp;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.repository.ApiRepository;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.LogIn;
import com.ovdiienko.yaroslav.retrofitcheck.dto.db.models.User;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class LogInViewModel extends AndroidViewModel {
    private MediatorLiveData<User> mUser;
    private ApiRepository mRepository;

    public LogInViewModel(Application application) {
        super(application);

        mUser = new MediatorLiveData<>();
        mRepository = ((BasicApp) application).getApiRepository();
    }

    public LiveData<User> getUser(LogIn logIn) {
        mUser.addSource(mRepository.provideLoginRepository().getUser(logIn), user -> mUser.setValue(user));

        return mUser;
    }
}
