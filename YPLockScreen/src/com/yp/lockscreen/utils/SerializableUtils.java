package com.yp.lockscreen.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
public class SerializableUtils {

    public static void inputSerializableFile(Object object,String SerializableFileName,Context context) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(context.getFilesDir().getPath()+ File.separator + SerializableFileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.flush();
                oos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void inputSerializableFile(String path,Object object,String SerializableFileName) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(path + File.separator + SerializableFileName);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                oos.flush();
                oos.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public static void delSerializableFile(String SerializableFileName,Context context){
        File f = new File(context.getFilesDir()+File.separator+SerializableFileName);
        if(f!=null && f.exists()){
            f.delete();
        }
        f=null;
    }
    
    public static void delSerializableFile(String path,String SerializableFileName){
        File f = new File(path + File.separator+SerializableFileName);
        if(f!=null && f.exists()){
            f.delete();
        }
        f=null;
    }
    
    public static Object readSerializableFile(String SerializableFileName,Context context) {
        FileInputStream fis=null;
        ObjectInputStream oin =null;
        Object obj=null;
        try {
            long start = System.currentTimeMillis();
            fis = new FileInputStream(context.getFilesDir()+File.separator+SerializableFileName);
            oin = new ObjectInputStream(fis);
            obj=oin.readObject();
//            LogHelper.d("FlashLight", "readSerializableFile 时间:"+(System.currentTimeMillis() - start));
//            Toast.makeText(FlashlightApp.getAppContext(), "readSerializableFile 时间:"+(System.currentTimeMillis() - start), Toast.LENGTH_LONG).show();
            return obj;
        } catch (Exception ex) {
//            ex.printStackTrace();
            return null;
        }finally{
            try{
                if(fis!=null)
                    fis.close();
                if(oin!=null)
                    oin.close();
                
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static Object readSerializableFile(String path,String SerializableFileName) {
        FileInputStream fis=null;
        ObjectInputStream oin =null;
        Object obj=null;
        try {
            long start = System.currentTimeMillis();
            fis = new FileInputStream(path+File.separator+SerializableFileName);
            oin = new ObjectInputStream(fis);
            obj=oin.readObject();
//            LogHelper.d("FlashLight", "readSerializableFile 时间:"+(System.currentTimeMillis() - start));
//            Toast.makeText(FlashlightApp.getAppContext(), "readSerializableFile 时间:"+(System.currentTimeMillis() - start), Toast.LENGTH_LONG).show();
            return obj;
        } catch (Exception ex) {
//            ex.printStackTrace();
            return null;
        }finally{
            try{
                if(fis!=null)
                    fis.close();
                if(oin!=null)
                    oin.close();
                
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
