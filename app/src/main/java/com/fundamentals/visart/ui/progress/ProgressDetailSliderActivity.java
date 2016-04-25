package com.fundamentals.visart.ui.progress;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.fundamentals.visart.R;
import com.fundamentals.visart.model.Progress;
import com.fundamentals.visart.utils.AuthChecker;
import com.fundamentals.visart.utils.Constants;
import com.fundamentals.visart.utils.FirebaseRefFactory;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jermiedomingo on 4/2/16.
 */
public class ProgressDetailSliderActivity extends FragmentActivity {

    @Bind(R.id.pager)
    ViewPager mViewPager;

    int position = 0;

    ScreenSlidePagerAdapter mScreenSlidePagerAdapter;

    public static final String EXTRA_PROGRESS_LIST = "progressList";
    public static final String EXTRA_PROGRESS_POSITION = "position";

    private ArrayList<Progress> mProgresses = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AuthChecker.checkUserAuth(this);
        setContentView(R.layout.activity_progress_detail_slider);
        ButterKnife.bind(this);


        position = getIntent().getIntExtra(EXTRA_PROGRESS_POSITION, 0);
        mProgresses = getIntent().getParcelableArrayListExtra(EXTRA_PROGRESS_LIST);
        mScreenSlidePagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mScreenSlidePagerAdapter);
        mViewPager.setCurrentItem(position);


    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            return ProgressDetailPageFragment.newInstance(mProgresses.get(position));
        }

        @Override
        public int getCount() {

            return mProgresses.size();
        }
    }
}
