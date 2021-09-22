package com.shellever.dexclassloader;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZipUtils {

    private static final String TAG = "DexClassLoader";

    public static void unzip(String zipFile, String targetDir) {
        Log.d(TAG, "unzip: zipFile="+zipFile);
        Log.d(TAG, "unzip: targetDir="+targetDir);
        int BUFFER = 4096; // 这里缓冲区我们使用4KB，
        String strEntry; // 保存每个zip的条目名称

        try {
            BufferedOutputStream dest = null; // 缓冲输出流
            FileInputStream      fis  = new FileInputStream(zipFile);
            ZipInputStream zis = new ZipInputStream(
                    new BufferedInputStream(fis));
            ZipEntry entry; // 每个zip条目的实例

            while ((entry = zis.getNextEntry()) != null) {
                Log.d(TAG, "unzip: ");

                try {
                    Log.i("Unzip: ", "=" + entry);
                    int count;
                    byte data[] = new byte[BUFFER];
                    strEntry = entry.getName();

                    if (entry.isDirectory()) {
                        File entryFile = new File(targetDir + File.separator
                                + strEntry);
                        if (!entryFile.exists()) {
                            entryFile.mkdirs();
                        }
                    } else {

                        FileOutputStream fos = new FileOutputStream(new File(
                                targetDir + File.separator + strEntry));
                        dest = new BufferedOutputStream(fos, BUFFER);
                        while ((count = zis.read(data)) != -1) {
                            dest.write(data, 0, count);
                        }
                        dest.flush();
                        dest.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            zis.close();
        } catch (Exception cwj) {
            cwj.printStackTrace();
        }
    }
}
