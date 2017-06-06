package com.wenjiehe.android_study.restart;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/28.
 */

public class Utils {
    static void ToastShow(Context context ,String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }
}
