package com.wenjiehe.android_study.restart;

import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.drm.DrmStore.Playback.STOP;

/**
 * Created by Administrator on 2017/6/4.
 */

public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    public static final int SUCCESS =0;
    public static final int FAILED =1;
    public static final int PAUSE =2;
    public static final int STOP =3;

    private boolean isStop =false;
    private boolean isPause =false;

    DownloadListener downloadListener;

    DownloadTask(DownloadListener dl){
        downloadListener = dl;
    }

    @Override
    protected Integer doInBackground(String... params) {
        InputStream is =null;
        RandomAccessFile savedFile =null;
        File file =null;

        long downloadedLength =0;
        String downloadUrl =params[0];
        String path = Environment.getExternalStorageDirectory()+"/";
        file =new File(path+"xingji.apk");
        if(file.exists()){
            downloadedLength =file.length();
        }
        try {
        long contentLength =getContentLength(downloadUrl);
        if(contentLength==downloadedLength){
            return SUCCESS;
        }else if(contentLength==0){
            return  FAILED;
        }

        OkHttpClient okHttpClient =new OkHttpClient();
        Request request =new Request.Builder()
                .addHeader("RANGE","bytes="+downloadedLength+"-")
                .url(downloadUrl)
                .build();

        Response response = okHttpClient.newCall(request).execute();
            if (response != null) {
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                savedFile.seek(downloadedLength);
                byte[] b =new byte[1024];
                int total =0;
                int len;
                while((len=is.read(b))!=-1){
                    if(isStop)
                        return STOP;
                    else if(isPause)
                        return PAUSE;
                    else
                        total +=len;
                    savedFile.write(b,0,len);
                    int progress =(int)((total+downloadedLength)*100/contentLength);
                    publishProgress(progress);
                }
            }
            response.body().close();
            return SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if (is != null)
                    is.close();
                if (savedFile != null)
                    savedFile.close();
                if (isStop && file != null)
                    file.delete();
            }catch (Exception e){
                e.printStackTrace();
            }

        }

        return FAILED;
    }

    private long getContentLength(String url) throws IOException {
        OkHttpClient client =new OkHttpClient();
        Request request =new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if(response!=null&&response.isSuccessful()){
            long contentLength =response.body().contentLength();
            response.close();
            return contentLength;
        }

        return 0;
    }

    public void pause(){
        isPause =true;
    }

    public void stop(){
        isStop =true;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        switch(integer){
            case SUCCESS:
                downloadListener.onSuccess();
                break;
            case FAILED:
                downloadListener.onFailed();
                break;
            case PAUSE:
                downloadListener.onPause();
                break;
            case STOP:
                downloadListener.onStop();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int progress = values[0];
        downloadListener.onProgress(progress);
    }
}
