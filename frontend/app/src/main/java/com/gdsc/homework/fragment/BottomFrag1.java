package com.gdsc.homework.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gdsc.homework.EditActivity;
import com.gdsc.homework.R;
import com.gdsc.homework.SettingsActivity;
import com.google.android.material.tabs.TabLayout;

// 1. 가사 분담
public class BottomFrag1 extends Fragment {

    public BottomFrag1() { }

    public static BottomFrag1 newInstance() {
        BottomFrag1 fragment = new BottomFrag1();
        return fragment;
    }

    public View rootView;

    @Override // 프래그먼트 레이아웃 인플레이트 시키고, 루트 뷰 리턴
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_bottom1, container, false);
        return rootView;
    }

    // View의 초기값 설정 - 바인딩, RecyclerView Adapter 설정 등
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        replaceTopTab(TopFrag1.newInstance());
        chooseTopTab();

        ImageButton btnAdd = rootView.findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), EditActivity.class);
            startActivity(intent);
        });

        ImageButton btnSettings = rootView.findViewById(R.id.btn_settings);
        btnSettings.setOnClickListener(view2 -> {
            Intent intent = new Intent(getContext(), SettingsActivity.class);
            startActivity(intent);
        });

    }

    private void chooseTopTab() {
        TabLayout tabLayout = rootView.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0){
                    replaceTopTab(TopFrag1.newInstance());
                }else{
                    replaceTopTab(TopFrag2.newInstance());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    // 상단 탭 전환
    public void replaceTopTab(Fragment fragment){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.top_tab_container, fragment).commit();
    }

    // 공통: 플로팅 버튼으로 가사분담 다이얼로그 띄우기

}
