package com.fundamentals.visart.utils;

import com.fundamentals.visart.BuildConfig;

/**
 * Created by jermiedomingo on 3/7/16.
 */
public class Constants {

    public static final String UNIQUE_FIRE_BASE_URL = BuildConfig.UNIQUE_FIREBASE_ROOT_URL;

    //Branches
    public static final String STORY = "story";
    public static final String PROGRESS = "progress";
    public static final String USERS = "users";
    public static final String TAGS = "tags";

    public static final String TIME_STAMP_CREATED = "timeStampCreated";


    //PROPERTIES
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";

    //Story
    public static final String USER_ID = "userId";
    public static final String DESCRIPTION = "description";
    public static final String PROGRESS_KEYS_LIST = "progressKeysList";
    public static final String LATEST_PICTURE_URL = "latestPictureUrl";

    //Progress
    public static final String STORY_ID = "storyId";

    //Users
    public static final String USER_FULL_NAME = "displayName";
    public static final String USER_PROFILE_IMAGE_URL = "profileImageURL";
    public static final String USER_PROVIDER = "provider";
    public static final String USER_STORY_KEYS_LIST = "storyKeysList";

    //TAGS
    public static final String TAGS_STORY_KEYS_LIST = "storyKeysList";

    //CLOUDINARY URL
    //// TODO: Put cloudinary url here 
    public static final String CLOUDINARY_URL = "CLOUDINARY URL HERE";

    //FB Constants
    public static final String FB_PROVIDER = "facebook";


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;


}
