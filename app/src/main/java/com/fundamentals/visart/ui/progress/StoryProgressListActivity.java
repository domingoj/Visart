package com.fundamentals.visart.ui.progress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.fundamentals.visart.R;
import com.fundamentals.visart.managers.StoryManager;
import com.fundamentals.visart.model.Progress;
import com.fundamentals.visart.model.Story;
import com.fundamentals.visart.ui.authentication.LoginActivity;
import com.fundamentals.visart.utils.AuthChecker;
import com.fundamentals.visart.utils.Constants;
import com.fundamentals.visart.utils.FirebaseRefFactory;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jermiedomingo on 3/11/16.
 */
public class StoryProgressListActivity extends AppCompatActivity {

    @Bind(R.id.progress_recycler_view)
    RecyclerView recyclerView;


    @Bind(R.id.latest_picture_image_view)
    ImageView mLatestPictureImageView;

    /*@Bind(R.id.story_description)
    TextView descriptionTv;
*/
    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    Firebase storyRef;

    public static final String STORY_KEY = "STORY_KEY";
    public static final int STORY_DELETED_RESULT = 99;
    public static final String STORY_EXTRA = "storyExtra";

    private String storyKey;
    private ProgressAdapter mProgressAdapter;
    private Story story;

    private ArrayList<Progress> mProgresses = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthChecker.checkUserAuth(this);

        FirebaseRefFactory.getFirebaseRef().addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {

                if (authData == null) {
                    startActivity(new Intent(StoryProgressListActivity.this, LoginActivity.class));
                }

            }
        });


        setContentView(R.layout.activity_story_progress_list);
        ButterKnife.bind(this);


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        storyKey = getIntent().getStringExtra(STORY_KEY);
        storyRef = FirebaseRefFactory.getFirebaseStoriesRef().child(storyKey);
        storyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    story = dataSnapshot.getValue(Story.class);

                    if (story != null) {
                        story.setId(storyKey);

                        final String pictureUrl = story.getLatestPictureUrl();

                        collapsingToolbar.setTitle(story.getTitle());

                        if (pictureUrl != null && !pictureUrl.isEmpty()) {
                            Picasso.with(StoryProgressListActivity.this).load(pictureUrl).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image).into(mLatestPictureImageView, new Callback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onError() {
                                    Picasso.with(StoryProgressListActivity.this).load(pictureUrl).error(R.drawable.placeholder_image).placeholder(R.drawable.placeholder_image).into(mLatestPictureImageView, this);

                                }
                            });
                        }
                    }
                }
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));


        Query query = FirebaseRefFactory.getFirebaseProgressRef().orderByChild(Constants.STORY_ID).equalTo(storyKey);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    mProgresses.clear();
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        mProgresses.add(data.getValue(Progress.class));
                    }
                }
                mProgressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mProgressAdapter = new ProgressAdapter(this, mProgresses, storyKey);
        recyclerView.setAdapter(mProgressAdapter);


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


    }


    @OnClick(R.id.uploadPhotosFAB)
    void addNewProgress() {
        Intent intent = new Intent(this, ProgressEditActivity.class);
        intent.putExtra(ProgressEditActivity.EXTRA_STORY_KEY, storyKey);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_story_progress_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            Intent intent = new Intent();
            intent.putExtra(STORY_EXTRA, story);
            setResult(STORY_DELETED_RESULT, intent);
            StoryManager.deleteStory(storyKey);
            finish();
            return true;
        } else if (id == R.id.action_logout) {
            FirebaseRefFactory.getFirebaseRef().unauth();
            return true;

        } else
            return super.onOptionsItemSelected(item);
    }


}
