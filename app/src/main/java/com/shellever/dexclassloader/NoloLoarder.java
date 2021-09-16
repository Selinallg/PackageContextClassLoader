package com.shellever.dexclassloader;

import android.content.Context;
import android.os.Environment;

import java.io.File;

import dalvik.system.DexClassLoader;

public class NoloLoarder {

    public static DexClassLoader getClassLoader(Context mContext ,String paramString) {
        String dexPath = new File(getAppJarDir(mContext), paramString).getAbsolutePath(); // 经过dex转码后的jar包存放路径
        String optimizedDirectory = getAppDexDir(mContext).getAbsolutePath();   // dex优化文件存放路径
        String librarySearchPath = getAppLibDir(mContext).getAbsolutePath();    // 本地依赖库存放路径
        ClassLoader parent = mContext.getClassLoader();                          // 父类的类加载器
        return new DexClassLoader(dexPath, optimizedDirectory, librarySearchPath, parent);
    }

    // Android/data/<package-name>/driver/lib/
    public static File getAppLibDir(Context mContext) {
        File localFile = new File(getAppHomeDir(mContext), "driver/lib/");
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        return localFile;
    }

    // /storage/emulated/0/Android/data/com.shellever.dexclassloader/driver/dex
    public static File getAppDexDir(Context mContext) {
        File localFile = new File(getAppHomeDir(mContext), "driver/dex/");
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        return localFile;
    }

    // Android/data/<package-name>/driver/jar/
    public static File getAppJarDir(Context mContext) {
        File localFile = new File(getAppHomeDir(mContext), "driver/jar/");
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        return localFile;
    }

    public static File getAppHomeDir(Context mContext) {
        String packageName = mContext.getPackageName();
        File sdcardDir = Environment.getExternalStorageDirectory(); // /storage/emulated/0/
        String appHomeDir = sdcardDir + File.separator + "Android" + File.separator + "data" + File.separator + packageName + File.separator;
        File localFile = new File(appHomeDir);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        return localFile;
    }


    public static Class<?>[] getParamType(String paramString) {
        if ((paramString == null) || ("".equals(paramString))) {
            return null;
        }
        String[] paramStringArray = paramString.split(" ");
        Class[]  arrayOfClass     = new Class[paramStringArray.length];
        int      i                = 0;
        while (i < paramStringArray.length) {
            arrayOfClass[i] = getClassType(paramStringArray[i]);
            i += 1;
        }
        return arrayOfClass;
    }

    public static Class<?> getClassType(String paramString) {
        if ("Context".equals(paramString)) {
            return Context.class;
        }
        if ("String".equals(paramString)) {
            return String.class;
        }
        if ("Int".equals(paramString)) {
            return Integer.TYPE;
        }
        if ("StringArray".equals(paramString)) {
            return String[].class;
        }
        if ("ByteArray".equals(paramString)) {
            return byte[].class;
        }
        return null;
    }
}
