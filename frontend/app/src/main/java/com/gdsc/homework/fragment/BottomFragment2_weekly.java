package com.gdsc.homework.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdsc.homework.R;

public class BottomFragment2_weekly extends Fragment {

    public BottomFragment2_weekly() {
        // Required empty public constructor
    }

    public static BottomFragment2_weekly newInstance() {
        BottomFragment2_weekly fragment = new BottomFragment2_weekly();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_fragment2_weekly, container, false);
    }
}