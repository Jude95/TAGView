package com.jude.tagviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.android.debug.hv.ViewServer;
import com.jude.tagview.TAGView;

public class MainActivity extends AppCompatActivity {
private TAGView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = (TAGView) findViewById(R.id.tag_text);
        view.setText("FB 的終極野心：用手機端智慧管家 M 取代 Google 大神的搜尋地位");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText("COOL");
            }
        });
        ViewServer.get(this).addWindow(this);
    }

    public void onDestroy() {
        super.onDestroy();
        ViewServer.get(this).removeWindow(this);
    }

    public void onResume() {
        super.onResume();
        ViewServer.get(this).setFocusedWindow(this);
    }

}
