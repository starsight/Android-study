package com.wenjiehe.android_study;


import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.Toast;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{

    private ArrayList<Object> mData = null;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = new ArrayList<Object>();
        initView();
    }

    private void initView() {
        listview = (ListView)findViewById(R.id.listview);
        mData.add(new SignText("1"));
        mData.add(new SignPic("2"));
        mData.add(new SignPic("2"));
        mData.add(new SignText("1"));
        adapter ad = new adapter(mData,R.layout.text_list);
listview.setAdapter(ad);
    }



}
