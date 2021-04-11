package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.Savings_productVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SavingsActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    List<Savings_productVO> list ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savingslist);
        list=new ArrayList<Savings_productVO>();
        InnerTask task = new InnerTask();
        task.execute();
    }


    public class InnerTask extends AsyncTask<Map, Integer, String> {

        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "SavingsList"); //스프링 url
            //파라미터 전송

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
            Log.d("json------", s);

            if(s.length()>0) {
                Gson gson = new Gson();
                Savings_productVO[] m = gson.fromJson(s.toString(), Savings_productVO[].class);
                list = Arrays.asList(m);
                
                // 2. 어댑터 생성 .. 데이터를 가져오기 위함
                SavingsAdapter adapter = new SavingsAdapter(list);  // WeatherAdapter 매개변수 생성자를 먼저 만들 만들고 생성

                // 3. 뷰와 어댑터를 연결
                ListView listview = findViewById(R.id.savings_list);
                listview.setAdapter(adapter);


                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(SavingsActivity.this, SavingsDetailActivity.class);
                        intent.putExtra("j_name", list.get(position).getJ_name());
                        intent.putExtra("j_summary", list.get(position).getJ_summary());
                        intent.putExtra("j_interest_rate", list.get(position).getJ_interest_rate());
                        intent.putExtra("j_type", list.get(position).getJ_type());
                        intent.putExtra("j_max_date", list.get(position).getJ_max_date());
                        intent.putExtra("j_min_date", list.get(position).getJ_min_date());
                        intent.putExtra("j_explanation", list.get(position).getJ_explanation());
                        intent.putExtra("j_notice", list.get(position).getJ_notice());
                        startActivity(intent);
                    }
                });

            }
        }
    }
}
