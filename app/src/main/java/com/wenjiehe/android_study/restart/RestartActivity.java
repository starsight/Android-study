package com.wenjiehe.android_study.restart;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wenjiehe.android_study.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.bitmap;
import static java.security.AccessController.getContext;

public class RestartActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.button0)
    Button button0;

    @BindView(R.id.button1)
    Button button1;

    @BindView(R.id.button2)
    Button button2;

    @BindView(R.id.button3)
    Button button3;

    @BindView(R.id.iv_edit_userphoto)
    ImageView iv_edit_userphoto;

    @BindView(R.id.button4)
    Button button4;

    @BindView(R.id.button5)
    Button button5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restart);

        ButterKnife.bind(this);

        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.button0:{
                //Utils.ToastShow(this,"button0");
                checkPremission(); //检查权限


                break;
            }
            case R.id.button1:{
                //Utils.ToastShow(this,"button1");
                //Uri uri = Uri.parse("content://sms/");
                /*Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
                ContentResolver contentResolver =getContentResolver();
                Cursor cursor = contentResolver.query(uri, null, null, null, null);
                while(cursor.moveToNext())
                {
                    String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    System.out.println("姓名:" + cName);
                    System.out.println("号码:" + cNum);
                    System.out.println("======================");
                }
                cursor.close();*/
                //Intent intent =new Intent(this,Button1Activity.class);
                //startActivity(intent);

                Intent intent =new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent,42);

                break;
            }
            case R.id.button2:{
                NotificationManager mNManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNManager.cancel(1);

            }
            case R.id.button3:{
                Intent intent =new Intent(RestartActivity.this,Button3Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.button4:{
                Intent intent = new Intent(RestartActivity.this,Button4Activity.class);
                startActivity(intent);
                break;
            }
            case R.id.button5:{
                Intent intent = new Intent(RestartActivity.this,Button5Activity.class);
                startActivity(intent);
                break;
            }
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 42 && resultCode == Activity.RESULT_OK) {
            Uri uri;
            if (data != null) {
                uri = data.getData();
                Log.e("HeHe", "Uri: " + uri.toString());
            }
        }

    }

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int REQUEST_CAPTURE = 2;
    private static final int REQUEST_PICTURE = 5;
    private static final int REVERSAL_LEFT = 3;
    private static final int REVERSAL_UP = 4;

    private void checkPremission() {
        String permission_camera = Manifest.permission.CAMERA;
        String permission_storage = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        if(ContextCompat.checkSelfPermission(this,permission_camera)!=PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,permission_storage)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,permission_camera)||
                    (ActivityCompat.shouldShowRequestPermissionRationale(this,permission_storage))){
                new AlertDialog
                        .Builder(this)
                        .setTitle("提示")
                        .setMessage("内容")
                        .setPositiveButton("好", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(RestartActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
                            }
                        }).show();
                //ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},CAMERA_REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},CAMERA_REQUEST_CODE);
            }
        }else{
            Utils.ToastShow(this,"同意权限1");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {  //申请权限的返回值
            case CAMERA_REQUEST_CODE:
                int length = grantResults.length;
                final boolean isGranted = length >= 1 && PackageManager.PERMISSION_GRANTED == grantResults[length - 1];
                if (isGranted) {  //如果用户赋予权限，则调用相机
                    //openCamera();
                    Utils.ToastShow(this,"同意权限2");
                } else { //未赋予权限，则做出对应提示
                    Utils.ToastShow(this,"没有权限2");
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
