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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.test.memberId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountDeleteActivity extends AppCompatActivity {
    private Spinner account_selectList;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    private Button btn_accountdelete, btn_reset1;
    private EditText deleteAccountPW;
    private  String selectAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accountdelete);
        btn_accountdelete = (Button) findViewById(R.id.btn_accountdelete);
        btn_reset1 = (Button) findViewById(R.id.btn_reset1);
        deleteAccountPW = (EditText) findViewById(R.id.deleteAccountPW);

        //인텐트로 넘겨온 값을 받아온다.
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Map<String,String> map = new HashMap<>();
        map.put("id", id);
       InnerTask task = new InnerTask();
        task.link="androidAccountDelete";
        task.execute(map);

        btn_accountdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트로 넘겨온 값을 받아온다.
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                String account = intent.getStringExtra("account");

                InnerTask task = new InnerTask();
                Map<String,String> map = new HashMap<>();
                map.put("id",  memberId.id);
                map.put("account",  selectAccount);
                map.put("accountPW", deleteAccountPW.getText().toString());

                task.link="androidAccountDeleteAction";
                task.execute(map);
            }
        });

        btn_reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트로 넘겨온 값을 받아온다.
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                intent = new Intent(AccountDeleteActivity.this, MainActivity.class);
                intent.putExtra("id", memberId.id);
                startActivity(intent);
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
            if(link.equals("androidAccountDelete")){
                if(s.length() > 0) {
                    Gson gson = new Gson();
                    String[] list = gson.fromJson(s, String[].class);
                    List<String> list1 = Arrays.asList(list);

                    arrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_dropdown_item, list1);

                    account_selectList = (Spinner)findViewById(R.id.account_selectList);
                    account_selectList.setPrompt("계좌번호를 선택하세요");
                    account_selectList.setAdapter(arrayAdapter);

                    account_selectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(),list1.get(i)+"가 선택되었습니다.",
                                    Toast.LENGTH_SHORT).show();
                            selectAccount=list1.get(i);

                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
                        }
                    });
                }

            } else{

                if(s.length() > 0){
                    Toast.makeText(getApplicationContext(), "계좌해지에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AccountDeleteActivity.this, MainActivity.class);
                    intent.putExtra("id", memberId.id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "계좌해지에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }

            }


        }
    }


}
