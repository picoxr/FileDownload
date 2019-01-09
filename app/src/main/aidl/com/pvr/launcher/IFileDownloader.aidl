// IFileDownloader.aidl
package com.pvr.launcher;

// Declare any non-default types here with import statements

interface IFileDownloader {
       boolean insertDownloadTask(String json);
       String  queryAllDownload();
       String  findDownloadTask(String mid,String itemid);
       boolean existDownloadTask(String mid,String itemid);
       boolean resumeDownloadTask(String mid,String itemid);
       boolean pauseDownloadTask(String mid,String itemid);
       void    deleteAllDownloadTask();
       boolean deleteDownloadTask(String mid,String itemid);
       boolean deleteDownloadTaskUrl(String url);
       int     downloadTaskState(String mid,String itemid);
       long    downloadTaskTotalSize(String mid,String itemid);
       long    downloadTaskCurrentSize(String mid,String itemid);
       long    downloadTaskCurrentSpeed(String mid,String itemid);
       void    updateDownloadPlayTime(String mid,String itemid,long playTime,long updateTime);
       int     getProgress();
}

