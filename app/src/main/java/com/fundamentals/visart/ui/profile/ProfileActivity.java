package com.fundamentals.visart.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;
import com.fundamentals.visart.R;
import com.fundamentals.visart.model.Story;
import com.fundamentals.visart.model.User;
import com.fundamentals.visart.ui.progress.StoryProgressListActivity;
import com.fundamentals.visart.utils.AuthChecker;
import com.fundamentals.visart.utils.Constants;
import com.fundamentals.visart.utils.FirebaseRefFactory;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jermiedomingo on 3/22/16.
 */
public class ProfileActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.stories_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.profile_image)
    CircleImageView profileImageView;
    @Bind(R.id.user_full_name)
    TextView userFullNameTextView;

    private FirebaseRecyclerAdapter mAdapter;

    public static final int STORY_REQUEST = 50;

    public static final String EXTRA_FULL_NAME = "fullName";
    public static final String EXTRA_PROFILE_IMAGE_URL = "imageUrl";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthChecker.checkUserAuth(this);

        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent().getStringExtra(EXTRA_PROFILE_IMAGE_URL) != null && !getIntent().getStringExtra(EXTRA_FULL_NAME).isEmpty()) {
            Picasso.with(this).load(getIntent().getStringExtra(EXTRA_PROFILE_IMAGE_URL)).into(profileImageView);
            userFullNameTextView.setText(getIntent().getStringExtra(EXTRA_FULL_NAME));
        } else {
            Firebase userRef = FirebaseRefFactory.getFirebaseUsersRef().child(FirebaseRefFactory.getAuthId());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        User user = dataSnapshot.getValue(User.class);
                        Picasso.with(ProfileActivity.this).load(user.getProfileImageURL()).into(profileImageView);
                        userFullNameTextView.setText(user.getDisplayName());

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }


        mRecyclerView.setLayoutManager(new GridLayoutManager(null,2, GridLayoutManager.VERTICAL,false));

        Firebase mRef = new Firebase(Constants.UNIQUE_FIRE_BASE_URL).child(Constants.STORY);
        Query query = mRef.orderByChild(Constants.USER_ID).equalTo(mRef.getAuth().getUid());

        mAdapter = new StoryAdapter(this, Story.class, R.layout.story_card_view_layout, StoryViewHolder.class, query);
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.uploadPhotosFAB)
    void addStory() {


        Intent intent = new Intent(ProfileActivity.this, StoryEditActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == STORY_REQUEST && resultCode == StoryProgressListActivity.STORY_DELETED_RESULT && data !=null) {
            final Story story = data.getParcelableExtra(StoryProgressListActivity.STORY_EXTRA);

            Snackbar.make(findViewById(R.id.main_content), "Story Deleted", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseRefFactory.getFirebaseStoriesRef().child(story.getId()).setValue(story);
                }
            }).setActionTextColor(getResources().getColor(R.color.colorAccent)).show();
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mAdapter.cleanup();
    }


}
