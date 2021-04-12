package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.sample.kosbank.R;
import com.teamproject.sample.kosbank.VO.AccountVO;
import com.teamproject.sample.kosbank.VO.TransferDetailVO;
import com.teamproject.sample.kosbank.test.fmt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransferDetail_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_detail);

        Intent intent= getIntent();
        String account = intent.getStringExtra("account");
        Log.d("trantran", account);

        TransferDetail_Activity.InnerTask task = new TransferDetail_Activity.InnerTask();
        Map<String,String> map = new HashMap<>();
        map.put("account", account); // MainActivity에서 값을 받아온 id
        task.link="get_TransferDetail";
        task.execute(map);
    }
    public class InnerTask extends AsyncTask<Map, Integer, String>{
        public String link = "";
        @Override
        protected void onPreExecute() {super.onPreExecute();}

        @Override
        protected String doInBackground(Map... maps) {
            //HTTP 요청 준비
            HttpClient.Builder http = new HttpClient.Builder("POST", Web.servletURL + link); //스프링 url
            //파라미터 전송
            http.addAllParameters(maps[0]);

            //HTTP 요청 전송
            HttpClient post = http.create();
            post.request();

            // 안드로이드에서 응답받음
            String body = post.getBody(); //Web의 Controller에서 리턴한 값
            Log.d("get_TransferDetail", body);
            return body;
        }



        @Override
        protected void onPostExecute(String s) {
            //JSON으로 받은 데이터를 VO Obejct로 바꿔준다.
            if(link.equals("get_TransferDetail")){
                if(s.length() > 0) {
                    Gson gson = new Gson();
                    TransferDetailVO[] array = gson.fromJson(s, TransferDetailVO[].class);
                    List<TransferDetailVO> TransferDetail = Arrays.asList(array);
                    Log.d("Transver List ==> " , TransferDetail.toString());

                    TransferDetailAdapter adapter = new TransferDetailAdapter(TransferDetail);
                    ListView list_TransferDetail = (ListView)findViewById(R.id.list_TransferDetail);
                    list_TransferDetail.setAdapter(adapter);
                }
            }
        }
    }
}
