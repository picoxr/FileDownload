package com.pvr.launcher;

import android.os.Environment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class DownloadModel {
    public static final String DEFAULT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "PicoVR/Download/Video";
    private String url;
    private String destFileDir;
    private String destFileName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDestFileDir() {
        return destFileDir;
    }

    public void setDestFileDir(String destFileDir) {
        this.destFileDir = destFileDir;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

    public String fileTempPath() {
        String fileName = DEFAULT_PATH + File.separator + getDestFileName() + ".tmp";
        return fileName;
    }

    public String fileRealPath() {
        String realName = DEFAULT_PATH + File.separator + getDestFileName() + ".mp4";
        return realName;
    }

    public JSONObject download2JSONObject() {
        JSONObject object = new JSONObject();
        try {
            object.put("url", this.url);
            object.put("destFileDir", this.destFileDir);
            object.put("destFileName", this.destFileName);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static DownloadModel downloadFromJSONObject(JSONObject object) {
        try {
            if (null != object) {
                DownloadModel model = new DownloadModel();
                model.url = object.getString("url");
                model.destFileDir = object.getString("destFileDir");
                model.destFileName = object.getString("destFileName");
                return model;
            } else {
                return null;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
