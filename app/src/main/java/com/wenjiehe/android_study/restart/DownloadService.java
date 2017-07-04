package com.wenjiehe.android_study.restart;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.wenjiehe.android_study.MainActivity;
import com.wenjiehe.android_study.R;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class DownloadService extends Service {

    String downloadUrl =null;
    DownloadTask downloadTask;

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder;
    }

    DownloadBinder downloadBinder =new DownloadBinder();

    public class DownloadBinder extends Binder {
        public void start(String url){
            if(downloadTask==null){
                downloadTask = new DownloadTask(downloadListener);
                downloadUrl =url;
                downloadTask.execute(downloadUrl);
                startForeground(1,getNotification("DownLoading...",0));
            }
        }
        public void pause(){
            if(downloadTask!=null){
                downloadTask.pause();
            }
        }

        public  void stop(){
            if(downloadTask!=null){
                downloadTask.stop();
            }else {
                if(downloadUrl!=null){
                    String fileName =downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                    String directory = Environment.getExternalStorageDirectory()+"/";
                    File file = new File(directory+fileName);
                    if(file.exists()){
                        file.delete();
                    }
                    getNotificationManager().cancel(1);
                    stopForeground(true);
                    Toast.makeText(DownloadService.this,"取消下载",Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

    private DownloadListener downloadListener =new DownloadListener(){

        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("下载最新安装包中...",progress));
        }

        @Override
        public void onSuccess() {
            downloadTask =null;
            stopForeground(true);
            getNotificationManager().notify(1, getNotification("下载成功",-1));
        }

        @Override
        public void onPause() {
            downloadTask =null;
            Toast.makeText(DownloadService.this,"暂停下载",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            downloadTask=null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"下载失败",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStop() {
            downloadTask=null;
            stopForeground(true);
            Toast.makeText(DownloadService.this,"取消下载",Toast.LENGTH_SHORT).show();
        }
    };


    private NotificationManager getNotificationManager(){
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String tittle,int progress){
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setContentTitle(tittle);
        if(progress>=0){
            builder.setProgress(100,progress,false);
            builder.setContentText(progress+"%");
        }
        return builder.build();
    }

    /*public interface BlueService {
        @GET("book/search")
        Call<IBinder> getSearchBooks(@Query("q") String name,
                                     @Query("tag") String tag, @Query("start") int start,
                                     @Query("count") int count);
    }*/
}
