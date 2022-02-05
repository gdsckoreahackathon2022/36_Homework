package com.gdsc.homework;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.gdsc.homework.model.BasicResponse;
import com.gdsc.homework.model.Response_checkRoom;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 10;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private RESTApi mRESTApi;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        account = GoogleSignIn.getLastSignedInAccount(this);
        preferences = getSharedPreferences("data", MODE_PRIVATE);
        editor= preferences.edit();
        mRESTApi = RESTApi.retrofit.create(RESTApi.class);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        if (isTokenOnSP()) {
            Log.d("LoginActivity_sequence", "isTokenOnSP");
            checkLoggedIn();
        } else {
            Log.d("LoginActivity_sequence", "not isTokenOnSP");
            updateUI(account);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            Log.d("LoginActivity", "idToken = " + idToken);
            // todo : idtoken 백으로 전달

            if(completedTask.isSuccessful()) {
                GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
                if (acct != null) {
                    String personName = acct.getDisplayName();
                    String personGivenName = acct.getGivenName();
                    String personFamilyName = acct.getFamilyName();
                    String personEmail = acct.getEmail();
                    String personId = acct.getId();
                    Uri personPhoto = acct.getPhotoUrl();
                }
            }

            updateUI(account);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account == null) {
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        } else {
            doRetrofit(account.getIdToken());
        }
    }

    private void doRetrofit(String idToken) {
        mRESTApi.googleLogin(idToken)
                .enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                        if (response.isSuccessful() && response.body().getStatus() == 200) {
                            token = response.body().getData();
                            editor.putString("token",token);
                            editor.commit();

                            Log.d("LoginActivity_sequence", "doRetrofit");
                            checkLoggedIn();
                        }
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable throwable) {
                        Log.d("LoginActivity", throwable.getMessage());
                    }
                });
    }

    private boolean isTokenOnSP() {
        token = preferences.getString("token","");
        if (token.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    private void checkLoggedIn() {
        mRESTApi.checkLoggedIn(token)
                .enqueue(new Callback<BasicResponse>() {
                    @Override
                    public void onResponse(Call<BasicResponse> call, Response<BasicResponse> response) {

                        if (!response.isSuccessful()) {
                            return;
                        }
                        else if (response.isSuccessful() && response.body().getStatus() == 200) {
                            long user_idx = Long.parseLong(response.body().getData());
                            editor.putLong("user_idx",user_idx);
                            editor.commit();

                            Log.d("LoginActivity_sequence", "checkLoggedIn");
                            checkRoom();
                        }
                        else if (response.body().getStatus() == 401) { // 로그인 만료
                            // googlelogin
                        }
                        else if (response.body().getStatus() == 500) {
                            Toast.makeText(LoginActivity.this, "서버가 불안정합니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BasicResponse> call, Throwable throwable) {
                        Log.d("LoginActivity", throwable.getMessage());
                    }
                });
    }

    private void checkRoom() {
        mRESTApi.checkRoom(token)
                .enqueue(new Callback<Response_checkRoom>() {
                    @Override
                    public void onResponse(Call<Response_checkRoom> call, Response<Response_checkRoom> response) {

                        if (response.isSuccessful() && response.body().getStatus() == 200 && response.body().getData().isResult()) {
                            long roomId = response.body().getData().getRoomId();
                            editor.putLong("roomId",roomId);
                            editor.commit();

                            Log.d("LoginActivity_sequence", "checkRoom_tomain" + roomId);

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else if (response.body().getStatus() == 200 && !response.body().getData().isResult()) {
                            // 방 만들 수 있게
                            Log.d("LoginActivity_sequence", "checkRoom_tointro");
                            Intent intent = new Intent(LoginActivity.this, IntroActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        }
                        else if (response.body().getStatus() == 401) {
                            Toast.makeText(LoginActivity.this, "로그인이 만료되었습니다", Toast.LENGTH_SHORT).show();
                        }
                        else if (response.body().getStatus() == 500) {
                            Toast.makeText(LoginActivity.this, "서버가 불안정합니다", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response_checkRoom> call, Throwable throwable) {
                        Log.d("LoginActivity", throwable.getMessage());
                    }
                });
    }
}