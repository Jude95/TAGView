package com.jude.tagviewdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jude.tagview.TAGView;

public class MainActivity extends AppCompatActivity {
    private TAGView view;
    private TAGView cer;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TAGView cer = (TAGView) findViewById(R.id.tag_certification_radius);
        cer.setRadius(dip2px(8));

        TAGView cer2 = (TAGView) findViewById(R.id.tag_certification_stroke);
        cer2.setStrokeWidth(dip2px(2));

        view = (TAGView) findViewById(R.id.tag_text);
        view.setText("FB 的終極野心：用手機端智慧管家 M 取代 Google 大神的搜尋地位");
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.setText("COOL");
            }
        });

        container = findViewById(R.id.container);

        TAGView tagView = new TAGView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dip2px(28));
        params.setMargins(dip2px(16), 0, 0, 0);
        tagView.setLayoutParams(params);
        tagView.setPadding(dip2px(16), 0, dip2px(16), 0);
        tagView.setText("接受");
        tagView.setTextColor(Color.GRAY);
        tagView.setBackgroundColor(Color.GRAY);
        tagView.setStrokeWidth(dip2px(1));
        tagView.setRadius(dip2px(16));
        container.addView(tagView);
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
