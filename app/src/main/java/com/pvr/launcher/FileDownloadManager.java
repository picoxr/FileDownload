package com.pvr.launcher;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by jeffreyliu on 16/11/5.
 */

public class FileDownloadManager implements IFileDownloaderListerer {
    public static final String TAG = "---Downloadmgr---";
    private static FileDownloadManager instance = null;
    private static final Object lock = new Object();

    private boolean allowWlan = false;
    private boolean isclosed = false;

    int progress = 0;

    private Context context = null;
    private OkHttpClient client = null;
    private List<DownloadModel> downloadModelList = null;

    private FileDownloader filedownloader = null;

    MyHandler handler;

    public static FileDownloadManager getInstance(Context context) {
        if (null == instance) {
            synchronized (lock) {
                instance = new FileDownloadManager(context);
            }
        }
        return instance;
    }

    public int getProgress() {
        return this.progress;
    }

    private FileDownloadManager(Context context) {
        this.context = context;
    }

    private void initHttpClient() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        okBuilder.connectTimeout(15, TimeUnit.SECONDS);
        okBuilder.readTimeout(15, TimeUnit.SECONDS);
        this.client = okBuilder.build();
    }

    public boolean insertDownloadTask(String json) {
        try {
            JSONObject object = new JSONObject(json);
            DownloadModel model = DownloadModel.downloadFromJSONObject(object);
            Log.e(TAG, "insertDownloadTask: json");
            downloadRealUrl(model);
            return true;
        } catch (JSONException e) {
            Log.e(TAG, "添加下载项目失败");
            e.printStackTrace();
            return false;
        }
    }

    private void downloadRealUrl(DownloadModel model) {
        handler = new MyHandler(Looper.getMainLooper());
        Log.e(TAG, "downloadRealUrl: " );
        Message msg = Message.obtain();
        msg.what = 0;
        msg.obj = model;
//        Bundle bundle = new Bundle();
//        bundle.putString("REALURL", model.getUrl());
//        msg.setData(bundle);
        handler.sendMessage(msg);
        Log.e(TAG, msg.what + "" );
    }


    public class MyHandler extends Handler {
        MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage:handle ");
            super.handleMessage(msg);
            switch (msg.what) {
                case 0: {
                    Log.e(TAG, "handleMessage: switch" );
                    DownloadModel model = (DownloadModel) msg.obj;
                    DownloadUtil.get().download(context, model.getUrl(), model.getDestFileDir(), model.getDestFileName(),  new DownloadUtil.OnDownloadListener() {
                        @Override
                        public void onDownloadSuccess() {}

                        @Override
                        public void onDownloading(final int progress) {
                            Log.e(TAG, "onDownloading: " + progress );
                            instance.progress = progress;
                        }

                        @Override
                        public void onDownloadFailed() {}
                    });
                }
                break;
                default:
                    break;
            }
        }
    }

    @Override
    public void started(FileDownloader downloader, Object download, long totalsize) {}


    @Override
    public void byteReaded(FileDownloader downloader, Object download, byte[] bytes, int length) {}

    @Override
    public void failure(FileDownloader downloader, Object download) {}

    @Override
    public void cancelled(FileDownloader downloader, Object download) {}

    @Override
    public void completed(FileDownloader downloader, Object download) {}

}
