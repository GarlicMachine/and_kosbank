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

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.accountVO;
import com.teamproject.sample.kosbank.test.fmt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.LinearLayout;
import android.widget.Toast;

/*import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;*/
import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.MemberVO;
import com.teamproject.sample.kosbank.test.memberId;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private View drawerMenu, btn_SignUpPage, lay_MyAllAccount;
    private Button Btn_Logout;
    private ImageButton btn_menu;
    private TextView text_Account, text_Balance;
    private  String mainAccount;
    private View drawerView;
    private View btn_Account_Add;
    private View btn_accountDelete;
    private View account_TransferList;
    private  String id ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((ImageButton)findViewById(R.id.btn_menu)).setImageResource(R.drawable.menuimg);

        //SignInActivity ??????????????? ???????????? ????????? id?????? ????????????.
        Log.e("Token", FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("allDevices");

        //???????????? ????????? ?????? ????????????.
        Intent intent = getIntent();
        text_Account = (TextView)findViewById(R.id.text_Account);
        text_Balance = (TextView)findViewById(R.id.text_Balance);

        id= memberId.id;
        drawerLayout = (DrawerLayout)findViewById(R.id.Drawer_Main);
        drawerMenu = (View)findViewById(R.id.Drawer_Menu);
        btn_menu = (ImageButton)findViewById(R.id.btn_menu);
        lay_MyAllAccount = (LinearLayout)findViewById(R.id.lay_MyAllAccount);

        //??? ???????????? ???????????? ?????? ??? ???????????????
        Button btn_AccTranView = (Button)findViewById(R.id.btn_AccTranView);
        btn_AccTranView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TransferDetail_Activity.class);
                intent.putExtra("account", mainAccount);
                startActivity(intent);
            }
        });


        //View drawerMenu = (View)findViewById(R.id.Drawer_Menu);
        //ImageButton btn_menu = (ImageButton)findViewById(R.id.btn_menu);
        //?????? ???????????? ?????? ???
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

        //???????????? ?????? ???
        btn_Account_Add = (LinearLayout)findViewById(R.id.btn_Account_Add);
        btn_Account_Add.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getApplicationContext(), AccountAddActivity.class);
               intent.putExtra("id", memberId.id);
               startActivity(intent);

           }
       });

        // ???????????? ?????? ???
        btn_accountDelete = (LinearLayout)findViewById(R.id.btn_accountDelete);
        btn_accountDelete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this, AccountDeleteActivity.class);
               intent.putExtra("id", memberId.id);
               startActivity(intent);
           }
        });

        // ???????????? ?????? ???
        account_TransferList = (LinearLayout)findViewById(R.id.btn_accounttransfer);
        account_TransferList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountTransferActivity.class);
                intent.putExtra("id", memberId.id);
                startActivity(intent);
            }
        });

        //???????????? ?????? ?????? ???
        Btn_Logout = (Button)findViewById(R.id.Btn_Logout);
        Btn_Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
       //????????????
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

        // ????????????????????? ?????? ?????? ?????? ??????????????? ?????????.
        //InnerTask??? ?????? ?????? ?????? ?????????.
        MainActivity.InnerTask task = new MainActivity.InnerTask();
        Map<String,String> map = new HashMap<>();
        map.put("id", id); // SignInActivity ?????? ????????? id
        // map.put("id", edtId.getText().toString());
        task.link="getAccountInfo_Main";
        task.execute(map);

        // ????????????????????? ????????? ???????????? ?????????.
        //InnerTask2??? ?????? ?????? ?????? ?????????.
        MainActivity.InnerTask2 task2 = new MainActivity.InnerTask2();
        Map<String,String> map2 = new HashMap<>();
        map2.put("id", id); // SignInActivity ?????? ????????? id
        // map.put("id", edtId.getText().toString());
        task2.link="my_Account_List";
        task2.execute(map);


    }
    // ?????? ?????? ?????? ??????????????? ???????????? ?????????
    public class InnerTask extends AsyncTask<Map, Integer, String> {

        public String link = "";
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP ?????? ??????
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + link); //????????? url
            //???????????? ??????
            http.addAllParameters(maps[0]);

            //HTTP ?????? ??????
            HttpClient post = http.create();
            post.request();

            // ????????????????????? ????????????
            String body = post.getBody(); //Web??? Controller?????? ????????? ???
            Log.d("body------", body);
            return body;
        }



        @Override
        protected void onPostExecute(String s) {
            //JSON?????? ?????? ???????????? VO Obejct??? ????????????.
            if(link.equals("getAccountInfo_Main")){
                if(s.length() > 0) {
                    Gson gson = new Gson();
                    accountVO m = gson.fromJson(s, accountVO.class);
                    if (m.getAccount() != null) {
                        String account = m.getAccount();
                        int balance = m.getBalance();
                        mainAccount = m.getAccount();
                        text_Account.setText(account);
                        text_Balance.setText(fmt.moneyFormatToWon(balance));

                    } else if (m.getAccount() == null) {
                        Toast.makeText(getApplicationContext(), "????????? ????????? ???????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "????????? ????????? ???????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    // ??? ?????????????????? ???????????? ?????????
    public class InnerTask2 extends AsyncTask<Map, Integer, String> {

        public String link = "";
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP ?????? ??????
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + link); //????????? url
            //???????????? ??????
            http.addAllParameters(maps[0]);

            //HTTP ?????? ??????
            HttpClient post = http.create();
            post.request();

            // ????????????????????? ????????????
            String body = post.getBody(); //Web??? Controller?????? ????????? ???
            Log.d("body------", body);
            return body;
        }

        @Override
        protected void onPostExecute(String s) {
            //JSON?????? ?????? ???????????? VO Obejct??? ????????????.
            if(link.equals("my_Account_List")){
                if(s.length() > 0) {
                   Gson gson = new Gson();
                    accountVO[] array = gson.fromJson(s, accountVO[].class);
                    List<accountVO> myAccList = Arrays.asList(array);

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
