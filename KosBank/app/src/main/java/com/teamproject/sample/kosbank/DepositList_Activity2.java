package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.google.sample.kosbank.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

/**
 * Created by psn on 2018-02-07.
 */

public class DepositList_Activity2 extends AppCompatActivity {

    InnerTask task = null;
    String id;

    /*@Override
    protected void onResume() {
        super.onResume();

        if(task != null) {
            task.cancel(true);
            task = null;

            task = new InnerTask();
            task.execute(id);
        }
    }*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depositlist2);

        Intent intent = getIntent();
        id = intent.getStringExtra("y_name"); //req.getParameter("id")'

        task = new InnerTask();
        task.execute(id);

    }



    private class InnerTask extends AsyncTask {

        //MypageRecyAdapter adapter;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "androidMyPageMain"); //@RequestMapping url
            http.addOrReplace("id", (String) objects[0]);

            HttpClient post = http.create();
            post.request();

            String body = post.getBody();
            return body;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("JSON_RESULT", (String) o);
            Gson gson = new Gson();
            Data data = gson.fromJson((String) o, Data.class);

            try {
                TextView name = (TextView) findViewById(R.id.Btn_savingsProduct);
                name.setText(data.getData1() + "님");

                Log.d("JSON_RESULT", "이름 = " + data.getMember().get("y_name"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } //onPostExecute
           /* if(((String) o).length() > 0) {
                //JSON 데이터를 Class Object로
                Gson gson = new Gson();
                final Data data = gson.fromJson((String) o, Data.class);

                //회원 이름
                TextView name = findViewById(R.id.edt_name);
                name.setText(data.getData1() + "님");

                //등급
                TextView tvVip = findViewById(R.id.tv_vip);
                String vip = String.valueOf(data.getMember().get("member_cumPoint")); //값이 실수로 넘어옴
                tvVip.setText(getPointVip((int) Double.parseDouble(vip)));

                //건수
                TextView tvMovie = findViewById(R.id.tv_movie);
                TextView tvRest = findViewById(R.id.tv_rest);
                TextView tvPark = findViewById(R.id.tv_park);
                tvMovie.setText(data.getData2() + " 건");
                tvRest.setText(data.getData3() + " 건");
                tvPark.setText(data.getData4() + " 건");

                //이미지 버튼 클릭시
                ImageButton iBtn[] = new ImageButton[3];
                int[] btnRes = {R.id.btn_movie, R.id.btn_rest, R.id.btn_park};
                for (int i = 0; i < btnRes.length; i += 1) {
                    final int idx = i;
                    iBtn[i] = findViewById(btnRes[i]);
                    iBtn[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //페이지 이동 + 값 전달
                            Intent intent = new Intent(MyPageMainActivity.this, MyPageListActivity.class);
                            intent.putExtra("id", id); //로그인 아이디
                            intent.putExtra("idx", String.valueOf(idx)); //무슨 내역인지
                            startActivity(intent);
                        }
                    });
                }

                //회원정보 수정 버튼
                ImageButton iBtnMy = findViewById(R.id.ibtn_my);
                iBtnMy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MyPageMainActivity.this, MyPageInfoActivity.class);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                });

                //RecyclerView
                if (data.getBoardList().size() > 0) {
                    RecyclerView recyView = findViewById(R.id.re_board);
                    recyView.setVisibility(View.VISIBLE); //RecyclerView 보이기
                    recyView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    if (adapter == null) {
                        adapter = new MypageRecyAdapter(data.getBoardList());
                        recyView.setAdapter(adapter);
                    }

                    //문의 내역 없음 표시 가리기
                    TextView tvNull = findViewById(R.id.tv_null);
                    tvNull.setVisibility(View.GONE);
                }
            } else {
                Toast.makeText(getApplicationContext(), "정보 불러오기 실패", Toast.LENGTH_SHORT).show();
            }
        }*/
    }//InnerTask
} //MyPageMainActivity
//포인트에 따른 등급
   /* public String getPointVip(int member_cumPoint) {
        String vip = null;
        if (member_cumPoint <= 15000) {
            vip = "일반";
        } else if (member_cumPoint <= 30000) {
            vip = "VIP";
        } else if (member_cumPoint <= 45000) {
            vip = "VVIP";
        } else {
            vip = "SVIP";
        }
        return vip;
    }*/
