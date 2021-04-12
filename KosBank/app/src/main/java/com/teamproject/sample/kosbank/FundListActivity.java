package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.FundingVO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FundListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fund);
        Intent intent =getIntent();
        String category=intent.getStringExtra("f_category");


        InnerTask task = new InnerTask();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("f_category",category);
        task.execute(map);

    }



    public class InnerTask extends AsyncTask<Map, Integer, String>{

        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "FundProducts"); //스프링 url
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
            Log.d("JSON_RESULT", s);
            if(s.length()>0){

                Gson gson = new Gson();
                FundingVO[] list = gson.fromJson(s,  FundingVO[].class);
                List<FundingVO> array = Arrays.asList(list);

                // 2. 어댑터 생성,,, 데이터를 가져오기 위함
                FundAdapter adapter = new FundAdapter(array);  // WeatherAdapter 매개변수 생성자를 먼저 만들고 생성

                // 3. 뷰와 어댑터 연결
                ListView listView = findViewById(R.id.list_Fund);
                listView.setAdapter(adapter);
            }

        }
    }
}
