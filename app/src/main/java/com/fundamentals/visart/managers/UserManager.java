package com.fundamentals.visart.managers;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.GenericTypeIndicator;
import com.firebase.client.ValueEventListener;
import com.fundamentals.visart.R;
import com.fundamentals.visart.model.User;
import com.fundamentals.visart.utils.Constants;
import com.fundamentals.visart.utils.FirebaseRefFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by jermiedomingo on 3/19/16.
 */
public class UserManager {


    public static void updateUser(AuthData authData) {

        Map<String, Object> map = new HashMap<>();
        map.put(Constants.USER_PROVIDER, authData.getProvider());
        if (authData.getProviderData().containsKey(Constants.USER_FULL_NAME)) {
            map.put(Constants.USER_FULL_NAME, authData.getProviderData().get(Constants.USER_FULL_NAME).toString());
        }

        if (authData.getProviderData().containsKey(Constants.USER_PROFILE_IMAGE_URL)) {
            map.put(Constants.USER_PROFILE_IMAGE_URL, authData.getProviderData().get(Constants.USER_PROFILE_IMAGE_URL).toString());
        }

        FirebaseRefFactory.getFirebaseUsersRef().child(authData.getUid()).updateChildren(map);

    }

    public static void updateUserStoryList(final String storyId) {

        final Firebase userRef = FirebaseRefFactory.getFirebaseUsersRef().child(FirebaseRefFactory.getAuthId());
        userRef.child(Constants.USER_STORY_KEYS_LIST).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<List<String>> t = new GenericTypeIndicator<List<String>>() {
                };

                List<String> storyList = dataSnapshot.getValue(t);
                if (storyList == null) {
                    storyList = new ArrayList<>();
                }
                storyList.add(storyId);

                HashMap<String, Object> newListValues = new HashMap<>();
                newListValues.put(Constants.USER_STORY_KEYS_LIST, storyList);

                userRef.updateChildren(newListValues);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
}
