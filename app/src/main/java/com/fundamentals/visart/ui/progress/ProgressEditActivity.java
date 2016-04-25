package com.fundamentals.visart.ui.progress;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.fundamentals.visart.R;
import com.fundamentals.visart.managers.ProgressManager;
import com.fundamentals.visart.managers.StoryManager;
import com.fundamentals.visart.utils.AuthChecker;
import com.fundamentals.visart.utils.CloudinaryClient;
import com.fundamentals.visart.utils.ImageFileCreatorUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jermiedomingo on 4/2/16.
 */
public class ProgressEditActivity extends AppCompatActivity {


    @Bind(R.id.progress_photo)
    ImageView mProgressImageView;

    @Bind(R.id.progress_notes)
    EditText mProgressNotesEditText;

    private String storyKey;

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;

    private static final int PICK_IMAGE_FROM_GALLERY_REQUEST_CODE = 0;
    public static final String EXTRA_STORY_KEY = "storyKeyExtra";

    Uri selectedImageUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthChecker.checkUserAuth(this);
        setContentView(R.layout.activity_progress_edit);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);

        storyKey = getIntent().getStringExtra(EXTRA_STORY_KEY);
        uploadPhotoFromGallery();
    }


    @OnClick(R.id.progress_photo)
    void uploadPhotoFromGallery() {
        startActivityForResult(
                Intent.createChooser(
                        new Intent(Intent.ACTION_GET_CONTENT)
                                .setType("image/*"), "Choose an image"), PICK_IMAGE_FROM_GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == PICK_IMAGE_FROM_GALLERY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                selectedImageUri = data.getData();
                mProgressImageView.setImageURI(selectedImageUri);


            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_progress, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_add_progress: {

                addProgress();
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


    private void addProgress() {
        String progressNotes = mProgressNotesEditText.getText().toString();

        if (selectedImageUri != null && !selectedImageUri.toString().isEmpty()) {

            //Get the ID of the newly created progress
            final String newProgressKey = ProgressManager.createProgress();

            //Creating the File object from the image Uri
            final File imageFile = ImageFileCreatorUtil.getImageFileFromUri(ProgressEditActivity.this, selectedImageUri);

            //UPLOAD IMAGE TO CLOUDINARY
            CloudinaryClient.upload(imageFile, newProgressKey);

            //Retrieve Image Url
            final String imageUrl = CloudinaryClient.generateUrl(newProgressKey);

            //Update values of the newly created empty progress
            ProgressManager.addProgress(storyKey, newProgressKey, progressNotes, imageUrl);

            //Update Story's progress list and image url
            StoryManager.updateStoryProgressList(storyKey, newProgressKey);
            StoryManager.updateStoryLatestUrl(storyKey, imageUrl);

            finish();
        }
    }

}
