package com.ovdiienko.yaroslav.retrofitcheck.ui.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ovdiienko.yaroslav.retrofitcheck.R;
import com.ovdiienko.yaroslav.retrofitcheck.dto.api.model.Photo;

import java.util.List;

/**
 * Created by Yaroslav Ovdiienko on 9/9/18.
 * RetrofitCheck
 */

public class GalleryPhotosAdapter extends RecyclerView.Adapter<GalleryPhotosAdapter.ViewHolder> {
    private final Context mContext;
    private List<Photo> mDataset;

    private final PreviewListener mListener;

    public GalleryPhotosAdapter(List<Photo> data, Context context, PreviewListener listener) {
        mDataset = data;
        mContext = context;
        mListener = listener;
    }

    public void updateData(List<Photo> data) {
        mDataset = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GalleryPhotosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout view = (FrameLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_view, null, false);
        ViewHolder viewHolder = new ViewHolder(view, mListener);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (mDataset != null) {
            holder.itemView.setTag(position);

            Glide.with(mContext)
                    .load(mDataset.get(position).getThumbnailUrl())
                    .into(holder.mImage);
        }

    }

    @Override
    public int getItemCount() {
        if (mDataset != null) {
            return mDataset.size();
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImage;
        PreviewListener mListener;

        ViewHolder(FrameLayout view, PreviewListener listener) {
            super(view);

            mListener = listener;
            mImage = itemView.findViewById(R.id.rv_item_image);
            mImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onListen(view, ((int) itemView.getTag()));
        }

    }

}