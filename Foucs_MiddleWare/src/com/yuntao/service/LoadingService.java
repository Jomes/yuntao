package com.yuntao.service;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.sohu.focus.framework.util.LogUtils;
import com.yuntao.Constants;
import com.yuntao.widget.FileUtils;
import com.yuntao.widget.HttpDownloader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by jomeslu on 15-8-24.
 */
public class LoadingService extends IntentService {
    private final int SUCCESS_GET_CONTACT = 1;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LoadingService(String name) {
        super(name);
    }

    public LoadingService() {
        super("LoadingService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final String urls = intent.getStringExtra("Intent_url");
        LogUtils.i(urls+"_________");
        //子线程通过Message对象封装信息，并且用初始化好的，
        //Handler对象的sendMessage()方法把数据发送到主线程中，从而达到更新UI主线程的目的
        FileUtils files = new FileUtils();
        try {
            URL url = new URL(urls);
            InputStream is = null;
            is = url.openStream();
            Bitmap  bitmap = BitmapFactory.decodeStream(is);
            saveFile(bitmap, files.fileName,files.getPath());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


//    private Handler mHandler=new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//            if(msg.what==)
//
//
//
//        }
//    };

    // 指定保存的路径：
    public void saveFile(Bitmap bm, String fileName, String path) throws IOException {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }


}
