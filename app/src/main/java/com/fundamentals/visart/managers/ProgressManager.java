package com.fundamentals.visart.managers;

import com.firebase.client.Firebase;
import com.firebase.client.ServerValue;
import com.fundamentals.visart.model.Progress;
import com.fundamentals.visart.utils.Constants;
import com.fundamentals.visart.utils.FirebaseRefFactory;

import java.util.HashMap;

/**
 * Created by jermiedomingo on 3/19/16.
 */
public class ProgressManager {


    public static String createProgress() {
        Firebase progressRef = FirebaseRefFactory.getFirebaseProgressRef();
        Firebase newProgressRef = progressRef.push();
        String newProgressKey = newProgressRef.getKey();
        return newProgressKey;
    }

    public static void addProgress(String storyKey, String newProgressKey, String progressNotes, String imageUrl) {

        Firebase newProgressRef = FirebaseRefFactory.getFirebaseProgressRef().child(newProgressKey);

        HashMap<String, Object> timestampCreated = new HashMap<>();
        timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);

        Progress newProgress = new Progress(storyKey, timestampCreated, imageUrl, progressNotes);

        // Do the update
        newProgressRef.setValue(newProgress);


    }
}
