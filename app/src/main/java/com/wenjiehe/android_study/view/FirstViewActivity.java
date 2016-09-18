package com.wenjiehe.android_study.view;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wenjiehe.android_study.R;

public class FirstViewActivity extends AppCompatActivity {

    //private FirstView firstview;
    private CircleImageView civ;
    //private StripMeiZi smz;
    private Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_view);

        //firstview = (FirstView)findViewById(R.id.firstview);
        civ = (CircleImageView)findViewById(R.id.circleimageview);
        //smz = (CircleImageView)findViewById(R.id.circleimageview);
        bt = (Button)findViewById(R.id.view_id);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator oa = ObjectAnimator.ofFloat(civ,"alpha",1f,0f,1f);
                oa.setDuration(3000);
                oa.start();
                civ.animate().alpha(0.5f);
            }
        });
    }

}
