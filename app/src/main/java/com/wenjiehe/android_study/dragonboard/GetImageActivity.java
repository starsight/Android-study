package com.wenjiehe.android_study.dragonboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.okhttp.internal.framed.FrameReader;
import com.wenjiehe.android_study.MyUtils;
import com.wenjiehe.android_study.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class GetImageActivity extends AppCompatActivity {



    private ImageView iv_dragonborad;
    private Button bt_get;
    private Button bt_show;
    private Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);

        iv_dragonborad = (ImageView)findViewById(R.id.iv_dragonboard);
        bt_get = (Button)findViewById(R.id.bt_get);
        bt_show = (Button)findViewById(R.id.bt_show);

        bt_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread t = new Thread(new Runnable(){
                    public void run(){
                        URL url = null;
                        try {
                            url = new URL("http://api.yeelink.net/v1.0/device/344726/sensor/383332/photo/content");
                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                            // 设置连接超时为5秒
                            conn.setConnectTimeout(5000);
                            // 设置请求类型为Get类型
                            conn.setRequestMethod("GET");
                            // 判断请求Url是否成功
                            if (conn.getResponseCode() != 200) {
                                throw new RuntimeException("请求url失败");
                            }
                            InputStream inStream = conn.getInputStream();
                            byte[] bt = MyUtils.read(inStream);
                            inStream.close();
                            bmp  = BitmapFactory.decodeByteArray(bt,0,bt.length);
                            Log.d("http","okkk");
                            //Toast.makeText(getApplicationContext(),"获取图片成功",Toast.LENGTH_SHORT).show();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (ProtocolException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();

            }
        });


        bt_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_dragonborad.setImageBitmap(bmp);
            }
        });

    }
}
