package com.teamproject.sample.kosbank.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.sample.kosbank.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ListTest  extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_test);


        ArrayList<testVO> data= new ArrayList<testVO>();
        data.add(new testVO("서울","20도","맑음","1"));
        data.add(new testVO("서울","30도","맑음","2"));
        data.add(new testVO("서울","40도","맑음","3"));
        data.add(new testVO("서울","50도","맑음","4"));
        data.add(new testVO("서울","60도","맑음","5"));
        data.add(new testVO("서울","70도","맑음","6"));
        data.add(new testVO("서울","80도","맑음","7"));
        data.add(new testVO("서울","90도","맑음","8"));

        testAdapter adapter = new testAdapter(data);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListTest.this,data.get(position).getText2(),Toast.LENGTH_SHORT).show();
            }
        });
    }


}

