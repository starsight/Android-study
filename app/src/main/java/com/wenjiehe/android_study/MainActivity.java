package com.wenjiehe.android_study;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import com.avos.avoscloud.AVAnalytics;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.ObjectValueFilter;
import com.wenjiehe.android_study.service.MyService;

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

    private TextView tx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = new ArrayList<Object>();
        initView();
    }

    private void initView() {
        tx = (TextView)findViewById(R.id.tx);
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

        tx.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        tx.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Toast.makeText(MainActivity.this, "keylisytener~", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        final Intent intent = new Intent(MainActivity.this,MyService.class);
        intent.setAction("com.wenjiehe.android_study.service.MY_SERVICE");
        intent.setPackage("com.wenjiehe");
        Log.i("Mainb",getPackageName());
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startService(intent);
                /*Intent in  = new Intent(MainActivity.this,Main2Activity.class);
                //in.putExtra("a",one_selected);
                Bundle bd = new Bundle();
                bd.putBoolean("firstActivity",one_selected);
                in.putExtras(bd);
                startActivityForResult(in,3);*/
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("Main22",String.valueOf(requestCode));
        Log.d("Main22",String.valueOf(resultCode));
        if(requestCode==0){
        }
        super.onActivityResult(requestCode, resultCode, data);
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
         /*AlertDialog alert = null;
         AlertDialog.Builder builder = null;
        alert = null;
        builder = new AlertDialog.Builder(mContext);
        alert = builder.setIcon(R.mipmap.ic_launcher)
                .setTitle("系统提示：")
                .setMessage("这是一个最普通的AlertDialog,\n带有三个按钮，分别是取消，中立和确定")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNeutralButton("中立", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(mContext, "你点击了中立按钮~", Toast.LENGTH_SHORT).show();
                    }
                }).create();             //创建AlertDialog对象
        alert.show();                    //显示对话框*/
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
