package com.imdoon.daemonguard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.content.Context;

public class AssetFileUtils {

//    public static void copyAssetDirToFiles(Context context, String dirname)
//        throws IOException {
//        File dir = new File(context.getFilesDir() + "/" + dirname);
//        dir.mkdir();
//
//        AssetManager assetManager = context.getAssets();
//        String[] children = assetManager.list(dirname);
//        for (String child : children) {
//            child = dirname + '/' + child;
//            String[] grandChildren = assetManager.list(child);
//            if (0 == grandChildren.length)
//                copyAssetFileToFiles(context, child);
//            else
//                copyAssetDirToFiles(context, child);
//        }
//    }

    public static void copyAssetFileToFiles(Context context, String filename)
        throws IOException {

        File of = new File(context.getFilesDir() + "/" + filename);
        if (!of.exists()) {
            InputStream is = context.getAssets().open(filename);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            of.createNewFile();
//            of.setExecutable(true);
            FileOutputStream os = new FileOutputStream(of);
            os.write(buffer);
            os.close();
        }
        Runtime.getRuntime().exec("chmod 777 " + of.getCanonicalPath());
    }
}
