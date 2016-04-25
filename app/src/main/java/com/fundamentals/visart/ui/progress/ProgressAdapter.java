package com.fundamentals.visart.ui.progress;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.fundamentals.visart.R;
import com.fundamentals.visart.model.Progress;
import com.fundamentals.visart.utils.Constants;
import com.fundamentals.visart.utils.GeneralViewHolder;
import com.fundamentals.visart.utils.PixelDpConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jermiedomingo on 3/11/16.
 */
public class ProgressAdapter extends RecyclerView.Adapter<GeneralViewHolder> {

    private Activity mActivity;
    private String storyKey;
    ArrayList<Progress> mProgresses;

    public ProgressAdapter(Activity activity, ArrayList<Progress> progresses, String storyKey) {
        mProgresses = progresses;
        mActivity = activity;
        this.storyKey = storyKey;
    }


    @Override
    public GeneralViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        GeneralViewHolder holder;
        View v;
        Context context = parent.getContext();
        if (viewType == Constants.TYPE_HEADER) {

            v = LayoutInflater.from(context).inflate(R.layout.progress_header_card_view_layout, parent, false);
            holder = new ProgressHeaderViewHolder(v);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.progress_card_view_layout, parent, false);
            holder = new ProgressViewHolder(v);
            ((ProgressViewHolder) holder).setProgressAdapter(this);
        }

        return holder;

    }

    @Override
    public void onBindViewHolder(GeneralViewHolder viewHolder, int position) {


        if (getItemViewType(position) == Constants.TYPE_HEADER) {

            //Sets the width of header to FULL SPAN
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setFullSpan(true);
            int margin = (int) PixelDpConverter.pxFromDp(mActivity, 20);
            layoutParams.setMargins(0, margin, 0, margin);
            viewHolder.itemView.setLayoutParams(layoutParams);
            viewHolder.populateViewHolder(mActivity, null, storyKey);


        } else {

            viewHolder.populateViewHolder(mActivity, getItem(position), storyKey);
        }

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mProgresses.size() + 1;
    }


    public Context getContext() {
        return mActivity;
    }

    private Progress getItem(int position) {
        return mProgresses.get(position - 1);
    }

    public int getItemPosition(Progress progress) {
        return mProgresses.indexOf(progress);
    }

    public ArrayList<Progress> getItems() {
        return mProgresses;
    }


}