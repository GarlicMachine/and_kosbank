package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.MemberVO;
import com.teamproject.sample.kosbank.VO.TransferVO;
import com.teamproject.sample.kosbank.VO.accountVO;
import com.teamproject.sample.kosbank.test.memberId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountTransferActivity  extends AppCompatActivity {
    private Spinner account_TransferList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    private Button btn_reset2, btn_AccountNameChk, btn_accountTransfer;
    private  String selectAccount;
    private EditText transferAccountPW;
    private TextView Transfer_balance, Transfer_limit, TransferMoney, out_comment, in_comment, sender_account, sender_name;
    List<accountVO> list1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounttransfer);

        btn_reset2 = (Button) findViewById(R.id.btn_reset2);
        btn_AccountNameChk = (Button) findViewById((R.id.btn_AccountNameChk));
        TransferMoney = (EditText) findViewById(R.id.TransferMoney);
        transferAccountPW = (EditText) findViewById(R.id.transferAccountPW);
        Transfer_balance = (TextView)findViewById(R.id.Transfer_balance);
        Transfer_limit = (TextView)findViewById(R.id.Transfer_limit);
        out_comment = (TextView)findViewById(R.id.out_comment);
        in_comment = (TextView)findViewById(R.id.in_comment);
        sender_account = (TextView) findViewById(R.id.sender_account);
        btn_accountTransfer = (Button) findViewById(R.id.btn_accountTransfer);
        sender_name = (TextView) findViewById(R.id.sender_name);

        //인텐트로 넘겨온 값을 받아온다.
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        InnerTask task = new InnerTask();
        Map<String,String> map = new HashMap<>();
        map.put("id",  memberId.id);
        map.put("account",  selectAccount);


        task.link="androidAccountTransfer";
        task.execute(map);


        btn_reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트로 넘겨온 값을 받아온다.
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                intent = new Intent(AccountTransferActivity.this, MainActivity.class);
                intent.putExtra("id", memberId.id);
                startActivity(intent);
            }
        });

        btn_AccountNameChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InnerTask task = new InnerTask();
                //인텐트로 넘겨온 값을 받아온다.
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                intent.putExtra("id", memberId.id);
                Map<String,String> map = new HashMap<>();
                map.put("id", id);
                map.put("sender_account",  sender_account.getText().toString());

                task.link="androidAccountNameCnk";
                task.execute(map);
            }
        });

        btn_accountTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InnerTask task = new InnerTask();
                //인텐트로 넘겨온 값을 받아온다.
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                intent.putExtra("id", memberId.id);
                Map<String,String> map = new HashMap<>();
                map.put("id", id);
                map.put("account",  selectAccount);
                map.put("accountPW", transferAccountPW.getText().toString());
                map.put("money", TransferMoney.getText().toString());
                map.put("balance", Transfer_balance.getText().toString());
                map.put("accountLimit", Transfer_limit.getText().toString());
                map.put("out_comment", out_comment.getText().toString());
                map.put("in_comment", in_comment.getText().toString());
                map.put("sender_account", sender_account.getText().toString());
                map.put("sender_name", sender_name.getText().toString());

                task.link="androidAccountTransferAction";
                task.execute(map);
            }
        });
    }

    public class InnerTask extends AsyncTask<Map, Integer, String> {
        public String link="";
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL +link); //스프링 url

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
            super.onPostExecute(s);
            Log.d("Result_insert",s );
            if(link.equals("androidAccountTransfer")){
                if(s.length() > 0) {
                    Gson gson = new Gson();
                    accountVO[] list = gson.fromJson(s, accountVO[].class);
                    list1 = Arrays.asList(list);
                    List<String> list2 = new ArrayList<String>();

                    for(int z=0;z<list1.size();z++){
                        list2.add(list1.get(z).getAccount());
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter<>(AccountTransferActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, list2);

                    account_TransferList = (Spinner)findViewById(R.id.account_TransferList);
                    account_TransferList.setPrompt("계좌번호를 선택하세요");
                    account_TransferList.setAdapter(arrayAdapter);

                    account_TransferList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(),list1.get(i).getAccount()+"가 선택되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                            selectAccount=list1.get(i).getAccount();
                            TextView Transfer_balance = (TextView)findViewById(R.id.Transfer_balance);
                            Transfer_balance.setText(Integer.toString(list1.get(i).getBalance()));
                            TextView Transfer_limit = (TextView)findViewById(R.id.Transfer_limit);
                            Transfer_limit.setText(Integer.toString(list1.get(i).getAccountLimit()));
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }

            } else if(link.equals("androidAccountNameCnk")) {
                if(s.length() > 0) {
                    Gson gson = new Gson();
                    TransferVO t = gson.fromJson(s, TransferVO.class);

                    TextView sender_name = (TextView) findViewById(R.id.sender_name);
                    sender_name.setText(t.getSender_name());
                }
            }else {
                Log.d("aaaaaaaaaa", s);
                if (s.length() > 0) {
                    Toast.makeText(getApplicationContext(), "계좌이체에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AccountTransferActivity.this, MainActivity.class);
                    intent.putExtra("id", memberId.id);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "계좌이체에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}
