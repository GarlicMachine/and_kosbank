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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.Deposit_productVO;
import com.teamproject.sample.kosbank.VO.MemberVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    List<Deposit_productVO> list ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        list=new ArrayList<Deposit_productVO>();

        InnerTask task = new InnerTask();
        task.execute();
    }


    public class InnerTask extends AsyncTask<Map, Integer, String> {

        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "DepositList"); //스프링 url
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
                Deposit_productVO[] m = gson.fromJson(s.toString(), Deposit_productVO[].class);
                list = Arrays.asList(m);

                // 2. 어댑터 생성 .. 데이터를 가져오기 위함
                DepositAdapter adapter = new DepositAdapter(list);  // WeatherAdapter 매개변수 생성자를 먼저 만들 만들고 생성

                // 3. 뷰와 어댑터를 연결
                ListView listview = findViewById(R.id.loan_list);
                listview.setAdapter(adapter);
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ProductActivity.this, DepositDetailActivity.class);
                        intent.putExtra("y_name", list.get(position).getY_name());
                        intent.putExtra("y_summary", list.get(position).getY_summary());
                        intent.putExtra("y_interest_rate", list.get(position).getY_interest_rate());
                        intent.putExtra("y_type", list.get(position).getY_type());
                        intent.putExtra("y_max_date", list.get(position).getY_max_date());
                        intent.putExtra("y_min_date", list.get(position).getY_min_date());
                        intent.putExtra("y_min_price", list.get(position).getY_min_price());
                        intent.putExtra("y_explanation", list.get(position).getY_explanation());
                        intent.putExtra("y_notice", list.get(position).getY_notice());
                        startActivity(intent);
                    }
                });

            }
        }
    }
}
