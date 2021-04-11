package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.sample.kosbank.R;

public class SavingsDetailActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;
    String J_name, J_summary, J_type, J_explanation, J_notice, J_rate, J_max_date, J_min_date;
    TextView j2,j3, j4, j6, j8, j10, j14, j16;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savingsdetail);

        Intent intent = getIntent();
        J_name = intent.getStringExtra("j_name"); //req.getParameter("id")'
        J_summary = intent.getStringExtra("j_summary"); //req.getParameter("id")'
        J_type = intent.getStringExtra("j_type"); //req.getParameter("id")'
        J_explanation = intent.getStringExtra("j_explanation"); //req.getParameter("id")'
        J_notice = intent.getStringExtra("j_notice"); //req.getParameter("id")'
        J_rate = intent.getStringExtra("j_interest_rate");
        J_max_date = intent.getStringExtra("j_max_date");
        J_min_date = intent.getStringExtra("j_min_date");

        j2 = findViewById(R.id.j_name);
        j3 = findViewById(R.id.j_summary);
        j4 = findViewById(R.id.j_rate);
        j6 = findViewById(R.id.j_type);
        j8 = findViewById(R.id.j_max_date);
        j10 = findViewById(R.id.j_min_date);
        j14 = findViewById(R.id.j_explanation);
        j16 = findViewById(R.id.j_notice);

        j2.setText(J_name);
        j3.setText(J_summary);
        j4.setText(J_rate);
        j6.setText(J_type);
        j8.setText(J_max_date);
        j10.setText(J_min_date);
        j14.setText(J_explanation);
        j16.setText(J_notice);
    }
}
