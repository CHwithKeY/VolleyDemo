package com.oji.kreate.vsf.publicClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;

import com.oji.kreate.vsf.base.BaseHttpAction;
import com.oji.kreate.vsf.http.HttpAction;
import com.oji.kreate.vsf.http.HttpHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by spilkaka on 2018/4/12.
 */

public class VersionAction extends BaseHttpAction {

    private String httpKey = "version";
    private String httpTag = "version_get";
    private String httpResultDescription = "description";

    private DownloadThread thread;

    public VersionAction(Context context) {
        super(context);
    }

    public void update() {
        String version_url = Methods.getSpecificProperty(context, "version_url");

        String version = Methods.getVersionName(context);

        String[] key = {httpKey};
        String[] value = {version};

        HttpAction action = new HttpAction(context);
        action.setUrl(version_url);
        action.setMap(key, value);
        action.setDialog("正在获取新版本，请稍后……");
        action.setHandler(new HttpHandler(context));
        action.setTag(httpTag);
        action.interaction();
    }

    public String getHttpKey() {
        return httpKey;
    }

    public String getHttpTag() {
        return httpTag;
    }

    public void handleWithDescriptionResponse(String result, boolean showSnack) throws JSONException {
        JSONObject obj = new JSONObject(result);
//        String description = obj.getString(HttpParams.RESULT_RESULT);
        String description = obj.getString(httpResultDescription);

        if (!description.isEmpty()) {
            String[] description_arr = description.split("-");

            StringBuilder builder = new StringBuilder("");
            for (String aDescription_arr : description_arr) {
                builder.append(aDescription_arr).append("\n\n");
            }

            builder.append("检测到新版本，请问是否更新？");
            String description_msg = builder.toString();

            new AlertDialog.Builder(context)
                    .setTitle("版本更新")
//                    .setMessage("检查到有新版本，是否更新？")
                    .setMessage(description_msg)
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            downLoadApk();
                        }
                    })
                    .setNegativeButton("否", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        } else {
            if (showSnack) {
                showSnack("当前已经是最新版本");
//                toast.showToast(context.getString(R.string.update_toast_is_Latest_version));
            }
        }
    }

    public void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        pd.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialog != null) {
                    dialog.cancel();
                    dialog.dismiss();

                    if (thread != null && !thread.isInterrupted()) {
//                        Log.i("Result", "thread interrupt");
                        thread.interrupt();
//                        thread = null;
                    }
                }
            }
        });

        pd.show();

        thread = new DownloadThread(pd);
        thread.start();
    }

    private class DownloadThread extends Thread {
        private ProgressDialog pd;

        DownloadThread(ProgressDialog pd) {
            this.pd = pd;
        }

        @Override
        public void run() {
            try {
//                File file = getFileFromServer(HttpSet.PUBLIC_URL + HttpSet.URL_VERSION, pd);

                String apk_url = Methods.getSpecificProperty(context, "apk_url");
                File file = getApkFromServer(apk_url, pd);

//                sleep(1500);
                installApk(file);
                pd.dismiss(); //结束掉进度条对话框
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public File getApkFromServer(String path, ProgressDialog pd) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA);
        String date = format.format(new Date());

        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdcard_dir = Environment.getExternalStorageDirectory();
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

            String app_name = Methods.getAppName(context);
            String file_path = sdcard_dir.getPath() + "/" + app_name;
            File app_dir = new File(file_path);
//            Log.i("Result", "file");

            if (!app_dir.exists()) {
                app_dir.mkdir();
//                Log.i("Result", "mkdir");
            }

            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();

            String apk_path = file_path + "/";
            File apk_file = new File(apk_path);

            if (!apk_file.exists()) {
                apk_file.createNewFile();
            }
            String apk_name = app_name + date + ".apk";

            File file = new File(apk_file, apk_name);
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

}
