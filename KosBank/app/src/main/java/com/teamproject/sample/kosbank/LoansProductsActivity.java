package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.Loans_productVO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoansProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loansproducts);

        InnerTask task = new InnerTask();
        task.execute();
    }

    private class InnerTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Object[] objects) {
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "LoansProducts");

            HttpClient post = http.create();
            post.request();

            String body = post.getBody();
            return body;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("JSON_RESULT", (String) o);
            if(((String) o).length()>0){
                Gson gson = new Gson();
                Loans_productVO[] list = gson.fromJson(o.toString(),  Loans_productVO[].class);
                List<Loans_productVO> array = Arrays.asList(list);

                // 2. 어댑터 생성,,, 데이터를 가져오기 위함
                LoansAdapter adapter = new LoansAdapter(array);  // WeatherAdapter 매개변수 생성자를 먼저 만들고 생성

                // 3. 뷰와 어댑터 연결
                ListView listView = findViewById(R.id.loan_list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(LoansProductsActivity.this, LoansDetailActivity.class);
                        intent.putExtra("d_name",array.get(position).getD_name());
                        startActivity(intent);
                    }
                });
            }

        }
    }
}
