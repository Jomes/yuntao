package com.yuntao.widget;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by jomeslu on 15-8-24.
 */
public class FileUtils {

    private String SDPATH;
    public String fileName="yyyt.jpg";

    public String getSDPATH() {
        return SDPATH;
    }

    public FileUtils() {
        //得到当前外部存储设备的目录
        SDPATH = Environment.getExternalStorageDirectory() + "/mypic_data/";
    }

    public String getPath(){

        return SDPATH;
    }


    /**
     * 在SD卡上创建文件
     *
     * @throws IOException
     */
    public File creatSDFile(String fileName) throws IOException {

        File file = new File(SDPATH + fileName);
        file.createNewFile();
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(SDPATH + dirName);
        dir.mkdir();
        return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public boolean isFileExist(String fileName) {

        File file = new File(SDPATH + fileName);
//        file.delete();
        return file.exists();

    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public File writeToSDFromInput(String path, String fileName, InputStream input) {

        File file = null;
        OutputStream output = null;
        try {
            creatSDDir(path);
            file = creatSDFile(path + fileName);
            output = new FileOutputStream(file);
            byte buffer[] = new byte[1024];
            int len = 0;
            //如果下载成功就开往SD卡里些数据
            while ((len = input.read(buffer)) != -1) {
                output.write(buffer, 0, len);
            }
            output.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                input.close();
                output.close();
            } catch (Exception e) {

                e.printStackTrace();
            }
        }
        return file;
    }


    public Bitmap getDiskBitmap(String filename)
    {
        Bitmap bitmap = null;
        try
        {
            File file = new File(SDPATH+filename);
            if(file.exists())
            {
                bitmap = BitmapFactory.decodeFile(SDPATH+filename);
            }
        } catch (Exception e)
        {
            // TODO: handle exception
        }


        return bitmap;
    }
}
