package com.gdsc.homework.fragment;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.gdsc.homework.AutoChargeFragment;
import com.gdsc.homework.MainActivity;
import com.gdsc.homework.PayFragment;
import com.gdsc.homework.R;
import com.gdsc.homework.RESTApi;
import com.gdsc.homework.model.BasicResponse;
import com.gdsc.homework.model.Request_addDeposit;
import com.gdsc.homework.model.Request_getDeposit;
import com.gdsc.homework.model.Response_checkRoom;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

// 4. 저금통
public class BottomFrag4 extends Fragment implements View.OnClickListener {

    private TextView tv_money, tv_autocharge, tv_normalcharge, tv_transfer, tv_usagehistory;
    private LinearLayout ll_normalcharge, ll_autocharge;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private RESTApi mRESTApi;
    private String token;
    private long roomId;
    private int money, money_normalcharge;
    private DecimalFormat formatter;

    private static final String ARG_PARAM = "param";
    private boolean mIsLottieAnimStart;     // true면 pay 이후에 온 거니까 lottie anim 1회 시현, 아주 살짝 딜레이 주고
    private LottieAnimationView animationView;

    public BottomFrag4() {}

    public static BottomFrag4 newInstance(boolean isAfterPayActivity) {
        BottomFrag4 fragment = new BottomFrag4();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM, isAfterPayActivity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsLottieAnimStart = getArguments().getBoolean(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottom4, container, false);

        preferences = view.getContext().getSharedPreferences("data", MODE_PRIVATE);
        editor= preferences.edit();
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        token = preferences.getString("token","");
        roomId = preferences.getLong("roomId",0);
        formatter = new DecimalFormat("###,###");

        tv_money = view.findViewById(R.id.tv_money);
        tv_autocharge = view.findViewById(R.id.tv_autocharge);
        tv_normalcharge = view.findViewById(R.id.tv_normalcharge);
        tv_transfer = view.findViewById(R.id.tv_transfer);
        tv_usagehistory = view.findViewById(R.id.tv_usagehistory);
        ll_normalcharge = view.findViewById(R.id.ll_normalcharge);
        ll_autocharge = view.findViewById(R.id.ll_autocharge);
        animationView = view.findViewById(R.id.lottie_pig);

        tv_autocharge.setOnClickListener(this);
        tv_normalcharge.setOnClickListener(this);
        tv_transfer.setOnClickListener(this);
        tv_usagehistory.setOnClickListener(this);
        ll_normalcharge.setOnClickListener(this);
        ll_autocharge.setOnClickListener(this);

        getDeposit();
        // todo : mIsLottieAnimStart 에 따라 애니메이션 구동
        if (mIsLottieAnimStart) {

            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable()  {
                public void run() {
                    animationView.playAnimation();
                }
            }, 1000);

            animationView.addAnimatorListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) { }

                @Override
                public void onAnimationEnd(Animator animator) {
                    animationView.pauseAnimation();
                }

                @Override
                public void onAnimationCancel(Animator animator) { }

                @Override
                public void onAnimationRepeat(Animator animator) { }
            });
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.ll_autocharge:
                ((MainActivity) requireActivity()).replaceBottomTab(AutoChargeFragment.newInstance());
                ((MainActivity) requireActivity()).setVisibilityBottomNavigation(false);
                break;
            case R.id.ll_normalcharge:
                // dialog로 얼마 충전할 건지 입력
                dialog_normalcharge(view);
                break;
            case R.id.tv_transfer:
                // 토스 참고, 어디로 돈을 보낼까요 ? -> 은행 선택, 계좌 번호 입력 확인버튼 -> 실제로 누르진 않음
                break;
            case R.id.tv_usagehistory:
                break;
        }
    }

    private void getDeposit() {
        Request_getDeposit request_getDeposit = new Request_getDeposit();
        request_getDeposit.setToken(token);
        request_getDeposit.setRoomId(roomId);
        mRESTApi.getDeposit(request_getDeposit).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        // 자리수 parsing 필요
                        money = Integer.parseInt(response.body().getData());
                        tv_money.setText(formatter.format(money) + "원");
                    }
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable throwable) {

            }
        });
    }

    private void addDeposit() {
        Request_addDeposit request_addDeposit = new Request_addDeposit();
        request_addDeposit.setToken(token);
        request_addDeposit.setAmount(money_normalcharge);
        mRESTApi.addDeposit(request_addDeposit).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) { }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable throwable) { }
        });
    }

    public void dialog_normalcharge(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_normalcharge, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        EditText et_money = dialogView.findViewById(R.id.et_money);
        Button ok_btn = dialogView.findViewById(R.id.btn_okay);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                money_normalcharge = Integer.parseInt(et_money.getText().toString());
                alertDialog.dismiss();
                addDeposit();
                ((MainActivity)getActivity()).replaceBottomTab(PayFragment.newInstance("정후네 가족",
                        "일반 충전", money_normalcharge));
                ((MainActivity)getActivity()).setVisibilityBottomNavigation(false);
            }
        });
    }
}