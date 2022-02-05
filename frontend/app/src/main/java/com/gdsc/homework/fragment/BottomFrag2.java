package com.gdsc.homework.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.gdsc.homework.R;
import com.gdsc.homework.adapter.TabFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

// 2. 통계
public class BottomFrag2 extends Fragment {
    
    private TabLayout mTabs;
    private View mIndicator;
    private ViewPager mViewPager;
    private int indicatorWidth;

    public BottomFrag2() {}

    public static BottomFrag2 newInstance() {
        BottomFrag2 fragment = new BottomFrag2();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_bottom2, container, false);

        mTabs = view.findViewById(R.id.tab);
        mIndicator = view.findViewById(R.id.indicator);
        mViewPager = view.findViewById(R.id.viewPager);

        TabFragmentAdapter adapter = new TabFragmentAdapter(getFragmentManager());
        adapter.addFragment(BottomFragment2_weekly.newInstance(), "주간");
        adapter.addFragment(BottomFragment2_monthly.newInstance(), "월간");
        mViewPager.setAdapter(adapter);
        mTabs.setupWithViewPager(mViewPager);

        return view;
    }
}
