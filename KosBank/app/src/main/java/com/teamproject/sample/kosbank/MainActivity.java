package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.AccountVO;
import com.teamproject.sample.kosbank.test.fmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerMenu, btn_SignUpPage, lay_MyAllAccount;
    private Button Btn_Logout;
    private ImageButton btn_menu;
    private TextView text_Account, text_Balance;
    private  String mainAccount;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SignInActivity 클래스에서 인텐트로 넘겨온 id값을 받아온다.
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        Log.d("IntentGetString", id);
        //......................................................
        text_Account = (TextView)findViewById(R.id.text_Account);
        text_Balance = (TextView)findViewById(R.id.text_Balance);

        drawerLayout = (DrawerLayout)findViewById(R.id.Drawer_Main);
        drawerMenu = (View)findViewById(R.id.Drawer_Menu);
        btn_menu = (ImageButton)findViewById(R.id.btn_menu);
        lay_MyAllAccount = (LinearLayout)findViewById(R.id.lay_MyAllAccount);

        //내 모든계좌 레이아웃 클릭 시 프레그먼트
        Button btn_AccTranView = (Button)findViewById(R.id.btn_AccTranView);
        btn_AccTranView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransferDetail_Activity.class);
                intent.putExtra("account", mainAccount);
                startActivity(intent);
            }
        });


        //툴바 메뉴버튼 클릭 시
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(drawerMenu);
            }
        });
        drawerLayout.setDrawerListener(listener);
        drawerMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //로그아웃 버튼 클릭 시
        Btn_Logout = (Button)findViewById(R.id.Btn_Logout);
        Btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
       //금융상품
        LinearLayout layout = (LinearLayout)findViewById(R.id.btn_bankitem);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BankItemActivity.class);
                startActivity(intent);
            }
        });
        LinearLayout layout2 = (LinearLayout)findViewById(R.id.btn_funditem);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FundCategoryActivity.class);
                startActivity(intent);
            }
        });

        // 로그인하고나서 돈이 제일 많은 계좌정보를 띄운다.
        //InnerTask에 맵에 담은 값을 넘긴다.
        MainActivity.InnerTask task = new MainActivity.InnerTask();
        Map<String,String> map = new HashMap<>();
        map.put("id", id); // SignInActivity 값을 받아온 id
        // map.put("id", edtId.getText().toString());
        task.link="getAccountInfo_Main";
        task.execute(map);

        // 로그인하고나서 내계좌 리스트를 받는다.
        //InnerTask2에 맵에 담은 값을 넘긴다.
        MainActivity.InnerTask2 task2 = new MainActivity.InnerTask2();
        Map<String,String> map2 = new HashMap<>();
        map2.put("id", id); // SignInActivity 값을 받아온 id
        // map.put("id", edtId.getText().toString());
        task2.link="my_Account_List";
        task2.execute(map);


    }
    // 가장 돈이 많은 계좌정보를 가져오는 클래스
    public class InnerTask extends AsyncTask<Map, Integer, String> {

        public String link = "";
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + link); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);

            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 안드로이드에서 응답받음
            String body = post.getBody(); //Web의 Controller에서 리턴한 값
            Log.d("body------", body);
            return body;
        }



        @Override
        protected void onPostExecute(String s) {
            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(link.equals("getAccountInfo_Main")){
                if(s.length() > 0) {
                    Gson gson = new Gson();
                    AccountVO m = gson.fromJson(s, AccountVO.class);
                    if (m.getAccount() != null) {
                        Toast.makeText(getApplicationContext(), "환영합니다", Toast.LENGTH_SHORT).show();
                        String account = m.getAccount();
                        int balance = m.getBalance();
                        mainAccount = m.getAccount();
                        text_Account.setText(account);
                        text_Balance.setText(fmt.moneyFormatToWon(balance));

                    } else if (m.getAccount() == null) {
                        Toast.makeText(getApplicationContext(), "보유한 계좌가 없습니다 계좌를 개설해주세요", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "보유한 계좌가 없습니다 계좌를 개설해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    // 내 계좌리스트를 가져오는 클래스
    public class InnerTask2 extends AsyncTask<Map, Integer, String> {

        public String link = "";
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + link); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);

            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 안드로이드에서 응답받음
            String body = post.getBody(); //Web의 Controller에서 리턴한 값
            Log.d("body------", body);
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(link.equals("my_Account_List")){
                if(s.length() > 0) {
                   Gson gson = new Gson();
                    AccountVO[] array = gson.fromJson(s, AccountVO[].class);
                    List<AccountVO> myAccList = Arrays.asList(array);

                    Main_myAccountAdapter adapter = new Main_myAccountAdapter(myAccList);

                    ListView list_myAccList = (ListView)findViewById(R.id.list_myAccList);
                    list_myAccList.setAdapter(adapter);

                    list_myAccList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(MainActivity.this, TransferDetail_Activity.class);
                            intent.putExtra("account", myAccList.get(position).getAccount());
                            startActivity(intent);
                        }
                    });
                }
            }
        }
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


}
