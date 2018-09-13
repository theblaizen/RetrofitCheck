package com.ovdiienko.yaroslav.retrofitcheck.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public abstract class BaseFragment extends Fragment {
    private Toast mToast;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupItems();
    }

    protected abstract void initItems(View view);

    /**
     * Runs on onViewCreated(...)
     */
    protected abstract void setupItems();

    protected void showToast(String text) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }

        mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        mToast.show();
    }

}
