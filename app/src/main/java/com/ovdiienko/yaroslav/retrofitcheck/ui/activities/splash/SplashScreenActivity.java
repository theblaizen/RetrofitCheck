package com.ovdiienko.yaroslav.retrofitcheck.ui.activities.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.ui.activities.login.LoginActivity;
import com.ovdiienko.yaroslav.retrofitcheck.ui.activities.main.MainActivity;
import com.ovdiienko.yaroslav.retrofitcheck.utils.PreferencesKeys;
import com.ovdiienko.yaroslav.retrofitcheck.utils.PreferencesUtils;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Imitates a delay for check some data or retrieve from internet need tokens.
        doSomeWork();
    }

    private void doSomeWork() {
        // Do some work here.

        new Handler().postDelayed(this::checkIfUserLoggedIn, 2_000L);
    }

    private void checkIfUserLoggedIn() {
        boolean isLoggedIn = (boolean) PreferencesUtils.get(this, PreferencesKeys.IS_LOGGED_IN, false);
        Intent intent;

        if (isLoggedIn) {
            intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }

}
