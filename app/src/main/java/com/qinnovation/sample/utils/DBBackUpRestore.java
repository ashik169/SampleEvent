package com.qinnovation.sample.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import com.qinnovation.sample.db.DBHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DBBackUpRestore {

    private static final String TAG = DBBackUpRestore.class.getSimpleName();

    /**
     * TODO remove this while going LIVE
     * to backup in local storage
     * which should not use in production
     */
    public static void backupDb(Context context) {
        try {
            final String inFileName = context.getDatabasePath(DBHelper.DB_NAME).getAbsolutePath();
            File dbFile = new File(inFileName);
            FileInputStream stream = new FileInputStream(dbFile);

            String outFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + File.separator + DBHelper.DB_NAME;

            File outFile = new File(outFileName);
            if (!outFile.exists())
                outFile.createNewFile();

            OutputStream outputStream = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = stream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            stream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeFile(long fileName, byte[] bytes, int quality, String extension) {
        try {
            String outFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                    + File.separator + "image_" + quality + fileName + extension;
            FileOutputStream fos = new FileOutputStream(outFileName);
            fos.write(bytes);
            fos.close();
        } catch (IOException e) {
            Log.d(TAG, "####e = " + e);
            e.printStackTrace();
        }
    }

    public static void writeFile(String fileName, Bitmap bitmap, String extension) {
        try {
            String outFileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
                    + File.separator + "image_" + fileName + extension;

            FileOutputStream outStream = new FileOutputStream(outFileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (IOException e) {
            Log.d(TAG, "####writeFile = " + e);
            e.printStackTrace();
        }
    }

    /*public static void restoreDb() {
        try {
            Context context = AppClass.getAppContext();

//          sourcePath
            String sourcePath = Environment.getExternalStorageDirectory() + context.getString(R.string.local_db_storage);
            System.out.println("####restoreDb.SourcePath = " + sourcePath);
            File srcFile = new File(sourcePath);
            FileInputStream fis = new FileInputStream(srcFile);

//          destpath
            final String dest = context.getDatabasePath(context.getString(R.string.dbName)).getAbsolutePath();
            File destFile = new File(dest);
            OutputStream outputStream = new FileOutputStream(destFile);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            fis.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
