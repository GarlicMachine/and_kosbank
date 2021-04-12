package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.MemberVO;
import com.teamproject.sample.kosbank.test.memberId;


import java.util.HashMap;
import java.util.Map;

public class AccountAddActivity extends AppCompatActivity {
    private EditText edtName,edtPw;
    private Button btnAccountAdd, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accountadd);
        btnAccountAdd = (Button) findViewById(R.id.btn_accountadd);
        btnReset = (Button) findViewById(R.id.btn_reset);
        edtPw = (EditText) findViewById(R.id.accountPW);

        // 로고 삽입
        ImageView logo = (ImageView) findViewById(R.id.logo1) ;
        logo.setImageResource(R.drawable.logo) ;

        //인텐트로 넘겨온 값을 받아온다.
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        Map<String,String> map = new HashMap<>();
        map.put("id", id);
        InnerTask task = new InnerTask();
        task.link="androidAccountAddPage";
        task.execute(map);

        btnAccountAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트로 넘겨온 값을 받아온다.
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");

                InnerTask task = new InnerTask();
                Map<String,String> map = new HashMap<>();
                map.put("id",  memberId.id);
                map.put("accountPW", edtPw.getText().toString());
                task.link="androidAccountAdd";
                task.execute(map);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //인텐트로 넘겨온 값을 받아온다.
                Intent intent = getIntent();
                String id = intent.getStringExtra("id");
                intent = new Intent(AccountAddActivity.this, MainActivity.class);
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
            if(link.equals("androidAccountAddPage")){
                if(s.length() > 0){
                    Gson gson = new Gson();
                    MemberVO m = gson.fromJson(s, MemberVO.class);

                    TextView name = (TextView)findViewById(R.id.accountName);
                    name.setText(m.getName());
                }
            }else{
                if(s.length() > 0){
                    Toast.makeText(getApplicationContext(), "계좌개설에 성공했습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AccountAddActivity.this, MainActivity.class);
                    intent.putExtra("id", memberId.id);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "계좌개설에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

}