package com.ovdiienko.yaroslav.retrofitcheck.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.utils.FragmentUtils;

/**
 * Created by Yaroslav Ovdiienko on 9/15/18.
 * RetrofitCheck
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected int mFragmentContainer = R.id.base_fragment_container;

    private Fragment mFragment;
    private Toolbar mToolbar;
    private FrameLayout mProgressLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        registerToolbar();
        registerFragment(addFragmentToActivity());
        registerProgressLayout();
    }

    protected abstract Fragment addFragmentToActivity();

    private void registerFragment(Fragment fragment) {
        mFragment = fragment;
        String tag = fragment.getClass().getSimpleName();
        FragmentUtils.addFragment(getSupportFragmentManager(), mFragmentContainer, fragment, tag);
    }

    public Fragment getFragment() {
        return mFragment;
    }

    protected abstract void addToolbarFeatures(Toolbar toolbar);

    private void registerToolbar() {
        mToolbar = findViewById(R.id.base_toolbar);
        addToolbarFeatures(mToolbar);
        setSupportActionBar(mToolbar);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    private void registerProgressLayout() {
        mProgressLayout = findViewById(R.id.base_activity_fl_pb);
    }

    public void toggleProgressLayout() {
        mProgressLayout.setVisibility(mProgressLayout.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachFragment();
    }

    private void detachFragment() {
        FragmentUtils.iterateDetachList(getSupportFragmentManager(), mFragment);
    }

}
