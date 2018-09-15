package com.ovdiienko.yaroslav.retrofitcheck.ui.activities.login;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.login.LoginFragment;
import com.ovdiienko.yaroslav.retrofitcheck.utils.FragmentUtils;

public class LoginActivity extends AppCompatActivity {
    private final int container = R.id.login_fragment_container;

    private Fragment mLoginFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupToolbar();
        addFragmentToActivity();
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.login_toolbar);
        mToolbar.setTitle(getString(R.string.app_login));

        setSupportActionBar(mToolbar);
    }

    private void addFragmentToActivity() {
        mLoginFragment = LoginFragment.newInstance(R.layout.fragment_login);
        FragmentUtils.addFragment(getSupportFragmentManager(), container, mLoginFragment, mLoginFragment.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachFragment();
    }

    private void detachFragment() {
        FragmentUtils.iterateDetachList(getSupportFragmentManager(), mLoginFragment);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

}
