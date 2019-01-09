package com.pvr.launcher;

/**
 * Created by jeffreyliu on 16/11/8.
 */

public interface IFileDownloaderListerer {
    void started(FileDownloader downloader, Object downloadObject, long totalsize);
    void byteReaded(FileDownloader downloader, Object downloadObject, byte[] bytes, int length);
    void failure(FileDownloader downloader, Object downloadObject);
    void cancelled(FileDownloader downloader, Object downloadObject);
    void completed(FileDownloader downloader, Object downloadObject);
}
