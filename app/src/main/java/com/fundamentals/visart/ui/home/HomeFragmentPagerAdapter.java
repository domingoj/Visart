package com.fundamentals.visart.ui.home;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by jermiedomingo on 3/18/16.
 */
public class HomeFragmentPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[]{"Topics", "Hot", "Subscriptions"};
    private Context context;


    public HomeFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
            return new TopicsFragment();
        } else if (position == 1) {
            return new WhatsHotFragment();
        } else if (position == 2) {
            return new SubscriptionsFragment();
        } else return new TopicsFragment();
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
