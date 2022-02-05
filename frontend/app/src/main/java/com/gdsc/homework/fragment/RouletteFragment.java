package com.gdsc.homework.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.Image;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gdsc.homework.IntroActivity;
import com.gdsc.homework.MainActivity;
import com.gdsc.homework.R;
import com.gdsc.homework.RESTApi;
import com.gdsc.homework.model.BasicResponse;
import com.gdsc.homework.model.Request_addDeposit;
import com.gdsc.homework.model.Request_participateRoom;
import com.gdsc.homework.model.Request_rouletteResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class RouletteFragment extends Fragment {

    private CircleManager circleManager;
    private RelativeLayout layoutRoulette;

    private Button btnRotate;
    private TextView tvResult;
    private ImageView iv_goback;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private RESTApi mRESTApi;
    private String token;

    private ArrayList<String> STRINGS;
    private float initAngle = 0.0f;
    private int num_roulette;

    public RouletteFragment() {
    }

    public static RouletteFragment newInstance() {
        RouletteFragment fragment = new RouletteFragment();
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
        View view = inflater.inflate(R.layout.fragment_roulette, container, false);


        tvResult = view.findViewById(R.id.tvResult);
        iv_goback = view.findViewById(R.id.iv_goback);
        btnRotate = view.findViewById(R.id.btnRotate);
        layoutRoulette = view.findViewById(R.id.layoutRoulette);


        preferences = view.getContext().getSharedPreferences("data", MODE_PRIVATE);
        editor= preferences.edit();
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        token = preferences.getString("token","");

        num_roulette = 4;
        STRINGS = new ArrayList<>();
        STRINGS.add("정후");
        STRINGS.add("혁준");
        STRINGS.add("하은");
        STRINGS.add("진아");
        //STRINGS = setRandom(1000, num_roulette);
        circleManager = new CircleManager(getContext(), num_roulette);
        layoutRoulette.addView(circleManager);

        btnRotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 돈이 빠져나가는데 그래도 하실건지 물음 dialog
                dialog_base(view);
            }
        });

        iv_goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).replaceBottomTab(TopFrag1.newInstance());
                ((MainActivity)getActivity()).setVisibilityBottomNavigation(true);
            }
        });

        return view;
    }

    public void rotateLayout(final RelativeLayout layout, final int num) {
        final float fromAngle = getRandom(360) + 3600 + initAngle;

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getResult(fromAngle, num); // start when animation complete
            }
        }, 3000);

        RotateAnimation rotateAnimation = new RotateAnimation(initAngle, fromAngle,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(AnimationUtils.loadInterpolator(getContext(), android.R.anim.accelerate_decelerate_interpolator));
        rotateAnimation.setDuration(3000);
        rotateAnimation.setFillEnabled(true);
        rotateAnimation.setFillAfter(true);
        layout.startAnimation(rotateAnimation);
    }

    // Set numbers on roulette to random
    public ArrayList<String> setRandom(int maxNumber, int num) {
        ArrayList<String> strings = new ArrayList<>();

        double r = Math.random();

        for (int i = 0; i < num; i++) {
            int rand = (int) (r * maxNumber);
            strings.add(String.valueOf(rand));
            r = Math.random();
        }

        return strings;
    }

    // get Angle to random
    private int getRandom(int maxNumber) {
        double r = Math.random();
        return (int)(r * maxNumber);
    }

    private void getResult(float angle, int num_roulette) {
        String text = "";
        angle = angle % 360;

        if (angle > 350 || angle <= 80) {
            text = STRINGS.get(2);
            buildAlert(text);
        } else if (angle > 80 && angle <= 170) {
            text = STRINGS.get(1);
            buildAlert(text);
        } else if (angle > 170 && angle <= 260) {
            text = STRINGS.get(0);
            buildAlert(text);
        } else if (angle > 260 && angle <= 350) {
            text = STRINGS.get(3);
            buildAlert(text);
        }

        tvResult.setText("Result : " + text);
    }

    // if you want use AlertDialog then use this
    private void buildAlert(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Congratulations")
                .setMessage(text+"님께 집안일을 넘겨드렸어요")
                .setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        layoutRoulette.setRotation(360 - initAngle);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        // todo : 서버 통신
        rouletteResult();
    }

    public class CircleManager extends View {
        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        private int[] COLORS = {ContextCompat.getColor(getContext(), R.color.main_yellow),
                ContextCompat.getColor(getContext(), R.color.main_lightyellow),
                ContextCompat.getColor(getContext(), R.color.main_redyellowyellow),
                ContextCompat.getColor(getContext(), R.color.main_redredyellow)};
        private int num;

        public CircleManager(Context context, int num) {
            super(context);
            this.num = num;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            int width = layoutRoulette.getWidth();
            int height = layoutRoulette.getHeight();
            int sweepAngle = 360 / num;

            RectF rectF = new RectF(0, 0, width, height);
            Rect rect = new Rect(0, 0, width, height);

            int centerX = (rect.left + rect.right) / 2;
            int centerY = (rect.top + rect.bottom) / 2;
            int radius = (rect.right - rect.left) / 2;
            radius -= 10;

            int temp = 0;

            for (int i = 0; i < num; i++) {
                paint.setColor(COLORS[i]);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                paint.setAntiAlias(true);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawArc(rectF, temp, sweepAngle, true, paint);

                float medianAngle = (temp + (sweepAngle / 2f)) * (float) Math.PI / 180f;

                paint.setColor(Color.BLACK);
                paint.setTextSize(64);
                paint.setStyle(Paint.Style.FILL_AND_STROKE);

                float arcCenterX = (float) (centerX + (radius * Math.cos(medianAngle))); // Arc's center X
                float arcCenterY = (float) (centerY + (radius * Math.sin(medianAngle))); // Arc's center Y

                // put text at middle of Arc's center point and Circle's center point
                float textX = (centerX + arcCenterX) / 2;
                float textY = (centerY + arcCenterY) / 2;

                canvas.drawText(STRINGS.get(i), textX, textY, paint);
                temp += sweepAngle;
            }
        }
    }

    public void dialog_base(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_base, null);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        Button btn_okay_broker = dialogView.findViewById(R.id.btn_okay_broker);
        btn_okay_broker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 서버 통신
                addDeposit();
                alertDialog.dismiss();
            }
        });
    }

    private void rouletteResult() {
        Request_rouletteResult request_rouletteResult = new Request_rouletteResult();
        request_rouletteResult.setToken(token);
        request_rouletteResult.setHouseworkId(Long.parseLong("1"));
        request_rouletteResult.setUserId(Long.parseLong("3"));

        mRESTApi.rouletteResult(request_rouletteResult)
                .enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                        if (response.body().getStatus() == 200) {
                            ((MainActivity) requireActivity()).replaceBottomTab(BottomFrag1.newInstance());
                            ((MainActivity) requireActivity()).setVisibilityBottomNavigation(true);
                        }
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable throwable) {
                        Log.d("LoginActivity", throwable.getMessage());
                    }
                });
    }


    private void addDeposit() {
        Request_addDeposit request_addDeposit = new Request_addDeposit();
        request_addDeposit.setToken(token);
        request_addDeposit.setAmount(0);
        mRESTApi.addDeposit(request_addDeposit).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                if(response.isSuccessful()) {
                    if (response.body().getStatus() == 200) {
                        rotateLayout(layoutRoulette, num_roulette);
                    }
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable throwable) { }
        });
    }
}