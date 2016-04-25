package com.fundamentals.visart.model;

import java.util.List;
import java.util.Map;

/**
 * Created by jermiedomingo on 3/8/16.
 */
public class User {

    private String displayName;
    private String profileImageURL;
    private String provider;
    private List<String> storyKeysList;

    public User() {

    }

    public User(String profileImageURL, String displayName, String provider, List<String> storyKeysList) {
        this.profileImageURL = profileImageURL;
        this.displayName = displayName;
        this.provider = provider;
        this.storyKeysList = storyKeysList;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<String> getStoryKeysList() {
        return storyKeysList;
    }

    public void setStoryKeysList(List<String> storyKeysList) {
        this.storyKeysList = storyKeysList;
    }
}