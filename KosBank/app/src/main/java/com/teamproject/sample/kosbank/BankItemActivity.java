package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.Loans_productVO;

public class BankItemActivity extends AppCompatActivity {

    private LinearLayout bank_saving;
    private LinearLayout bank_deposit;
    private LinearLayout bank_loan;
    private TextView saving;
    private TextView deposit;
    private TextView loan;
    private String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankitem);

        InnerTask task = new InnerTask();
        task.execute();

        /*bank_saving = findViewById(R.id.bank_saving); // 예금상품 페이지 이동
        bank_saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankItemActivity.this, "예금으로 고치세요".class);
                startActivity(intent);
            }
        });*/

        /*bank_deposit = findViewById(R.id.bank_deposit); // 적금상품 페이지 이동
        bank_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankItemActivity.this, "적금으로 고치세요".class);
                startActivity(intent);
            }
        });*/

        bank_loan = findViewById(R.id.bank_loan); // 대출상품 페이지 이동
        bank_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankItemActivity.this, LoansProductsActivity.class);
                startActivity(intent);
            }
        });

        // 예금상품 페이지 이동

        // 적금상품 페이지 이동

        loan = findViewById(R.id.d_name3); // 대출상품 상세페이지 이동
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = loan.getText().toString();

                Intent intent = new Intent(BankItemActivity.this, LoansDetailActivity.class);
                intent.putExtra("d_name", value);
                startActivity(intent);
            }
        });
    }

    private class InnerTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "BankItemLoan");

            HttpClient post = http.create();
            post.request();

            String body = post.getBody();
            return body;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("JSON RESULT", (String) o);
            if(((String) o).length() > 0) {
                Gson gson = new Gson();

                Loans_productVO vo = gson.fromJson(o.toString(), Loans_productVO.class);
                TextView d_name3 = (TextView) findViewById(R.id.d_name3);
                d_name3.setText(vo.getD_name());
                TextView d_summary3 = (TextView) findViewById(R.id.d_summary3);
                d_summary3.setText(vo.getD_summary());
            }
        }
    }
}
