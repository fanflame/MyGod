package com.fanyiran.mygod.utils;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

/**
 * Created by fanqiang on 2019-05-14.
 */
public class FileUtils {
    public static boolean saveBeanToJson(Object object, String filePath, String fileName) {
        String json = new Gson().toJson(object);
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File temp = new File(filePath, fileName);
        if (!temp.exists()) {
            try {
                temp.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Writer writer = new FileWriter(new File(filePath + File.separator + fileName));
            writer.write(json);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static Object getBeanFromJson(Class object, String filePath, String fileName) {
        File file = new File(filePath, fileName);
        if (!file.exists()) {
            return null;
        }
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[2048];
            int readNum = 0;
            StringBuilder builder = new StringBuilder();
            while ((readNum = reader.read(chars)) != -1) {
                builder.append(new String(chars, 0, readNum));
            }
            return new Gson().fromJson(builder.toString(), object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean saveToFile(InputStream byteStream, String savePath, String saveName) {
        if (byteStream == null || savePath == null)
            return false;
        byte[] bytes = new byte[2048];
        File filePah = new File(savePath);
        if (!filePah.exists()) {
            if (!filePah.mkdirs()) {
                return false;
            }
        }
        File file = new File(savePath, saveName);
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    return false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        int readCount;
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            while ((readCount = byteStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, readCount);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static boolean clearPath(String path){
        if (path == null) {
            return false;
        }
        File file = new File(path);
        if (file.exists()) {
            return false;
        }
        if (file.exists() && file.isFile()) {
            return file.delete();
        }
        File[] files = file.listFiles();
        if (file.length() == 0) {
            return true;
        }
        for (File file1 : files) {
            clearPath(path);
        }
        return true;
    }
}
