package com.gdsc.homework;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gdsc.homework.model.BasicResponse;
import com.gdsc.homework.model.Request_participateRoom;
import com.gdsc.homework.model.Response_createRoom;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_createroom, tv_joinroom;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private RESTApi mRESTApi;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        preferences = getSharedPreferences("data", MODE_PRIVATE);
        editor= preferences.edit();
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        token = preferences.getString("token","");

        tv_createroom = findViewById(R.id.tv_createroom);
        tv_joinroom = findViewById(R.id.tv_joinroom);
        tv_createroom.setOnClickListener(this);
        tv_joinroom.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_createroom:
                createRoom();
                break;
            case R.id.tv_joinroom:
                dialog_roomcode(view);
                break;
        }
    }

    private void createRoom() {
        mRESTApi.createRoom(token)
                .enqueue(new Callback<Response_createRoom>() {
                    @Override
                    public void onResponse(Call<Response_createRoom> call, Response<Response_createRoom> response) {

                        if (response.body().getStatus() == 200) {
                            String roomCode = response.body().getData().getRoomCode();
                            editor.putString("roomCode",roomCode);
                            editor.commit();

                            Log.d("IntroActivity_sequence", "createRoom" + roomCode);

                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response_createRoom> call, Throwable throwable) {
                        Log.d("LoginActivity", throwable.getMessage());
                    }
                });
    }

    private void participateRoom(String roomcode) {
        Request_participateRoom request_participateRoom = new Request_participateRoom();
        request_participateRoom.setToken(token);
        request_participateRoom.setRoomCode(roomcode);

        mRESTApi.participateRoom(request_participateRoom)
                .enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                        if (response.body().getStatus() == 200) {
                            long roomId = Long.parseLong(response.body().getData());
                            editor.putLong("roomId",roomId);
                            editor.commit();

                            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable throwable) {
                        Log.d("LoginActivity", throwable.getMessage());
                    }
                });
    }

    public void dialog_roomcode(View v) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_roomcode, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        EditText et_roomcode = dialogView.findViewById(R.id.et_roomcode);
        Button ok_btn = dialogView.findViewById(R.id.btn_okay);
        ok_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                participateRoom(et_roomcode.getText().toString());
                alertDialog.dismiss();
            }
        });
    }
}