package com.gdsc.homework.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.gdsc.homework.MainActivity;
import com.gdsc.homework.R;

// 3. 커뮤니티, 전문가 상담
public class BottomFrag3 extends Fragment {

    private LinearLayout ll_commu1;
    private ImageView tint_1,tint_2,tint_3,tint_4;

    public BottomFrag3() {}

    public static BottomFrag3 newInstance() {
        BottomFrag3 fragment = new BottomFrag3();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bottom3, container, false);

        tint_1 = view.findViewById(R.id.tint_1);
        tint_2 = view.findViewById(R.id.tint_2);
        tint_3 = view.findViewById(R.id.tint_3);
        tint_4 = view.findViewById(R.id.tint_4);

        tint_1.setColorFilter(Color.parseColor("#FF9E3C"), PorterDuff.Mode.SRC_IN);
        tint_2.setColorFilter(Color.parseColor("#FF9E3C"), PorterDuff.Mode.SRC_IN);
        tint_3.setColorFilter(Color.parseColor("#FF9E3C"), PorterDuff.Mode.SRC_IN);
        tint_4.setColorFilter(Color.parseColor("#FF9E3C"), PorterDuff.Mode.SRC_IN);

        ll_commu1 = view.findViewById(R.id.ll_commu1);
        ll_commu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) requireActivity()).replaceBottomTab(CommunityDetailFragment.newInstance());
                ((MainActivity) requireActivity()).setVisibilityBottomNavigation(false);
            }
        });

        return view;
    }
}