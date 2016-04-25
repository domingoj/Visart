package com.fundamentals.visart.ui.profile;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.fundamentals.visart.model.Story;

/**
 * Created by jermiedomingo on 3/11/16.
 */
public class StoryAdapter extends FirebaseRecyclerAdapter<Story, StoryViewHolder> {

    private Activity mActivity;

    public StoryAdapter(Activity activity, Class<Story> modelClass, int modelLayout, Class<StoryViewHolder> viewHolderClass, Query ref) {
        super(modelClass, modelLayout, viewHolderClass, ref);
        mActivity = activity;
    }


    @Override
    protected void populateViewHolder(StoryViewHolder storyViewHolder, Story story, int i) {
        storyViewHolder.populateViewHolder(mActivity, story);
    }

    @Override
    public StoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        StoryViewHolder viewHolder = super.onCreateViewHolder(parent, viewType);
        viewHolder.setAdapter(this);
        return viewHolder;
    }

    public Context getContext() {
        return mActivity;
    }
}
