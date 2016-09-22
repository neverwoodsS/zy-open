package com.zy.open.lib.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 全局异常捕获
 *
 * @author yanghai
 *         该类将发生异常的日志信息写在本地目录中,
 *         使用时,需要在Application中进行初始化的配置
 *         <p>
 *         ExceptionHandler catchHandler = ExceptionHandler.getInstance();
 *         catchHandler.init(getApplicationContext());
 *         catchHandler.collectDeviceInfo(getApplicationContext());
 *         catchHandler.getSDPath();
 *         </p>
 */
public class ExceptionHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";
    // 系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static ExceptionHandler instance;
    // 程序的Context对象
    private Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    private String fileDir = "";


    private ExceptionHandler() {

    }

    public static ExceptionHandler getInstance() {
        if (instance == null)
            instance = new ExceptionHandler();
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        mContext = context;
        fileDir = getSDPath();
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 收集设备参数信息
        collectDeviceInfo(mContext);

        // 使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                // Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出." + getSDPath(),
                // Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        // 保存日志文件
        saveCatchInfo2File(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        Log.i(TAG, "收集设备信息");
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(),
                    PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = ;
                infos.put("versionName", versionName);
                infos.put("versionCode", String.valueOf(pi.versionCode));
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCatchInfo2File(Throwable ex) {
        OutputStream fos = null;
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        try {
            String fileName = "crash-" + new Date().getTime() + ".txt";
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                String path = fileDir + "/exception/";

                File dir = new File(path);
                if (!dir.exists()) {
                    // 创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
                    dir.mkdirs();

                }
                String fileAddress = path + fileName;
                File exFile = new File(fileAddress);
                if (exFile.exists()) {
                    exFile.createNewFile();
                }
                fos = new FileOutputStream(exFile);
                fos.write(sb.toString().getBytes());
                // 发送给开发人员
                sendCrashLog2PM(path + fileName);
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);

        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 将捕获的导致崩溃的错误信息发送给开发人员
     * <p>
     * 目前只将log日志保存在sdcard 和输出到LogCat中，并未发送给后台。
     */
    private void sendCrashLog2PM(String fileName) {
        if (!new File(fileName).exists()) {
            Toast.makeText(mContext, "日志文件不存在！", Toast.LENGTH_SHORT).show();
            return;
        }
        FileInputStream fis = null;
        BufferedReader reader = null;
        String s = null;
        try {
            fis = new FileInputStream(fileName);
            reader = new BufferedReader(new InputStreamReader(fis, "GBK"));
            while (true) {
                s = reader.readLine();
                if (s == null)
                    break;
                // 由于目前尚未确定以何种方式发送，所以先打出log日志。
                Log.i("info", s.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally { // 取消流
            try {
                reader.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果需要自定义文件存放目录,调用该方法
     * 默认情况为sd根目录
     *
     * @param fileDir
     */
    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }

    // 获得sd卡的根目录
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        // 判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录
        }
        return sdDir.toString();
    }
}