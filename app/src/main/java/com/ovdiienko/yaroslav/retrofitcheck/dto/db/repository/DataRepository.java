package com.ovdiienko.yaroslav.retrofitcheck.dto.db.repository;

import com.ovdiienko.yaroslav.retrofitcheck.dto.db.AppDatabase;
import com.ovdiienko.yaroslav.retrofitcheck.utils.AppExecutors;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public class DataRepository {
    private static DataRepository sInstance;

    private final AppDatabase mDatabase;
    private AppExecutors mExecutors;
    private UserRepository mUserRepository;

    private DataRepository(final AppDatabase database, AppExecutors executors) {
        mDatabase = database;
        mExecutors = executors;
        mUserRepository = new UserRepository(database, executors);
    }

    public static DataRepository getInstance(final AppDatabase database, AppExecutors executors) {
        if (sInstance == null) {
            synchronized (DataRepository.class) {
                if (sInstance == null) {
                    sInstance = new DataRepository(database, executors);
                }
            }
        }
        return sInstance;
    }

    public UserRepository provideUserRepository() {
        return mUserRepository;
    }

    public void clearAllTables() {
        mExecutors.diskIO().execute(mDatabase::clearAllTables);
    }

}
