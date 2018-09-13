package com.ovdiienko.yaroslav.retrofitcheck.ui.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ovdiienko.yaroslav.retrofitcheck.R;

/**
 * Created by Yaroslav Ovdiienko on 9/12/18.
 * RetrofitCheck
 */

public class ImageOverlayView extends FrameLayout {
    private TextView mTvDescription;

    public ImageOverlayView(@NonNull Context context) {
        super(context);
        init();
    }

    public ImageOverlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setDescription(String description) {
        mTvDescription.setText(description);
    }

    private void init() {
        View view = inflate(getContext(), R.layout.view_image_overlay, this);
        mTvDescription = view.findViewById(R.id.view_tv_description);
    }

}
