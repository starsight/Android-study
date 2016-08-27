package com.wenjiehe.android_study;


import android.content.Context;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.ObjectValueFilter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ArrayList<Object> mData = null;
    ListView listview;

    private Spinner spin_one;
    //private Spinner spin_two;
    private Context mContext;
    //判断是否为刚进去时触发onItemSelected的标志
    //private boolean one_selected = false;
    private boolean one_selected = false;
    private SpinnerAdapter spinnerAdadpter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = new ArrayList<Object>();
        initView();
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        mData.add(new SignText("1"));
        mData.add(new SignPic("2"));
        mData.add(new SignPic("2"));
        mData.add(new SignText("1"));
        adapter ad = new adapter(mData, R.layout.text_list);
        listview.setAdapter(ad);

        mContext = MainActivity.this;
        spinnerAdadpter = new SpinnerAdapter(mData, R.layout.item_spin_hero);
        spin_one = (Spinner) findViewById(R.id.spin_one);
        spin_one.setAdapter(spinnerAdadpter);
        spin_one.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_one:
                if (one_selected) {
                    myToast(MainActivity.this,"您的分段是~：" + parent.getItemAtPosition(position).toString());
                } else one_selected = true;
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void myToast(Context mContext,String str){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.view_toast_custom,
                (ViewGroup) findViewById(R.id.lly_toast));
        //ImageView img_logo = (ImageView) view.findViewById(R.id.img_logo);
        TextView tv_msg = (TextView) view.findViewById(R.id.tv_msg);
        tv_msg.setText(str);
        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

}
