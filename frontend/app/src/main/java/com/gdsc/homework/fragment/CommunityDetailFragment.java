package com.gdsc.homework.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gdsc.homework.MainActivity;
import com.gdsc.homework.R;

public class CommunityDetailFragment extends Fragment {

    private ImageView iv_goback;

    public CommunityDetailFragment() {
        // Required empty public constructor
    }

    public static CommunityDetailFragment newInstance() {
        CommunityDetailFragment fragment = new CommunityDetailFragment();
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

        View view = inflater.inflate(R.layout.fragment_community_detail, container, false);


        iv_goback = view.findViewById(R.id.iv_goback);

        iv_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceBottomTab(BottomFrag3.newInstance());
                ((MainActivity)getActivity()).setVisibilityBottomNavigation(true);
            }
        });

        return view;
    }
}