package com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ovdiienko.yaroslav.retrofitcheck.BasicApp;
import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.model.Photo;
import com.ovdiienko.yaroslav.retrofitcheck.dto.db.models.User;
import com.ovdiienko.yaroslav.retrofitcheck.ui.activities.login.LoginActivity;
import com.ovdiienko.yaroslav.retrofitcheck.ui.adapters.GalleryPhotosAdapter;
import com.ovdiienko.yaroslav.retrofitcheck.ui.adapters.PreviewListener;
import com.ovdiienko.yaroslav.retrofitcheck.ui.custom.ImageOverlayView;
import com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.BaseFragment;
import com.ovdiienko.yaroslav.retrofitcheck.utils.PreferencesKeys;
import com.ovdiienko.yaroslav.retrofitcheck.utils.PreferencesUtils;
import com.ovdiienko.yaroslav.retrofitcheck.utils.Utils;
import com.ovdiienko.yaroslav.retrofitcheck.view_models.main.MainViewModel;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class MainFragment extends BaseFragment implements PreviewListener {
    private static final boolean DEBUG = true;
    private static final String TAG = MainFragment.class.getSimpleName();

    private SwipeRefreshLayout mRefreshLayout;
    private ProgressBar mProgressBar;
    private RecyclerView mPhotosList;
    private GalleryPhotosAdapter mAdapter;
    private ImageOverlayView mOverlayView;
    private List<Photo> mDataset = new ArrayList<>();
    private String mUserToken;
    private MainViewModel mViewModel;

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        int container = R.layout.fragment_main;

        bundle.putInt(CONTAINER_LAYOUT, container);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Probably is better to create separate class for Token and w/r it from Data Base.
        mUserToken = (String) PreferencesUtils.get(getActivity(), PreferencesKeys.OWN_USER_TOKEN, "");
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected View initItems(View view) {
        mRefreshLayout = view.findViewById(R.id.main_fragment_refresh);
        mProgressBar = view.findViewById(R.id.main_fragment_pb);
        mPhotosList = view.findViewById(R.id.main_fragment_rv_photos);

        return view;
    }

    @Override
    protected void setupItems() {
        if (isInternetConnectionExist()) {
            mViewModel.getPhotosLiveData().observe(this, this::observeImages);
            receiveData();
        }

        mRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(R.dimen.toolbar_height) + 20);
        mRefreshLayout.setOnRefreshListener(this::refreshListener);

        mPhotosList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new GalleryPhotosAdapter(mDataset, getActivity(), this);
        mPhotosList.setAdapter(mAdapter);
    }

    private void receiveData() {
        mViewModel.getData(mUserToken);
    }

    private void observeImages(List<Photo> photos) {
        mDataset.clear();
        mDataset.addAll(photos.subList(0, photos.size() - 1)); // too large data array (5k size). Took only half.
        mAdapter.updateData(mDataset);

        applyVisibilityParameters(mDataset);
    }

    private void applyVisibilityParameters(List<Photo> photos) {
        if (photos != null && photos.size() > 0) {
            mPhotosList.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        } else {
            mPhotosList.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private void refreshListener() {
        if (isInternetConnectionExist()) {
            showToast(getString(R.string.app_updating_content));
            receiveData();
        }

        applyVisibilityParameters(mDataset);
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_sign_out:
                if (DEBUG) {
                    List<User> users = ((BasicApp) getActivity().getApplication()).getDataRepository().provideUserRepository().loadUsers().getValue();
                    if (users != null) {
                        Log.d(TAG, users.toString());
                    }
                }

                Utils.signOut(getActivity());
                backToLogInScreen();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void backToLogInScreen() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        if (getActivity() != null) {
            getActivity().finish();
        }
    }

    @Override
    public void onListen(View view, int position) {
        mOverlayView = new ImageOverlayView(getActivity());
        new ImageViewer.Builder<>(getActivity(), mDataset)
                .setFormatter(Photo::getUrl)
                .allowZooming(true)
                .setStartPosition(position)
                .allowSwipeToDismiss(true)
                .setImageChangeListener(this::imageChangeListener)
                .setOverlayView(mOverlayView)
                .setBackgroundColorRes(R.color.primaryTextColor)
                .show();
    }

    private void imageChangeListener(int position) {
        if (mDataset != null && mDataset.size() > 0) {
            Photo image = mDataset.get(position);
            mOverlayView.setDescription(image.getTitle());
        }
    }

    @Override
    public void onDestroy() {
        mDataset.clear();
        super.onDestroy();
    }
}
