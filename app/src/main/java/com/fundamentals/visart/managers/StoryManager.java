package com.fundamentals.visart.managers;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;
import com.fundamentals.visart.model.Story;
import com.fundamentals.visart.utils.Constants;
import com.fundamentals.visart.utils.FirebaseRefFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jermiedomingo on 3/19/16.
 */
public class StoryManager {

    public static String createNewStory() {
        Firebase storyRef = FirebaseRefFactory.getFirebaseStoriesRef();
        Firebase newStoryRef = storyRef.push();
        String storyId = newStoryRef.getKey();

        return storyId;


    }


    public static void addNewStory(final String storyId, String title, String description) {

        Firebase newStoryRef = FirebaseRefFactory.getFirebaseStoriesRef().child(storyId);


        /**
         * Set raw version of date to the ServerValue.TIMESTAMP value and save into
         * timestampCreatedMap
         */
        HashMap<String, Object> timestampCreated = new HashMap<>();
        timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

        List<String> tags = new ArrayList<>();
        tags.add("minimalism");
        tags.add("color");
            /* Build the shopping list */
        Story newStory = new Story(FirebaseRefFactory.getAuthId(), title, description, timestampCreated, new ArrayList<String>(), null, tags);

        newStoryRef.setValue(newStory);

        if (!tags.isEmpty()) {

            final Firebase tagsRef = FirebaseRefFactory.getFirebaseTagsRef();
            for (final String tag : tags) {
                //TODO tags implementation

                tagsRef.child(tag).child(Constants.TAGS_STORY_KEYS_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(final DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                        };
                        List<String> storyListIds = new ArrayList<>();
                        if (dataSnapshot != null && dataSnapshot.getValue(t) != null) {
                            storyListIds = dataSnapshot.getValue(t);
                        }
                        storyListIds.add(storyId);
                        HashMap<String, Object> newValues = new HashMap<>();
                        newValues.put(Constants.TAGS_STORY_KEYS_LIST, storyListIds);
                        tagsRef.child(tag).updateChildren(newValues);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }

                });

            }
        }

    }

    public static void updateStoryProgressList(String id, final String progressId) {
        final Firebase storyRef = FirebaseRefFactory.getFirebaseStoriesRef().child(id);


        storyRef.child(Constants.PROGRESS_KEYS_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };
                List<String> progressList = dataSnapshot.getValue(t);
                if (progressList == null) {
                    progressList = new ArrayList<>();
                }
                progressList.add(progressId);

                HashMap<String, Object> newListValues = new HashMap<>();
                newListValues.put(Constants.PROGRESS_KEYS_LIST, progressList);

                storyRef.updateChildren(newListValues);

            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public static void updateStoryLatestUrl(String id, String imageUrl) {

        final Firebase storyRef = FirebaseRefFactory.getFirebaseStoriesRef().child(id);

        HashMap<String, Object> newListValues = new HashMap<>();
        newListValues.put(Constants.LATEST_PICTURE_URL, imageUrl);
        storyRef.updateChildren(newListValues);

    }

    public static void deleteStory(String storyKey) {
        FirebaseRefFactory.getFirebaseStoriesRef().child(storyKey).setValue(null);
    }

}
