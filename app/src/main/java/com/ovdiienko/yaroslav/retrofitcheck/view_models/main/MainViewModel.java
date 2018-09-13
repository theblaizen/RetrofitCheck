package com.ovdiienko.yaroslav.retrofitcheck.view_models.main;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;

import com.ovdiienko.yaroslav.retrofitcheck.BasicApp;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.model.Photo;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.repository.ApiRepository;

import java.util.List;

/**
 * Created by Yaroslav Ovdiienko on 9/9/18.
 * RetrofitCheck
 */

public class MainViewModel extends AndroidViewModel {
    private MediatorLiveData<List<Photo>> mData;
    private ApiRepository mRepository;

    public MainViewModel(Application application) {
        super(application);

        mData = new MediatorLiveData<>();
        mRepository = ((BasicApp) application).getApiRepository();
    }

    public LiveData<List<Photo>> getData(String userToken) {
        mData.addSource(mRepository.provideMainRepository().getContent(userToken), data -> mData.setValue(data));

        return mData;
    }
}
