package com.ovdiienko.yaroslav.retrofitcheck.utils;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.List;

/**
 * Created by Yaroslav Ovdiienko on 9/11/18.
 * RetrofitCheck
 */

public class FragmentUtils {

    public static void replaceFragment(FragmentManager manager, @IdRes int container, Fragment fragment, @Nullable String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(container, fragment, tag);
        transaction.commit();
    }

    public static void addFragment(FragmentManager manager, @IdRes int container, Fragment fragment, @Nullable String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(container, fragment, tag);
        transaction.commit();
    }

    public static void detachFragment(FragmentManager manager, @NonNull String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(manager.findFragmentByTag(tag));
        transaction.commit();
    }

    public static void iterateDetachList(FragmentManager manager, Fragment attachedFragment) {
        List<Fragment> fragments = manager.getFragments();

        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.getTag() != null && fragment.getTag().equals(attachedFragment.getTag())) {
                FragmentUtils.detachFragment(manager, fragment.getTag());
            }
        }

    }

}
