package com.fundamentals.visart.utils;

import com.firebase.client.Firebase;

/**
 * Created by jermiedomingo on 3/19/16.
 */
public class FirebaseRefFactory {

    private static Firebase firebaseRef = new Firebase(Constants.UNIQUE_FIRE_BASE_URL);

    public static Firebase getFirebaseRef() {
        return firebaseRef;
    }

    public static String getAuthId() {
        return firebaseRef.getAuth().getUid();
    }

    //STORIES
    public static Firebase getFirebaseStoriesRef() {
        return firebaseRef.child(Constants.STORY);
    }


    //PROGRESS
    public static Firebase getFirebaseProgressRef() {
        return firebaseRef.child(Constants.PROGRESS);
    }

    //USERS

    public static Firebase getFirebaseUsersRef() {
        return firebaseRef.child(Constants.USERS);
    }


    //TAGS
    public static Firebase getFirebaseTagsRef() {
        return firebaseRef.child(Constants.TAGS);
    }


}
