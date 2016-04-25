package com.fundamentals.visart.ui.progress;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fundamentals.visart.R;
import com.fundamentals.visart.model.Progress;
import com.fundamentals.visart.utils.GeneralViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jermiedomingo on 3/11/16.
 */
public class ProgressViewHolder extends GeneralViewHolder implements View.OnClickListener {


    @Bind(R.id.story_photo)
    ImageView mImageView;

    ProgressAdapter mProgressAdapter;
    String mStoryKey;
    Progress mProgress;

    public void setProgressAdapter(ProgressAdapter progressAdapter) {
        mProgressAdapter = progressAdapter;
    }

    public ProgressViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
    }

    public void populateViewHolder(final Context context, Progress progress, String storyKey) {

        mStoryKey = storyKey;
        mProgress = progress;
        final String pictureUrl = progress.getPictureUrl();


        Picasso.with(context).load(pictureUrl).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image).into(mImageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(pictureUrl).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image).into(mImageView, this);

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mProgressAdapter.getContext(), ProgressDetailSliderActivity.class);
        int progressPosition = mProgressAdapter.getItemPosition(mProgress);
        intent.putExtra(ProgressDetailSliderActivity.EXTRA_PROGRESS_POSITION, progressPosition);
        intent.putParcelableArrayListExtra(ProgressDetailSliderActivity.EXTRA_PROGRESS_LIST, mProgressAdapter.getItems());
        mProgressAdapter.getContext().startActivity(intent);

    }
}
