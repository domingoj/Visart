package com.fundamentals.visart.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.fundamentals.visart.R;
import com.fundamentals.visart.model.User;
import com.fundamentals.visart.ui.authentication.LoginActivity;
import com.fundamentals.visart.ui.home.HomeFragment;
import com.fundamentals.visart.ui.profile.ProfileActivity;
import com.fundamentals.visart.utils.AuthChecker;
import com.fundamentals.visart.utils.FirebaseRefFactory;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tool_bar)
    Toolbar mToolbar;
    @Bind(R.id.navigation_view)
    NavigationView mNavigationView;
    @Bind(R.id.drawer)
    DrawerLayout mDrawerLayout;

    private String userFullName;
    private String profileImageUrl;

    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (AuthChecker.checkUserAuth(this)) {
            finish();
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        Firebase userRef = FirebaseRefFactory.getFirebaseUsersRef().child(FirebaseRefFactory.getAuthId());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    User user = dataSnapshot.getValue(User.class);
                    View header = mNavigationView.getHeaderView(0);
                    TextView userNameTextView = (TextView) header.findViewById(R.id.username);
                    CircleImageView profileImageImageVIew = (CircleImageView) header.findViewById(R.id.profile_image);

                    userFullName = user.getDisplayName();
                    profileImageUrl = user.getProfileImageURL();

                    userNameTextView.setText(userFullName);
                    Picasso.with(MainActivity.this).load(profileImageUrl).into(profileImageImageVIew);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        View header = mNavigationView.getHeaderView(0);


        TextView textView = (TextView) header.findViewById(R.id.see_profile);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(ProfileActivity.EXTRA_FULL_NAME, userFullName);
                intent.putExtra(ProfileActivity.EXTRA_PROFILE_IMAGE_URL, profileImageUrl);
                mDrawerLayout.closeDrawers();
                startActivity(intent);
            }
        });


        //Initializing Toolbar
        setSupportActionBar(mToolbar);


        //Initializing NavigationView
        setUpDrawerContent();

        //Initializing DrawerLayout

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame, new HomeFragment()).commit();
            setTitle(R.string.home_string);
        }

    }


    private void setUpDrawerContent() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                selectDrawerItem(menuItem);
                return true;
            }

        });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment;


        switch (menuItem.getItemId()) {
            case R.id.home:
                fragment = new HomeFragment();
                break;

            default:
                fragment = new HomeFragment();
                break;

        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame, fragment).addToBackStack(null).commit();

        // Highlight the selected item, update the title, and close the menu_drawer
        // Highlight the selected item has been done by NavigationView
        // menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        mDrawerLayout.closeDrawers();
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the menu_drawer toggles
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}
