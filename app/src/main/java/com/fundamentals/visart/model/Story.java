package com.fundamentals.visart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firebase.client.ServerValue;
import com.fundamentals.visart.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jermiedomingo on 3/7/16.
 */
public class Story implements Parcelable {

    @JsonIgnore
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String userId;
    private String title;
    private String description;
    private HashMap<String, Object> timestampCreated;
    private HashMap<String, Object> timestampLastChanged;
    private List<String> progressKeysList;
    private String latestPictureUrl;
    private List<String> tags;

    public Story() {
    }

    public Story(String userId, String title, String description, HashMap<String, Object> timestampCreated, List<String> progressKeysList, String latestPictureUrl, List<String> tags) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.timestampCreated = timestampCreated;
        HashMap<String, Object> timestampNowObject = new HashMap<>();
        timestampNowObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
        this.progressKeysList = progressKeysList;
        this.latestPictureUrl = latestPictureUrl;
        this.tags = tags;
    }


    public String getTitle() {
        return title;
    }


    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    public HashMap<String, Object> getTimestampLastChanged() {
        return timestampLastChanged;
    }


    public String getLatestPictureUrl() {
        return latestPictureUrl;
    }

    @JsonIgnore
    public long getTimestampLastChangedLong() {

        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    @JsonIgnore
    public long getTimestampCreatedLong() {
        return (long) timestampLastChanged.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    public void setTimestampLastChangedToNow() {
        HashMap<String, Object> timestampNowObject = new HashMap<>();
        timestampNowObject.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);
        this.timestampLastChanged = timestampNowObject;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getProgressKeysList() {
        return progressKeysList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.userId);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeSerializable(this.timestampCreated);
        dest.writeSerializable(this.timestampLastChanged);
        dest.writeStringList(this.progressKeysList);
        dest.writeString(this.latestPictureUrl);
        dest.writeStringList(this.tags);
    }

    protected Story(Parcel in) {
        this.id = in.readString();
        this.userId = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.timestampCreated = (HashMap<String, Object>) in.readSerializable();
        this.timestampLastChanged = (HashMap<String, Object>) in.readSerializable();
        this.progressKeysList = in.createStringArrayList();
        this.latestPictureUrl = in.readString();
        this.tags = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Story> CREATOR = new Parcelable.Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel source) {
            return new Story(source);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };
}
