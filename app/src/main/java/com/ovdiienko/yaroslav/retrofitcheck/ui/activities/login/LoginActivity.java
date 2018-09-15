package com.ovdiienko.yaroslav.retrofitcheck.ui.activities.login;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.ui.activities.BaseActivity;
import com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.login.LoginFragment;

public class LoginActivity extends BaseActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected Fragment addFragmentToActivity() {
        return LoginFragment.newInstance();
    }

    @Override
    protected void addToolbarFeatures(Toolbar toolbar) {
        toolbar.setTitle(R.string.app_login_title);
    }

}
