package com.example.jinhui.learnbasequickadapter.base.utils;

import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhusr on 2017/7/11.
 */

public class FileUtils {

    /**
     * 生成文件（之前需要先生成文件夹）
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 生成文件夹
     *
     * @param filePath
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }

    /**
     * 获取指定目录下文件夹中文件list
     *
     * @param path
     * @return
     */
    public static List<String> getFileList(String path) {
        List<String> list = new ArrayList<>();
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                list.add(file.getName());
            }
        }
        return list;
    }

    /**
     * 判断文件夹中是否存在文件
     *
     * @param path     文件夹路径
     * @param fileName 文件名
     * @return
     */
    public static Boolean ifFileListExitsFile(String path, String fileName) {
        Boolean flag = false;
        File[] files = new File(path).listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (fileName.equals(file.getName())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }
}
