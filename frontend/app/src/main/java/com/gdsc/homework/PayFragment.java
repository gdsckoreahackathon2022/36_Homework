package com.gdsc.homework;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gdsc.homework.fragment.BottomFrag4;
import com.gdsc.homework.model.Approve;
import com.gdsc.homework.model.Ready;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayFragment extends Fragment {

    WebView webView;
    MyWebViewClient myWebViewClient;
    String tidPin; //결제 고유 번호
    String pgToken; //결제 요청 토큰

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private String storeName;
    private String productName;
    private Integer productPrice;

    public static PayFragment newInstance(String storeName, String productName, Integer productPrice) {
        PayFragment fragment = new PayFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, storeName);
        args.putString(ARG_PARAM2, productName);
        args.putInt(ARG_PARAM3, productPrice);
        fragment.setArguments(args);
        return fragment;
    }

    public PayFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeName = getArguments().getString(ARG_PARAM1);
            productName = getArguments().getString(ARG_PARAM2);
            productPrice = getArguments().getInt(ARG_PARAM3);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay, container, false);

        webView = view.findViewById(R.id.pay_webView);
        webView.getSettings().setJavaScriptEnabled(true);

        myWebViewClient = new MyWebViewClient();
        webView.setWebViewClient(myWebViewClient);

        myWebViewClient.readyAndCallPay();
        return view;
    }

    public class MyWebViewClient extends WebViewClient {

        KakaoPayApi mRESTApi = KakaoPayApi.retrofit.create(KakaoPayApi.class);

        public void readyAndCallPay() {
            String cid = "TC0ONETIME";
            String partner_order_id = "1001";
            String partner_user_id = "gorany";
            String item_name = productName;
            Integer quantity = 1;
            Integer total_amount = productPrice;
            Integer tax_free_amount = 0;
            String approval_url = "https://developers.kakao.com";
            String cancel_url = "https://naver.com";
            String fail_url = "https://google.com";

            mRESTApi.paymentReady(cid, partner_order_id, partner_user_id, item_name, quantity,
                    total_amount, tax_free_amount, approval_url, cancel_url, fail_url).enqueue(new Callback<Ready>() {
                @Override
                public void onResponse(Call<Ready> call, Response<Ready> response) {
                    Ready ready = (Ready) response.body();

                    if (response.isSuccessful() == true && ready != null) {
                        String url = ready.getNext_redirect_mobile_url();
                        String tid = ready.getTid();

                        webView.loadUrl(url);
                        tidPin = tid;
                    }
                }

                @Override
                public void onFailure(Call<Ready> call, Throwable throwable) {
                    Log.e("Debug", "Error: " + throwable.getMessage());
                }
            });
        }

        public void approvePayment() {
            String cid = "TC0ONETIME";
            String tid = tidPin;
            String partner_order_id = "1001";
            String partner_user_id = "gorany";
            String pg_token = pgToken;
            Integer total_amount = productPrice;

            mRESTApi.paymentApprove(cid, tid, partner_order_id, partner_user_id, pg_token, total_amount).enqueue(new Callback<Approve>() {
                @Override
                public void onResponse(Call<Approve> call, Response<Approve> response) {
                    if(response.isSuccessful() == true) {
                        Log.d("Debug", "payment approve success");
                    }
                }

                @Override
                public void onFailure(Call<Approve> call, Throwable throwable) {
                    Log.e("Debug", "Error: " + throwable.getMessage());
                }
            });
        }

        // URL 변경시 발생 이벤트
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("Debug", "change url: " + url);

            if (url != null && url.contains("pg_token=")) {
                String pg_Token = url.substring(url.indexOf("pg_token=") + 9);
                pgToken = pg_Token;

                this.approvePayment();

//                // MainActivity.class를 가고자 하는 엑티비티로 변경 필요
//                OrderSuccessActivity orderSuccessActivity = new OrderSuccessActivity(storeName, productName);
//                Intent intentorder = new Intent(PayActivity.this, orderSuccessActivity.getClass());
//                startActivity(intentorder);
//                finish();   // 다시 돌아오지 않을 것이므로 PayActivity finish
                ((MainActivity)getActivity()).replaceBottomTab(BottomFrag4.newInstance(true));
                ((MainActivity)getActivity()).setVisibilityBottomNavigation(true);
//                Intent intent = new Intent(getApplicationContext(), OrderSuccessActivity.class);
//                intent.putExtra("store", storeName);
//                intent.putExtra("menu", productName);
//                startActivity(intent);
//                finish();
            } else if (url != null && url.startsWith("intent://")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    startActivity(intent);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // 다른 엑티비티로 intent 전달해줄 것이므로 의미 없는 구문
//            view.loadUrl(url);
            return false;
        }
    }
}