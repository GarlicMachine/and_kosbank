package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.MemberVO;
import com.teamproject.sample.kosbank.test.memberId;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private View drawerView;
    private View btn_Account_Add;
    private View btn_accountDelete;
    private View account_TransferList;
    private  String id ;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //인텐트로 넘겨온 값을 받아온다.
        Intent intent = getIntent();
        //id = intent.getStringExtra("id");
        //Log.d("id",id);
        id= memberId.id;
        drawerLayout = (DrawerLayout)findViewById(R.id.Drawer_Main);
        drawerView = (View)findViewById(R.id.Drawer_Menu);

        Button btn_menu =(Button)findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerView);
            }
        });

        //계좌개설 클릭 시
        btn_Account_Add = (LinearLayout)findViewById(R.id.btn_Account_Add);
        btn_Account_Add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), AccountAddActivity.class);
               intent.putExtra("id", memberId.id);
               startActivity(intent);

           }
       });

        // 계좌해지 클릭 시
        btn_accountDelete = (LinearLayout)findViewById(R.id.btn_accountDelete);
        btn_accountDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, AccountDeleteActivity.class);
               intent.putExtra("id", memberId.id);
               startActivity(intent);
           }
        });

        // 계좌이체 클릭 시
        account_TransferList = (LinearLayout)findViewById(R.id.btn_accounttransfer);
        account_TransferList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountTransferActivity.class);
                intent.putExtra("id", memberId.id);
                startActivity(intent);
            }
        });



        Button btn_exit = (Button)findViewById(R.id.Btn_MenuExit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }


    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {        }
        @Override
        public void onDrawerStateChanged(int newState) {        }
    };

    public class InnerTask extends AsyncTask<Map, Integer, String> {
        public String url = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + url); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);

            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 안드로이드에서 응답받음
            String body = post.getBody(); //Web의 Controller에서리턴한 값
            Log.d("body------", body);
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON_RESULT", s);
            Gson gson = new Gson();
            String m = gson.fromJson(s,String.class);
        }
    }


}
