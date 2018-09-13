package com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.login;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ovdiienko.yaroslav.retrofitcheck.BasicApp;
import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.requests.LogIn;
import com.ovdiienko.yaroslav.retrofitcheck.dto.db.models.User;
import com.ovdiienko.yaroslav.retrofitcheck.ui.activities.main.MainActivity;
import com.ovdiienko.yaroslav.retrofitcheck.ui.fragments.BaseFragment;
import com.ovdiienko.yaroslav.retrofitcheck.utils.PreferencesKeys;
import com.ovdiienko.yaroslav.retrofitcheck.utils.PreferencesUtils;
import com.ovdiienko.yaroslav.retrofitcheck.utils.Utils;
import com.ovdiienko.yaroslav.retrofitcheck.view_models.login.LogInViewModel;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private static final boolean DEBUG = false;
    private static final String TAG = LoginFragment.class.getSimpleName();

    private EditText mUserName;
    private EditText mUserPassword;
    private Button mSignIn;
    private Button mSignUp;
    private TextView mTestUser;

    private LogInViewModel mViewModel;

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initItems(view);

        return view;
    }

    @Override
    protected void initItems(View view) {
        mViewModel = ViewModelProviders.of(this).get(LogInViewModel.class);

        mTestUser = view.findViewById(R.id.login_dev_user);
        mUserName = view.findViewById(R.id.login_name);
        mUserPassword = view.findViewById(R.id.login_password);
        mSignIn = view.findViewById(R.id.login_btn_sign_in);
        mSignUp = view.findViewById(R.id.login_btn_sign_up);
    }

    @Override
    protected void setupItems() {
        if (DEBUG) {
            mTestUser.setVisibility(View.VISIBLE);
            mTestUser.setOnClickListener(this);
        }

        mSignIn.setOnClickListener(this);
        mSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_sign_in:
                checkIfNameAndPasswordValid();
                break;
            case R.id.login_btn_sign_up:
                showToast(getString(R.string.app_not_implemented));
                break;
            case R.id.login_dev_user:
                mUserName.setText("user1234");
                mUserPassword.setText("password1234");
                break;
        }
    }

    private void checkIfNameAndPasswordValid() {
        mUserPassword.setError(null);
        mUserName.setError(null);

        String userName = mUserName.getText().toString().trim();
        String userPassword = mUserPassword.getText().toString().trim();
        boolean isPasswordValid = Utils.isPasswordValid(userPassword) && userPassword.length() > 6;
        boolean isNameValid = !userName.isEmpty() && userName.length() > 3;

        if (!isPasswordValid && isNameValid) {
            mUserPassword.requestFocus();
            mUserPassword.setError(getString(R.string.login_password_not_valid));
        } else if (isPasswordValid && !isNameValid) {
            mUserName.requestFocus();
            mUserName.setError(getString(R.string.login_name_not_valid));
        } else if (!isPasswordValid && !isNameValid) {
            mUserName.requestFocus();
            mUserName.setError(getString(R.string.login_name_not_valid));
            mUserPassword.requestFocus();
            mUserPassword.setError(getString(R.string.login_password_not_valid));
        } else {
            if (getActivity() != null) {

                LogIn logIn = new LogIn(userName, userPassword);
                mViewModel.getUser(logIn).observe(this, observeUser());
            }
        }
    }

    private Observer<User> observeUser() {
        return user -> {
            if (user == null) {
                Log.e(TAG, "User object is null!");
                return;
            }

            ((BasicApp) getActivity().getApplication()).getDataRepository().provideUserRepository().insertUser(user);

            PreferencesUtils.put(getActivity(), PreferencesKeys.IS_LOGGED_IN, true);
            PreferencesUtils.put(getActivity(), PreferencesKeys.OWN_USER_LOGIN, user.getLogin());
            PreferencesUtils.put(getActivity(), PreferencesKeys.OWN_USER_TOKEN, user.getToken());

            finishAndStartApp();

        };
    }

    private void finishAndStartApp() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        getActivity().finish();
    }

}
