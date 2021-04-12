package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.MemberVO;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtId, edtPw, edtName, edtRRNf, edtRRNl, edtAddress, edtJob, edtPhone, edtEmail;
    private Button btnOCR, btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnOCR = (Button) findViewById(R.id.btn_OCR);
        btnSignUp = (Button) findViewById(R.id.btn_SignUp);

        edtId = (EditText) findViewById(R.id.SignId);
        edtPw = (EditText) findViewById(R.id.SignPw);
        edtName = (EditText) findViewById(R.id.SignName);
        edtRRNf = (EditText) findViewById(R.id.SignRRN_first);
        edtRRNl = (EditText) findViewById(R.id.SignRRN_last);
        edtAddress = (EditText) findViewById(R.id.SignAddress);
        edtJob = (EditText) findViewById(R.id.SignJob);
        edtPhone = (EditText) findViewById(R.id.SignPhone);
        edtEmail = (EditText) findViewById(R.id.SignEmail);

        //OCR 기능으로 [전송]버튼 눌러서 값을 불러올 때
        Intent intent= getIntent();
        String Name = intent.getStringExtra("Name");
        String RRNf = intent.getStringExtra("RRNf");
        String RRNl = intent.getStringExtra("RRNl");
        String Address = intent.getStringExtra("Address");

        // 불러온 값을 findView해둔 변수에 set 한다.
        edtName.setText(Name);
        edtRRNf.setText(RRNf);
        edtRRNl.setText(RRNl);
        edtAddress.setText(Address);

        btnOCR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OCR_Activity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InnerTask task = new InnerTask();
                Map<String,String> map = new HashMap<>();
                map.put("id", edtId.getText().toString());
                map.put("pw", edtPw.getText().toString());
                map.put("name", edtName.getText().toString());
                map.put("rrnf", edtRRNf.getText().toString());
                map.put("rrnl", edtRRNl.getText().toString());
                map.put("address", edtAddress.getText().toString());
                map.put("job", edtJob.getText().toString());
                map.put("phone", edtPhone.getText().toString());
                map.put("email", edtEmail.getText().toString());
                task.execute(map);
            }
        });

    }

    public class InnerTask extends AsyncTask<Map, Integer, String>{
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "androidSignUp"); //스프링 url
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
            super.onPostExecute(s);
            Log.d("Result_insert", s);

            if(s.length() > 0){
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }


        }
    }

}
