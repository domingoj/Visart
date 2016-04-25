package com.fundamentals.visart.ui.progress;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.fundamentals.visart.R;
import com.fundamentals.visart.model.Progress;
import com.fundamentals.visart.utils.FirebaseRefFactory;
import com.fundamentals.visart.utils.GeneralViewHolder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jermiedomingo on 3/22/16.
 */
public class ProgressHeaderViewHolder extends GeneralViewHolder {

    @Bind(R.id.progress_notes)
    TextView notesTV;

    public ProgressHeaderViewHolder(final View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void populateViewHolder(final Context context, Progress progress, String storyKey) {


        FirebaseRefFactory.getFirebaseStoriesRef().child(storyKey).child("description").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                notesTV.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }

}
