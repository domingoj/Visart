package com.fundamentals.visart.ui.home;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundamentals.visart.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @Bind(R.id.home_view_pager)
    ViewPager mViewPager;

    @Bind(R.id.home_tab_layout)
    TabLayout mTabLayout;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, v);

        mViewPager.setAdapter(new HomeFragmentPagerAdapter(getActivity().getSupportFragmentManager(),getActivity()));
        mTabLayout.setupWithViewPager(mViewPager);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
