package com.fundamentals.visart.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fundamentals.visart.utils.Constants;

import java.util.HashMap;

/**
 * Created by jermiedomingo on 3/7/16.
 */
public class Progress implements Parcelable {

    private String storyId;
    private HashMap<String, Object> timestampCreated;
    private String pictureUrl;
    private String note;

    public Progress() {
    }

    public Progress(String storyId, HashMap<String, Object> timestampCreated, String pictureUrl, String note) {
        this.storyId = storyId;
        this.timestampCreated = timestampCreated;
        this.pictureUrl = pictureUrl;
        this.note = note;
    }


    public String getStoryId() {
        return storyId;
    }


    public HashMap<String, Object> getTimestampCreated() {
        return timestampCreated;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getNote() {
        return note;
    }

    @JsonIgnore
    public long gettimestampCreatedLong() {
        return (long) timestampCreated.get(Constants.FIREBASE_PROPERTY_TIMESTAMP);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.storyId);
        dest.writeSerializable(this.timestampCreated);
        dest.writeString(this.pictureUrl);
        dest.writeString(this.note);
    }

    protected Progress(Parcel in) {
        this.storyId = in.readString();
        this.timestampCreated = (HashMap<String, Object>) in.readSerializable();
        this.pictureUrl = in.readString();
        this.note = in.readString();
    }

    public static final Parcelable.Creator<Progress> CREATOR = new Parcelable.Creator<Progress>() {
        @Override
        public Progress createFromParcel(Parcel source) {
            return new Progress(source);
        }

        @Override
        public Progress[] newArray(int size) {
            return new Progress[size];
        }
    };
}
