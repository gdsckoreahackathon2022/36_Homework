package com.gdsc.homework;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import com.gdsc.homework.model.BasicResponse;
import com.gdsc.homework.model.Request_addDeposit;
import com.gdsc.homework.model.Request_createHousework;
import com.gdsc.homework.model.Request_getDeposit;
import com.gdsc.homework.model.Request_participateRoom;
import com.gdsc.homework.model.Request_rouletteResult;
import com.gdsc.homework.model.Response_checkRoom;
import com.gdsc.homework.model.Response_createRoom;
import com.gdsc.homework.model.Response_getMyHousework;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public interface RESTApi {
    @Headers(value = "Content-Type: application/json")

    // auth
    @POST("api/auth/google/login")
    Call<BasicResponse> googleLogin(
            @Body String idToken);

    @POST("api/auth/check")
    Call<BasicResponse> checkLoggedIn(
            @Body String token);

    // room
    @POST("api/room/create")
    Call<Response_createRoom> createRoom(
            @Body String token);

    @POST("api/room/participate")
    Call<BasicResponse> participateRoom(
            @Body Request_participateRoom request_participateRoom);

    @POST("api/room/check")
    Call<Response_checkRoom> checkRoom(
            @Body String token);

    // bank
    @POST("api/bank/deposit")
    Call<BasicResponse> getDeposit(
            @Body Request_getDeposit request_getDeposit);

    @POST("api/bank/deposit/add")
    Call<BasicResponse> addDeposit(
            @Body Request_addDeposit request_addDeposit);

    // housework
    @POST("api/housework/my")
    Call<Response_getMyHousework> getMyHousework(
            @Body String token);

    @POST("api/housework/create")
    Call<BasicResponse> createHousework(
            @Body Request_createHousework request_createHousework);

    // roulette
    @POST("api/roulette/result")
    Call<BasicResponse> rouletteResult(
            @Body Request_rouletteResult request_rouletteResult);


    Gson gson = new GsonBuilder().setLenient().create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://ec2-3-38-185-14.ap-northeast-2.compute.amazonaws.com:8081/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
}
