package com.gdsc.homework;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gdsc.homework.fragment.BottomFrag4;

public class AutoChargeFragment extends Fragment implements View.OnClickListener {

    private Button btn_goback;

    public AutoChargeFragment() { }

    public static AutoChargeFragment newInstance() {
        AutoChargeFragment fragment = new AutoChargeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_autocharge, container, false);

        btn_goback = view.findViewById(R.id.btn_goback);
        btn_goback.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_goback:
                ((MainActivity)getActivity()).replaceBottomTab(BottomFrag4.newInstance(false));
                ((MainActivity)getActivity()).setVisibilityBottomNavigation(true);
                break;
        }
    }
}