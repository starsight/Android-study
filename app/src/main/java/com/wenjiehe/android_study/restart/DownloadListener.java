package com.wenjiehe.android_study.restart;

/**
 * Created by Administrator on 2017/6/4.
 */

public interface DownloadListener {
    void onProgress(int progress);
    void onSuccess();
    void onPause();
    void onFailed();
    void onStop();
}
