package com.gdsc.homework;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gdsc.homework.model.BasicResponse;
import com.gdsc.homework.model.Request_addDeposit;
import com.gdsc.homework.model.Request_createHousework;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditActivity extends AppCompatActivity implements View.OnClickListener {

    private String cate, name, day;
    private Button btn_addhomework;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private RESTApi mRESTApi;
    private String token;

    private ImageView iv_goback;

    private Request_createHousework request_createHousework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // 카테고리: 싱글 스피너
        // 담당자: 멀티 스피너
        // 요일: 멀티 스피너
        // 리스너: 카테고리는 문자열 하나 보내기, 담당자 & 요일은 배열 보내기
        int[] spinId = { R.id.spinner_category, R.id.spinner_person, R.id.spinner_day };
        int[] arrayResId = { R.array.categories, R.array.people, R.array.week };
        int[] layoutId = { R.layout.spinner_single_item,
                R.layout.spinner_single_item, R.layout.spinner_single_item};


        preferences = getSharedPreferences("data", MODE_PRIVATE);
        editor= preferences.edit();
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        token = preferences.getString("token","");
        request_createHousework = new Request_createHousework();

        iv_goback = findViewById(R.id.iv_goback);
        iv_goback.setOnClickListener(this);

        btn_addhomework = findViewById(R.id.btn_addhomework);
        btn_addhomework.setOnClickListener(this);

        Spinner[] spinners = new Spinner[3];

        for (int i = 0; i < 3; i++) {
            spinners[i] = initSpinnerItems(spinId[i], arrayResId[i], layoutId[i]);
        }

        // 카테고리 스피너
        spinners[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                cate = spinners[0].getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // 담당자 스피너
        spinners[1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                name = spinners[0].getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // 요일 스피너
        spinners[2].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                day = spinners[2].getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private Spinner initSpinnerItems(int spinId, int arrayResId, int layoutId) {
        Spinner spinner = findViewById(spinId);
        String[] array = getResources().getStringArray(arrayResId);

        ArrayAdapter<String> adapter;

        if(layoutId == R.layout.spinner_single_item){
               adapter = new ArrayAdapter<String>(this, layoutId, array) {
                   @Override
                   public boolean isEnabled(int position) {
                       if (position == 0) {
                           // Disable the first item from Spinner
                           // First item will be use for hint
                           return false;
                       } else {
                           return true;
                       }
                   }

                   @Override
                   public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                       View view = super.getDropDownView(position, convertView, parent);
                       TextView tv = (TextView) view;
                       if (position == 0) {
                           // Set the hint text color gray
                           tv.setTextColor(Color.GRAY);
                       } else {
                           tv.setTextColor(Color.BLACK);
                       }
                       return view;
                   }};
           } else{
                // 스피너의 텍스트뷰에 대한 리소스 아이디를 어댑터에게 전달해줘야
                // 어댑터가 배열의 내용을 텍스트뷰에 적용 가능!
                adapter = new ArrayAdapter<String>(this, layoutId, R.id.tv_spinner, array){
                    @Override
                    public boolean isEnabled(int position) {
                        if (position == 0) {
                            // Disable the first item from Spinner
                            // First item will be use for hint
                            return false;
                        } else {
                            return true;
                        }
                    }

                    @Override
                    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View view = super.getDropDownView(position, convertView, parent);
                        TextView tv_spinner = view.findViewById(R.id.tv_spinner);
                        CheckBox checkBox = view.findViewById(R.id.checkbox);

                        if (position == 0) {
                            // Set the hint text color gray
                            tv_spinner.setTextColor(Color.GRAY);
                        } else {
                            tv_spinner.setTextColor(Color.BLACK);
                            checkBox.setVisibility(View.VISIBLE);
                        }
                        return view;
                    }};
            }

        spinner.setAdapter(adapter);

        return spinner;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_addhomework:
                addDeposit();
                break;
            case R.id.iv_goback:
                onBackPressed();
                break;
        }
    }

    private void addDeposit() {

        List<Long> a = new ArrayList<>();
        a.add(Long.parseLong("3"));
        List<String> b = new ArrayList<>();
        b.add(day);

        request_createHousework.setToken(token);
        request_createHousework.setName(name);
        request_createHousework.setUserId(a);
        request_createHousework.setDay(b);
        request_createHousework.setStartTime("09:00");
        request_createHousework.setFinishTime("10:00");
        request_createHousework.setRepeat(true);
        request_createHousework.setPenalty(1000);
        request_createHousework.setMemo("오전에 다 할 것");

        mRESTApi.createHousework(request_createHousework).enqueue(new Callback<BasicResponse>() {
            @Override
            public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {
                if(response.isSuccessful()) {
                    if(response.body().getStatus() == 200) {
                        Intent intent = new Intent(EditActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BasicResponse> call, Throwable throwable) { }
        });
    }
}