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
import com.teamproject.sample.kosbank.VO.Deposit_productVO;
import com.teamproject.sample.kosbank.VO.Savings_productVO;

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

        bank_deposit = findViewById(R.id.bank_deposit); // 적금상품 페이지 이동
        bank_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankItemActivity.this, DepositActivity.class);
                startActivity(intent);
            }
        });

        bank_saving = findViewById(R.id.bank_saving); // 예금상품 페이지 이동
        bank_saving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankItemActivity.this, SavingsActivity.class);
                startActivity(intent);
            }
        });

        InnerTask task = new InnerTask();   // 예금
        task.execute();
        InnerTask2 task2 = new InnerTask2();    // 적금
        task2.execute();
        /*InnerTask2 task3 = new InnerTask3();    // 대출
        task3.execute();*/

        /*bank_loan = findViewById(R.id.bank_loan); // 대출상품 페이지 이동
        bank_loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BankItemActivity.this, LoansProductsActivity.class);
                startActivity(intent);
            }
        });*/

        deposit = findViewById(R.id.d_name1);
        saving = findViewById(R.id.d_name2);




        /*loan = findViewById(R.id.d_name3); // 대출상품 상세페이지 이동
        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = loan.getText().toString();

                Intent intent = new Intent(BankItemActivity.this, LoansDetailActivity.class);
                intent.putExtra("d_name", value);
                startActivity(intent);
            }
        });*/
    }

    private class InnerTask extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "BankItemDeposit");

            HttpClient post = http.create();
            post.request();

            String body = post.getBody();
            return body;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("JSON RESULT", (String) o);
            if (((String) o).length() > 0) {
                Gson gson = new Gson();
                Deposit_productVO vo = gson.fromJson(o.toString(), Deposit_productVO.class);
                TextView d_name1 = (TextView) findViewById(R.id.d_name1);
                d_name1.setText(vo.getY_name());
                TextView d_summary1 = (TextView) findViewById(R.id.d_summary1);
                d_summary1.setText(vo.getY_summary());

                deposit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String y_name = vo.getY_name();
                        String y_summary = vo.getY_summary();
                        String y_interest_rate = vo.getY_interest_rate();
                        String y_type = vo.getY_type();
                        String y_max_date = vo.getY_max_date();
                        String y_min_date = vo.getY_min_date();
                        String y_min_price = vo.getY_min_price();
                        String y_explanation = vo.getY_explanation();
                        String y_notice = vo.getY_notice();

                        Intent intent = new Intent(BankItemActivity.this, DepositDetailActivity.class);
                        intent.putExtra("y_name", y_name);
                        intent.putExtra("y_summary", y_summary);
                        intent.putExtra("y_interest_rate", y_interest_rate);
                        intent.putExtra("y_type", y_type);
                        intent.putExtra("y_max_date", y_max_date);
                        intent.putExtra("y_min_date", y_min_date);
                        intent.putExtra("y_min_price", y_min_price);
                        intent.putExtra("y_explanation", y_explanation);
                        intent.putExtra("y_notice", y_notice);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    private class InnerTask2 extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "BankItemSavings");

            HttpClient post = http.create();
            post.request();

            String body = post.getBody();
            return body;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("JSON RESULT", (String) o);
            if (((String) o).length() > 0) {
                Gson gson = new Gson();
                Savings_productVO vo = gson.fromJson(o.toString(), Savings_productVO.class);
                TextView d_name2 = (TextView) findViewById(R.id.d_name2);
                d_name2.setText(vo.getJ_name());
                TextView d_summary2 = (TextView) findViewById(R.id.d_summary2);
                d_summary2.setText(vo.getJ_summary());

                saving.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String j_name = vo.getJ_name();
                        String j_summary = vo.getJ_summary();
                        String j_interest_rate = vo.getJ_interest_rate();
                        String j_type = vo.getJ_type();
                        String j_max_date = vo.getJ_max_date();
                        String j_min_date = vo.getJ_min_date();
                        String j_explanation = vo.getJ_explanation();
                        String j_notice = vo.getJ_notice();

                        Intent intent = new Intent(BankItemActivity.this, SavingsDetailActivity.class);
                        intent.putExtra("j_name", j_name);
                        intent.putExtra("j_summary", j_summary);
                        intent.putExtra("j_interest_rate", j_interest_rate);
                        intent.putExtra("j_type", j_type);
                        intent.putExtra("j_max_date", j_max_date);
                        intent.putExtra("j_min_date", j_min_date);
                        intent.putExtra("j_explanation", j_explanation);
                        intent.putExtra("j_notice", j_notice);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    /*private class InnerTask3 extends AsyncTask {
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
            if (((String) o).length() > 0) {
                Gson gson = new Gson();
                Loans_productVO vo = gson.fromJson(o.toString(), Loans_productVO.class);
                TextView d_name3 = (TextView) findViewById(R.id.d_name3);
                d_name3.setText(vo.getD_name());
                TextView d_summary3 = (TextView) findViewById(R.id.d_summary3);
                d_summary3.setText(vo.getD_summary());
            }
        }
    }*/



}