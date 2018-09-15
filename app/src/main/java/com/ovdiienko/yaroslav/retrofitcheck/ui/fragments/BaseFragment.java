package com.ovdiienko.yaroslav.retrofitcheck.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.utils.Utils;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public abstract class BaseFragment extends Fragment {
    protected static final String CONTAINER_LAYOUT = "Fragment.v4.CONTAINER_LAYOUT";
    protected int container_layout;

    private Toast mToast;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        getArgumentsFromInstance();
    }

    private void getArgumentsFromInstance() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            container_layout = bundle.getInt(CONTAINER_LAYOUT, 0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initItems(getLayout(inflater, container));
    }

    /**
     * Runs on onCreateView(...)
     */
    protected abstract View initItems(View view);

    private View getLayout(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(container_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupItems();
    }

    /**
     * Runs on onViewCreated(...)
     */
    protected abstract void setupItems();

    protected void showToast(String text) {
        if (TextUtils.isEmpty(text))
            return;

        cancelToast();

        mToast = Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void cancelToast() {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
    }

    protected boolean isInternetConnectionExist() {
        boolean isConnected = Utils.isOnline(getActivity());
        if (!isConnected) {
            showToast(getString(R.string.app_check_internet_connection));
        }

        return isConnected;
    }

    @Override
    public void onStop() {
        cancelToast();
        super.onStop();

    }
}
