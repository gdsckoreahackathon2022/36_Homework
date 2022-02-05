package com.gdsc.homework.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdsc.homework.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class BottomFragment2_monthly extends Fragment {

    private PieChart chart;

    public BottomFragment2_monthly() { }

    public static BottomFragment2_monthly newInstance() {
        BottomFragment2_monthly fragment = new BottomFragment2_monthly();
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

        View view = inflater.inflate(R.layout.fragment_bottom_fragment2_monthly, container, false);

        chart = view.findViewById(R.id.tab1_chart_1);
        setPieChart();

        return view;
    }

    private void setPieChart() {

        chart.clearChart();

        chart.addPieSlice(new PieModel("정후", 40, Color.parseColor("#D64B25")));
        chart.addPieSlice(new PieModel("혁준", 28, Color.parseColor("#F77D47")));
        chart.addPieSlice(new PieModel("진아", 22, Color.parseColor("#FF9E3C")));
        chart.addPieSlice(new PieModel("하은", 10, Color.parseColor("#FFB612")));

        chart.startAnimation();

    }
}