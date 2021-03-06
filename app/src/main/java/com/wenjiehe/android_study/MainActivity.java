package com.wenjiehe.android_study;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.content.LocalBroadcastManager;
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

import com.wenjiehe.android_study.service.MyBroadcastReceiver;
import com.wenjiehe.android_study.service.MyService;
import com.wenjiehe.android_study.view.FirstViewActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.R.attr.name;


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

    MyBroadcastReceiver mbr;
    LocalBroadcastManager localBroadcastManager;

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


        tx.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Toast.makeText(MainActivity.this, "keylisytener~", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        /*mbr = new MyBroadcastReceiver();
        IntentFilter inf = new IntentFilter();
        inf.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mbr,inf);*/

        final Intent intent = new Intent(MainActivity.this,MyService.class);
        intent.setAction("com.wenjiehe.android_study.service.MY_SERVICE");
        intent.setPackage("com.wenjiehe");
        //Log.i("Mainb",getPackageName());

        final MyDbOpenHelper mdoh = new MyDbOpenHelper(MainActivity.this,"my.db",null,3);

        /*FragmentManager fm =getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.bottom_layout,new FirstFragment());
        ft.addToBackStack(null);
        ft.commit();*/

        final SecondFragment  sf = new SecondFragment();


        //localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter =new IntentFilter();
        intentFilter.addAction("com.wenjiehe.broadcast");
        //localBroadcastManager.registerReceiver(new MyBroadcastReceiver(),intentFilter);

        registerReceiver(new MyBroadcastReceiver(),intentFilter);

        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mdoh.getWritableDatabase().execSQL("insert into person values(null,?)",new String[]{"wenjie"});
                /*Cursor cursor = mdoh.getReadableDatabase().rawQuery("select * from person where name like ?",new String[]{"wenjie"});
                if(cursor.moveToFirst())
                    Log.d("Main",cursor.getString(cursor.getColumnIndex("name")));*/
                //mdoh.onCreate();
                /*Intent intent0 = new Intent();
                intent0.setAction("my_action");
                intent0.addCategory("my_category");
                startActivity(intent0);*/
                //startService(intent);
                /*Intent in  = new Intent(MainActivity.this,Main2Activity.class);
                //in.putExtra("a",one_selected);
                Bundle bd = new Bundle();
                bd.putBoolean("firstActivity",one_selected);
                in.putExtras(bd);
                startActivityForResult(in,3);*/

                /*FragmentManager fm =getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args =new Bundle();
                byte bb =0x1;
                args.putByte("1",bb);
                sf.setArguments(args);
                ft.replace(R.id.bottom_layout,sf);
                ft.addToBackStack(null);
                ft.commit();*/
                //Toast.makeText(MainActivity.this,"tx",Toast.LENGTH_SHORT).show();

                /**
                 * fragment -> activity
                 * 通过回调函数，结合getData，先在getData获取到数据，再调用callback的getResult，把获取到的数据传参数到getResult
                 * 再在activity中
                 */
                sf.getData(new SecondFragment.Callback() {
                    @Override
                    public void getResult(String result) {
                        Toast.makeText(MainActivity.this, "-->>" + result, Toast.LENGTH_SHORT).show();
                    }
                });

                FileOutputStream fileOutputStream;
                BufferedWriter bufferedWriter;
                String str="";
                File f = new File(Environment.getExternalStorageDirectory() + "/did");
                try {
                    //fileOutputStream = openFileOutput("datas",MODE_APPEND);
                    str = Environment.getExternalStorageDirectory() + "/did" ;
                    fileOutputStream = new FileOutputStream(f);
                    bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
                    bufferedWriter.write("nihaoya");
                    //bufferedWriter.flush();
                    bufferedWriter.close();
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                FileInputStream fileInputStream;
                BufferedReader bufferedReader;
                try {
                    //fileInputStream = openFileInput("datas");
                    fileInputStream =new FileInputStream(f);
                    bufferedReader =new BufferedReader(new InputStreamReader(fileInputStream));
                    String str1;
                    while((str1=bufferedReader.readLine())!=null){
                        Toast.makeText(MainActivity.this,str1,Toast.LENGTH_SHORT).show();
                    }
                    bufferedReader.close();
                    fileInputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*Intent inte = new Intent();
                inte.setAction("com.wenjiehe.broadcast");
                sendBroadcast(inte);*/
                //localBroadcastManager.sendBroadcast(inte);
                //Intent int_view = new Intent(MainActivity.this, GetImageActivity.class);
                //startActivity(int_view);
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

    @Override
    protected void onDestroy() {
        unregisterReceiver(mbr);
        super.onDestroy();
    }
}
