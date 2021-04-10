package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.Loans_productVO;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoansDetailActivity extends AppCompatActivity {

    InnerTask task = null;
    String d_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loansdetail);

        Intent intent = getIntent();
        d_name = intent.getStringExtra("d_name");

        task = new InnerTask();
        task.execute(d_name);
    }

    private class InnerTask extends AsyncTask {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "LoansDetail");
            http.addOrReplace("d_name", (String)objects[0]);

            HttpClient post = http.create();
            post.request();

            String body = post.getBody();
            return body;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("JSON_RESULT",  (String) o);
            if (((String) o).length() > 0) {
                Gson gson = new Gson();

                Loans_productVO vo = gson.fromJson(o.toString(), Loans_productVO.class);

                TextView d_name2 = (TextView) findViewById(R.id.d_name2);
                d_name2.setText(vo.getD_name());
                TextView d_summary2 = (TextView) findViewById(R.id.d_summary2);
                d_summary2.setText(vo.getD_summary());
                TextView d_min_date2 = (TextView) findViewById(R.id.d_min_date2);
                d_min_date2.setText(Integer.toString(vo.getD_min_date()));
                TextView d_max_date2 = (TextView) findViewById(R.id.d_max_date2);
                d_max_date2.setText(Integer.toString(vo.getD_max_date()));
                TextView d_interest_rate2 = (TextView) findViewById(R.id.d_interest_rate2);
                d_interest_rate2.setText(Double.toString(vo.getD_interest_rate()));
                TextView d_min_price2 = (TextView) findViewById(R.id.d_min_price2);
                d_min_price2.setText(Integer.toString(vo.getD_min_price()));
                TextView d_max_price2 = (TextView) findViewById(R.id.d_max_price2);
                d_max_price2.setText(Integer.toString(vo.getD_max_price()));
                TextView d_explanation1 = (TextView) findViewById(R.id.d_explanation1);
                d_explanation1.setText(vo.getD_explanation1());
                TextView d_explanation2 = (TextView) findViewById(R.id.d_explanation2);
                d_explanation2.setText(vo.getD_explanation2());
                TextView d_explanation3 = (TextView) findViewById(R.id.d_explanation3);
                d_explanation3.setText(vo.getD_explanation3());

            }
        }
    }
}
