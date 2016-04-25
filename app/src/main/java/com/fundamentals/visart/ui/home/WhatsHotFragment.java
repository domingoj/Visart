package com.fundamentals.visart.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundamentals.visart.R;

/**
 * Created by jermiedomingo on 3/18/16.
 */
public class WhatsHotFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_whats_hot,container,false);
        return v;
    }
}
