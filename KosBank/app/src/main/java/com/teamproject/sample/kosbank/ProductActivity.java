package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.sample.kosbank.R;

public class ProductActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private View drawerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //테스트4 클릭시
        View Btn_depositProduct = (View)findViewById(R.id.Btn_depositProduct);
        Btn_depositProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DepositList_Activity.class);
                startActivity(intent);
            }
        });




    }
}
