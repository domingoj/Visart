package com.fundamentals.visart.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fundamentals.visart.model.Progress;

/**
 * Created by jermiedomingo on 3/26/16.
 */
public abstract class GeneralViewHolder extends RecyclerView.ViewHolder {
    public GeneralViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void populateViewHolder(final Context context, Progress progress, String storyKey);


}
