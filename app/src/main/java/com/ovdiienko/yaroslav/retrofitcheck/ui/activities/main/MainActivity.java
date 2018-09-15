package com.ovdiienko.yaroslav.retrofitcheck.ui.activities.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.main.MainFragment;
import com.ovdiienko.yaroslav.retrofitcheck.utils.FragmentUtils;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private final int container = R.id.main_fragment_container;

    private Fragment mMainFragment;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        addFragmentToActivity();
    }


    private void setupToolbar() {
        mToolbar = findViewById(R.id.main_toolbar);
        mToolbar.setTitle(getString(R.string.app_name));

        setSupportActionBar(mToolbar);
    }

    private void addFragmentToActivity() {
        mMainFragment = MainFragment.newInstance(R.layout.fragment_main);
        FragmentUtils.addFragment(getSupportFragmentManager(), container, mMainFragment, mMainFragment.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        detachFragment();
    }

    private void detachFragment() {
        FragmentUtils.iterateDetachList(getSupportFragmentManager(), mMainFragment);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

}
