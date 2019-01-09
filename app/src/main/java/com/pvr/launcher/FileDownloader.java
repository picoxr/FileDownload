package com.pvr.launcher;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jeffreyliu on 16/11/8.
 */


public class FileDownloader implements Callback {
    private static final String TAG = FileDownloader.class.getSimpleName();

    private Call call = null;
    private OkHttpClient client = null;
    private Object downloaderObject = null;
    private IFileDownloaderListerer listener = null;

    public FileDownloader(OkHttpClient client,Object downloaderObject,IFileDownloaderListerer listener){
        this.client = client;
        this.listener = listener;
        this.downloaderObject = downloaderObject;
    }

    public void pause(){
        if(null != this.call && !this.call.isCanceled()){
            this.call.cancel();
        }
    }

    public void start(String url,long offset){
        Request request = new Request.Builder()
                .url(url).header("RANGE","bytes=" + offset + "-")
                .header("Accept-Encoding", "identity")
                .build();
        this.call = this.client.newCall(request);
        call.enqueue(this);
    }

    @Override
    public void onFailure(Call call, IOException e) {
        e.printStackTrace();
        if(this.call.isCanceled()){
            Log.d(TAG,"file downloaer task is canceled");
            if(null != this.listener){
                this.listener.cancelled(this,this.downloaderObject);
            }
        }else{
            Log.d(TAG,"file downloaer task is failed");
            if(null != this.listener){
                this.listener.failure(this,this.downloaderObject);
            }
        }
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
//        InputStream input = null;
//        try {
//        	Headers headers1 =  call.request().headers();
//        	for(int i = 0;i < headers1.size();i++){
//        		Log.i(TAG, "" + headers1.name(i) + "      r      " + headers1.value(i));
//        	}
//
//        	Headers headers =  response.headers();
//        	for(int i = 0;i < headers.size();i++){
//        		Log.i(TAG, "" + headers.name(i) + "    " + headers.value(i));
//        	}
//
//            long totalSize = response.body().contentLength();
//            if(null != this.listener){
//                this.listener.started(this,this.downloaderObject,totalSize);
//            }
//            input = response.body().byteStream();
//            byte[] bytes = new byte[12 * 1024];
//            int readLength = 0;
//            while ((readLength = input.read(bytes)) > 0) {
//                totalSize -= readLength;
//                if(null != this.listener){
//                    this.listener.byteReaded(this,this.downloaderObject,bytes,readLength);
//                }
//            }
//            if(totalSize == 0){
//                if(null != this.listener) {
//                    this.listener.completed(this, this.downloaderObject);
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            if(this.call.isCanceled()){
//                if(null != this.listener){
//                    this.listener.cancelled(this,this.downloaderObject);
//                }
//            }else{
//                if(null != this.listener){
//                    this.listener.failure(this,this.downloaderObject);
//                }
//            }
//        }finally {
//            if(null != input){
//                input.close();
//            }
//        }
    }
}
