package com.ovdiienko.yaroslav.retrofitcheck;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.repository.ApiRepository;
import com.ovdiienko.yaroslav.retrofitcheck.dto.db.AppDatabase;
import com.ovdiienko.yaroslav.retrofitcheck.dto.db.repository.DataRepository;
import com.ovdiienko.yaroslav.retrofitcheck.utils.AppExecutors;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class BasicApp extends Application {
    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
        initFresco();
    }

    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
                .setResizeAndRotateEnabledForNetwork(true)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, config);
    }

    private AppDatabase getDatabase() {
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public DataRepository getDataRepository() {
        return DataRepository.getInstance(getDatabase(), mAppExecutors);
    }

    public ApiRepository getApiRepository() {
        return ApiRepository.getInstance(mAppExecutors);
    }
}
