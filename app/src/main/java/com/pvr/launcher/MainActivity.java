package com.pvr.launcher;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.unity3d.player.UnityPlayerNativeActivityPico;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends UnityPlayerNativeActivityPico {
    private static final String TAG = "---MainActivity---";
    private IFileDownloader mRemoteService;

    Timer timer;
    TimerTask timerTask;
    Button button;

//    public static Activity unityActivity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        unityActivity = this;

        button = findViewById(R.id.start);
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                Log.e(TAG, "run: " + getProgress());
            }
        };

        //绑定远程服务
        bindRemoteService();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                download("http://static.pvrvideo.com/BadoinkvrT/BaDoinkVR_A_Bumpy_Ride_oculus_180_180x180_3dh_LR_120S_UD_3D180_LR.mp4", Environment.getExternalStorageDirectory().getAbsolutePath(), "video1224.mp4");
                timer.schedule(timerTask,  1000, 1000);
            }
        });
    }

    //获取进度接口
    public int getProgress() {
        int p = 0;
        try {
            p = mRemoteService.getProgress();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Log.e(TAG, "getProcess: " +  p);
        return p;
    }

    //下载方法
    public boolean download(final String url, final String destFileDir, final String destFileName) {
        DownloadModel model = new DownloadModel();
        model.setUrl(url);
        model.setDestFileDir(destFileDir);
        model.setDestFileName(destFileName);

        try {
            if (null != mRemoteService) {
                JSONObject object = model.download2JSONObject();
                if (null != object) {
                    Log.e(TAG, "object = " + object.toString());
                    return mRemoteService.insertDownloadTask(object.toString());
                } else {
                    Log.e(TAG, "failed to insert task, object is null");
                    return false;
                }
            } else {
                Log.e(TAG, "failed to insert task, service is null");
                return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
    // 监控远程服务状态链接，ServiceConnection
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemoteService = IFileDownloader.Stub.asInterface(service);// 链接上了才会调用
            Log.e(TAG, "[Client] onServiceConnected  ");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 正常顺序默认不调用意外情况销毁调用
            Log.e(TAG, "[Client] onServiceDisconnected");
            mRemoteService = null;
            bindRemoteService();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindRemoteService();
    }


    // 绑定远程服务
    private void bindRemoteService() {
        Log.e(TAG, "bindRemoteService" + "start");
        Intent intent = new Intent();
        intent.setAction("com.picovr.file.download");
        intent.setPackage(getPackageName());// Android5.0不允许直接隐式启动，需要加包名
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        Log.e(TAG, "bindRemoteService: " + "end" );
    }

    // 解除绑定
    private void unbindRemoteService() {
        Log.i(TAG, "[Client] unbindRemoteService ==>");
        unbindService(mConnection);
        mRemoteService = null;
    }
}
