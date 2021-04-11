package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import android.util.Log;
import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.FundingVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FundCategoryActivity extends AppCompatActivity {

    LinearLayout f_category_all;
    LinearLayout f_category_tech;
    LinearLayout f_category_fashion;
    LinearLayout f_category_beauty;
    LinearLayout f_category_food;
    LinearLayout f_category_home;
    LinearLayout f_category_design;
    LinearLayout f_category_trip;
    LinearLayout f_category_sports;
    LinearLayout f_category_pet;
    LinearLayout f_category_game;
    LinearLayout f_category_etc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fundcategory);

        //InnerTask task = new InnerTask();
        //task.execute();

        // 펀드카테고리 - '전체보기' 선택
        f_category_all = findViewById(R.id.f_category_all);
        f_category_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "전체보기");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '테크·가전' 선택
        f_category_tech = findViewById(R.id.f_category_tech);
        f_category_tech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "테크·가전");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '패션·잡화' 선택
        f_category_fashion = findViewById(R.id.f_category_fashion);
        f_category_fashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "패션·잡화");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '뷰티' 선택
        f_category_beauty = findViewById(R.id.f_category_beauty);
        f_category_beauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "뷰티");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '푸드' 선택
        f_category_food = findViewById(R.id.f_category_food);
        f_category_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "푸드");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '홈리빙' 선택
        f_category_home = findViewById(R.id.f_category_home);
        f_category_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "홈리빙");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '디자인·소품' 선택
        f_category_design = findViewById(R.id.f_category_design);
        f_category_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "디자인·소품");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '여행·레저' 선택
        f_category_trip = findViewById(R.id.f_category_trip);
        f_category_trip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "여행·레저");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '스포츠·모빌리티' 선택
        f_category_sports = findViewById(R.id.f_category_sports);
        f_category_sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "스포츠·모빌리티");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '반려동물' 선택
        f_category_pet = findViewById(R.id.f_category_pet);
        f_category_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "반려동물");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '게임·취미' 선택
        f_category_game = findViewById(R.id.f_category_game);
        f_category_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "게임·취미");
                startActivity(intent);
            }
        });

        // 펀드카테고리 - '기타' 선택
        f_category_etc = findViewById(R.id.f_category_etc);
        f_category_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FundCategoryActivity.this, FundListActivity.class);
                intent.putExtra("f_category", "기타");
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
        protected String doInBackground(Object[] objects) {
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + "FundProducts");

            HttpClient post = http.create();
            post.request();

            String body = post.getBody();
            return body;
        }

        @Override
        protected void onPostExecute(Object o) {
            Log.d("JSON_RESULT", (String) o);

            Gson gson = new Gson();
            FundingVO[] list = gson.fromJson(o.toString(),  FundingVO[].class);
            List<FundingVO> array = Arrays.asList(list);

            // 2. 어댑터 생성,,, 데이터를 가져오기 위함
            FundAdapter adapter = new FundAdapter(array);  // WeatherAdapter 매개변수 생성자를 먼저 만들고 생성

            // 3. 뷰와 어댑터 연결

        }
    }


}
