# FileDownload Instructions

# Introduce:

Demo use the okHttp framework to perform file downloads in a separate process Service

## Method:

```
public boolean download(final String url, final String destFileDir, final String destFileName)
```

Return Value:Service returns true if it is not empty and RemoteException does not occur, otherwise it returns false

| String                   | Remark            |
| ------------------------ | ----------------- |
| url                      | download link     |
| destFileDir              | download folder   |
| destFileName             | Filename          |
| public int getProgress() |                   |
| return value             | download progress |

## Use Note:

1.Copy the android engineering app/libs okhttp-3.4.2.jar and okio-1.9.0.jar into Unity

2.Add permissions into AndroidManifest

```
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
<uses-permission android:name="android.permission.WAKE_LOCK"/>

```

Register service in AndroidManifest

```
<service android:name="com.pvr.launcher.FileDownloadService"     
   android:process=":filedownload">
      <intent-filter>
        <action android:name="com.picovr.file.download" />
      </intent-filter>
</service>

```

