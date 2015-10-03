package com.jude.tagviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jude.tagview.TAGView;

public class MainActivity extends AppCompatActivity {
    private TAGView view;
    private TAGView cer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cer = (TAGView) findViewById(R.id.tag_certification);
        cer.setAsCircle();
        cer.setBackgroundColor(Color.GRAY);

        view = (TAGView) findViewById(R.id.tag_text);
        view.setText("FB 的終極野心：用手機端智慧管家 M 取代 Google 大神的搜尋地位");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText("COOL");
            }
        });
    }


}
