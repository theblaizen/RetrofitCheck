package com.ovdiienko.yaroslav.retrofitcheck.dto.db.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;

import com.ovdiienko.yaroslav.retrofitcheck.dto.db.AppDatabase;
import com.ovdiienko.yaroslav.retrofitcheck.dto.db.models.User;
import com.ovdiienko.yaroslav.retrofitcheck.utils.AppExecutors;

import java.util.List;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public class UserRepository {
    private final AppDatabase mDatabase;
    private AppExecutors mExecutors;

    private MediatorLiveData<List<User>> mObservableUsers;

    UserRepository(AppDatabase appDatabase, AppExecutors executors) {
        mDatabase = appDatabase;
        mExecutors = executors;

        mObservableUsers = new MediatorLiveData<>();
        mObservableUsers.addSource(mDatabase.userDao().loadAllUsers(),
                userEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObservableUsers.postValue(userEntities);
                    }
                });
    }

    public LiveData<List<User>> loadUsers() {
        return mObservableUsers;
    }

    public LiveData<User> loadUser(final String userId) {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        final User[] user = new User[1];
        mExecutors.diskIO().execute(() -> user[0] = mDatabase.userDao().loadUserSync(userId));
        userLiveData.setValue(user[0]);

        return userLiveData;
    }

    public User loadUserSync(final String userId) {
        final User[] user = new User[1];
        mExecutors.diskIO().execute(() -> user[0] = mDatabase.userDao().loadUserSync(userId));

        return user[0];
    }

    public void insertUser(User user) {
        mExecutors.diskIO().execute(() -> mDatabase.userDao().insertUser(user));
    }

}
