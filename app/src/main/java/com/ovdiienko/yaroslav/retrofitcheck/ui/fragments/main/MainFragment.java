package com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
    private static final boolean DEBUG = false;
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
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mUserToken = (String) PreferencesUtils.get(getActivity(), PreferencesKeys.OWN_USER_TOKEN, "");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        initItems(view);

        return view;
    }

    @Override
    protected void initItems(View view) {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mRefreshLayout = view.findViewById(R.id.main_fragment_refresh);
        mProgressBar = view.findViewById(R.id.main_fragment_pb);
        mPhotosList = view.findViewById(R.id.main_fragment_rv_photos);
    }

    @Override
    protected void setupItems() {
        receiveData();

        mRefreshLayout.setProgressViewOffset(false, 0, getResources().getDimensionPixelSize(R.dimen.toolbar_height) + 20);
        mRefreshLayout.setOnRefreshListener(this::refreshListener);

        mPhotosList.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapter = new GalleryPhotosAdapter(mDataset, getActivity(), this);
        mPhotosList.setAdapter(mAdapter);
    }

    private void receiveData() {
        if (!Utils.isOnline(getActivity())) {
            showToast(getString(R.string.app_check_internet_connection));
        }

        mViewModel.getData(mUserToken).observe(this, this::observeImages);
    }

    private void observeImages(List<Photo> photos) {
        applyVisibilityParameters(photos);

        mDataset.clear();
        mDataset.addAll(photos.subList(0, photos.size() - 1)); // too large data array (5k size). Took only half.
        mAdapter.updateData(mDataset);
    }

    private void applyVisibilityParameters(List<Photo> photos) {
        if (photos != null && photos.size() > 0) {
            mPhotosList.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    private void refreshListener() {
        showToast(getString(R.string.app_updating_content));

        receiveData();
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_sign_out:
                Utils.signOut(getActivity());
                backToLogInScreen();

                if (DEBUG) {
                    List<User> users = ((BasicApp) getActivity().getApplication()).getDataRepository().provideUserRepository().loadUsers().getValue();
                    if (users != null) {
                        Log.d(TAG, users.toString());
                    }
                }
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

}
