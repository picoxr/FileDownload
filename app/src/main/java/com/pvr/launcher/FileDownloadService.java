package com.pvr.launcher;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;

/**
 * Created by jeffreyliu on 16/11/5.
 */

public class FileDownloadService extends Service {
    private static  final  String TAG = "---DownloadService---";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"FileDownloadService   onCreate" + Process.myPid());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"FileDownloadService   onDestroy");
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"FileDownloadService   onStartCommand");
        return  START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"FileDownloadService   onBind");
        return mBind;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.i(TAG,"FileDownloadService   onUnbind");
        return super.onUnbind(intent);
    }
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG,"FileDownloadService   onRebind");
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG,"FileDownloadService   onLowMemory");
    }


    IFileDownloader.Stub mBind = new IFileDownloader.Stub() {
        @Override
        public boolean insertDownloadTask(String json){
            Log.e(TAG, "insertDownloadTask: " );
            Looper.prepare();
//            return fdm.insertDownloadTask(json);
            return FileDownloadManager.getInstance(FileDownloadService.this).insertDownloadTask(json);
        }

        @Override
        public int getProgress() {
            return FileDownloadManager.getInstance(FileDownloadService.this).getProgress();
        }

        @Override
        public String queryAllDownload(){
//            return FileDownloadManager.getInstance(FileDownloadService.this).queryAllDownload();
            return "";
        }

//        @Override
        public String  findDownloadTask(String mid,String itemid){
//            DownloadModel model = FileDownloadManager.getInstance(FileDownloadService.this).findDownloadTask(mid,itemid);
//            if(null != model){
//                JSONObject object = model.download2JSONObject();
//                return object.toString();
//            }else{
//                return null;
//            }
            return "";
        }
        public boolean existDownloadTask(String mid,String itemid){
//            return FileDownloadManager.getInstance(FileDownloadService.this).existDownloadTask(mid,itemid);
            return false;
        }

        @Override
        public boolean resumeDownloadTask(String mid,String itemid){
//            return FileDownloadManager.getInstance(FileDownloadService.this).resumeDownloadTask(mid,itemid);
            return false;
        }

        @Override
        public boolean pauseDownloadTask(String mid,String itemid){
//            return FileDownloadManager.getInstance(FileDownloadService.this).pauseDownloadTask(mid,itemid);
            return false;
        }

        @Override
        public boolean deleteDownloadTask(String mid,String itemid){
//            return FileDownloadManager.getInstance(FileDownloadService.this).deleteDownloadTask(mid,itemid);
            return false;
        }

        @Override
        public void deleteAllDownloadTask(){
//            FileDownloadManager.getInstance(FileDownloadService.this).deleteAllDownloadTask();
        }

        @Override
        public int downloadTaskState(String mid, String itemid)  {
//            DownloadModel model = FileDownloadManager.getInstance(FileDownloadService.this).findDownloadTask(mid,itemid);
//            if(null != model){
//                return model.getDownloadstate().getIndex();
//            }else{
//                return 0;
//            }
            return 0;
        }
        @Override
        public long downloadTaskTotalSize(String mid, String itemid){
//            DownloadModel model = FileDownloadManager.getInstance(FileDownloadService.this).findDownloadTask(mid,itemid);
//            if(null != model){
//                return model.getTotalsize();
//            }else{
//                return 0;
//            }
            return 0;
        }
        @Override
        public long downloadTaskCurrentSize(String mid, String itemid) {
//            DownloadModel model = FileDownloadManager.getInstance(FileDownloadService.this).findDownloadTask(mid,itemid);
//            if(null != model){
//                return model.getCurrentsize();
//            }else{
//                return 0;
//            }
            return 0;
        }
        @Override
        public long downloadTaskCurrentSpeed(String mid, String itemid) throws RemoteException {
//            DownloadModel model = FileDownloadManager.getInstance(FileDownloadService.this).findDownloadTask(mid,itemid);
//            if(null != model){
//                return model.getSpeed();
//            }else{
//                return 0;
//            }
            return 0;
        }

		@Override
		public void updateDownloadPlayTime(String mid, String itemid, long playTime, long updateTime) {
//				throws RemoteException {
//			Log.e("peiwen", "更新下载的播放时间----2");
//			DownloadModel model = FileDownloadManager.getInstance(FileDownloadService.this).findDownloadTask(mid,itemid);
//			if(null != model){
//				model.setPlaytime(playTime);
//				model.setUpdatetime(new Date(updateTime));
//				DatabaseManager.getInstance(FileDownloadService.this).updateDownload(model);
//			}
		}

		@Override
		public boolean deleteDownloadTaskUrl(String url) throws RemoteException {
//			return FileDownloadManager.getInstance(FileDownloadService.this).deleteDownloadTask(url);
            return false;
		}
    };
}
