package com.fundamentals.visart.ui.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import com.fundamentals.visart.R;
import com.fundamentals.visart.managers.StoryManager;
import com.fundamentals.visart.managers.UserManager;
import com.fundamentals.visart.utils.AuthChecker;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jermiedomingo on 3/26/16.
 */


public class StoryEditActivity extends AppCompatActivity {

    @Bind(R.id.input_title)
    EditText mTitleEditText;

    @Bind(R.id.input_description)
    EditText mDescriptionEditText;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthChecker.checkUserAuth(this);
        setContentView(R.layout.activity_story_edit);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_story, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_create: {

                String title = mTitleEditText.getText().toString();
                String description = mDescriptionEditText.getText().toString();


                if (title.equals("") || title.isEmpty()) {
                    title = "Untitled";
                }

                final String storyId = StoryManager.createNewStory();

                //Setting up values for the newly created story
                StoryManager.addNewStory(storyId, title, description);

                //Adding the newly created story to the user's story list
                UserManager.updateUserStoryList(storyId);
                finish();
                return true;
            }


            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        finish(); // close this activity as oppose to navigating up
        return false;
    }
}
