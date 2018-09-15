package com.ovdiienko.yaroslav.retrofitcheck.ui.activities.main;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.ui.activities.BaseActivity;
import com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.main.MainFragment;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected Fragment addFragmentToActivity() {
        return MainFragment.newInstance();
    }

    @Override
    protected void addToolbarFeatures(Toolbar toolbar) {
        toolbar.setTitle(R.string.app_name);
    }

}
