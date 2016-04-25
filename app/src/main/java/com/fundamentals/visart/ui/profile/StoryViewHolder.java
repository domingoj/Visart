package com.fundamentals.visart.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fundamentals.visart.R;
import com.fundamentals.visart.model.Story;
import com.fundamentals.visart.ui.progress.StoryProgressListActivity;
import com.fundamentals.visart.utils.SquareImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jermiedomingo on 3/9/16.
 */
public class StoryViewHolder extends RecyclerView.ViewHolder implements OnClickListener {


    @Bind(R.id.story_title)
    TextView titleTV;

    SquareImageView mSquareImageView;

    StoryAdapter mStoryAdapter;

    public StoryViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        mSquareImageView = (SquareImageView) itemView.findViewById(R.id.story_photo);
        itemView.setOnClickListener(this);

    }

    public void populateViewHolder(final Context context, final Story story) {

        titleTV.setText(story.getTitle());
        final String pictureUrl = story.getLatestPictureUrl();


        Picasso.with(context).load(pictureUrl).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image).into(mSquareImageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(pictureUrl).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image).into(mSquareImageView, this);

            }
        });

    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(mStoryAdapter.getContext(), StoryProgressListActivity.class);
        String storyKey = mStoryAdapter.getRef(getAdapterPosition()).getKey();
        intent.putExtra(StoryProgressListActivity.STORY_KEY, storyKey);
        ((ProfileActivity) mStoryAdapter.getContext()).startActivityForResult(intent, ProfileActivity.STORY_REQUEST);

    }

    public void setAdapter(StoryAdapter adapter) {
        mStoryAdapter = adapter;
    }


}
