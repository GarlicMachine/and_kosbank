package com.teamproject.sample.kosbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.sample.kosbank.R;

public class SignActivity extends AppCompatActivity {
    private EditText edtName, edtRRNf, edtRRNl, edtAddress;
    private Button btnOCR;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        btnOCR = (Button) findViewById(R.id.btn_OCR);

        Intent intent= getIntent();
        String Name = intent.getStringExtra("Name");
        String RRNf = intent.getStringExtra("RRNf");
        String RRNl = intent.getStringExtra("RRNl");
        String Address = intent.getStringExtra("Address");

        edtName = (EditText) findViewById(R.id.SignName);
        edtRRNf = (EditText) findViewById(R.id.SignRRN_first);
        edtRRNl = (EditText) findViewById(R.id.SignRRN_last);
        edtAddress = (EditText) findViewById(R.id.SignAddress);

        edtName.setText(Name);
        edtRRNf.setText(RRNf);
        edtRRNl.setText(RRNl);
        edtAddress.setText(Address);




        btnOCR.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OCR_Activity.class);
                startActivity(intent);
            }
        });

    }
}
