package com.fundamentals.visart.ui.progress;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fundamentals.visart.R;
import com.fundamentals.visart.model.Progress;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jermiedomingo on 4/2/16.
 */
public class ProgressDetailPageFragment extends Fragment {

    public static final String ARG_PROGRESS_IMAGE_URL = "progress_image_url";
    public static final String ARG_PROGRESS_NOTES = "progress_notes";


    @Bind(R.id.progress_image)
    ImageView mImageView;

    @Bind(R.id.progress_notes)
    TextView mTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup v = (ViewGroup) inflater.inflate(R.layout.layout_progress_detail, container, false);
        ButterKnife.bind(this, v);

        final String imageUrl = getArguments().getString(ARG_PROGRESS_IMAGE_URL);
        String notes = getArguments().getString(ARG_PROGRESS_NOTES);

        if (imageUrl != null && mImageView != null) {
            Picasso.with(getActivity()).load(imageUrl).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image).into(mImageView, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    if (imageUrl != null && mImageView != null) {
                        Picasso.with(getActivity()).load(imageUrl).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image).into(mImageView, this);
                    }
                }
            });

        }
        mTextView.setText(notes);


        return v;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    public static ProgressDetailPageFragment newInstance(Progress progress) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PROGRESS_IMAGE_URL, progress.getPictureUrl());
        bundle.putString(ARG_PROGRESS_NOTES, progress.getNote());
        ProgressDetailPageFragment progressDetailPageFragment = new ProgressDetailPageFragment();
        progressDetailPageFragment.setArguments(bundle);
        return progressDetailPageFragment;

    }
}
