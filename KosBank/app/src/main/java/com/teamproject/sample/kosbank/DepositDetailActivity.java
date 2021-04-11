package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.Deposit_productVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DepositDetailActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    String y_name, y_summary, y_type, y_explanation, y_notice, y_rate, y_max_date, y_min_date, y_min_price;
    TextView y1, y2, y3, y4, y5, y6, y7, y8, y9, y10, y12, y14, y16;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depositdetail);

        Intent intent = getIntent();
        y_name = intent.getStringExtra("y_name"); //req.getParameter("id")'
        y_summary = intent.getStringExtra("y_summary"); //req.getParameter("id")'
        y_type = intent.getStringExtra("y_type"); //req.getParameter("id")'
        y_explanation = intent.getStringExtra("y_explanation"); //req.getParameter("id")'
        y_notice = intent.getStringExtra("y_notice"); //req.getParameter("id")'
        y_rate = intent.getStringExtra("y_interest_rate");
        y_max_date = intent.getStringExtra("y_max_date");
        y_min_date = intent.getStringExtra("y_min_date");
        y_min_price = intent.getStringExtra("y_min_price");

        y2 = findViewById(R.id.y_name);
        y3 = findViewById(R.id.y_summary);
        y4 = findViewById(R.id.y_rate);
        y6 = findViewById(R.id.y_type);
        y8 = findViewById(R.id.y_max_date);
        y10 = findViewById(R.id.y_min_date);
        y12 = findViewById(R.id.y_min_price);
        y14 = findViewById(R.id.y_explanation);
        y16 = findViewById(R.id.y_notice);

        y2.setText(y_name);
        y3.setText(y_summary);
        y4.setText(y_rate);
        y6.setText(y_type);
        y8.setText(y_max_date);
        y10.setText(y_min_date);
        y12.setText(y_min_price);
        y14.setText(y_explanation);
        y16.setText(y_notice);


    }

}
