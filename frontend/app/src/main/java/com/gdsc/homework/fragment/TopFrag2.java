package com.gdsc.homework.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdsc.homework.R;
import com.gdsc.homework.adapter.FamilyAdapter;
import com.gdsc.homework.model.FamilyChores;

import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

// 전체 페이지 탭
public class TopFrag2 extends Fragment {
    private View rootView;

    public TopFrag2() {
        // Required empty public constructor
    }

    public static TopFrag2 newInstance() {
        TopFrag2 fragment = new TopFrag2();
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
        rootView = inflater.inflate(R.layout.fragment_top2, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        showFamilyCalendar(R.id.wholeCalendarView);
        showFamilyList();
    }

    // list_item_family
    private void showFamilyList() {
        ArrayList<FamilyChores> dataSet = new ArrayList<FamilyChores>() {{
            add(new FamilyChores(R.drawable.ic_profile_black,"진아   세탁",
                    "세제 많이 넣지마~"));
            add(new FamilyChores(R.drawable.ic_profile_black, "혁준   분리수거",
                    "플라스틱 생수병에 라벨 제거 꼭 하기!"));
            add(new FamilyChores(R.drawable.ic_profile_black,"하은   설거지",
                    "음식물 쓰레기도 처리하는 거 잊지마!"));
            add(new FamilyChores(R.drawable.ic_profile_black, "정후   화장실 청소",
                    "이번 주 화장실 청소 담당 화이팅~!"));
        }};

        RecyclerView familyRecyclerView = rootView.findViewById(R.id.familyList);

        // 레이아웃 매니저, 어댑터 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        familyRecyclerView.setLayoutManager(layoutManager);
        FamilyAdapter adapter = new FamilyAdapter(dataSet);
        familyRecyclerView.setAdapter(adapter);

        // 구분선 추가
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(familyRecyclerView.getContext(),
                layoutManager.getOrientation());
        familyRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    public void showFamilyCalendar(int viewId) {
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        // setting up our horizontal calendar view and passing id our calendar view to it.

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(requireActivity(), viewId)
                // add a range as start date and end date to our calendar.
                .range(startDate, endDate)
                // provide a number of dates which will be visible on the screen at a time.
                .datesNumberOnScreen(7)
                .configure()
                .showTopText(false)
                .end()
                // at last, call a build method to build our horizontal recycler view.
                .build();

        // 선택된 날짜에 따라 다른 리스트 보여주기
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                // 요일에 따라 다른 리스트 보여주기

            }
        });
    }
}