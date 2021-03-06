package com.quickdone.znwh.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhum
 * @Date: 2018/7/12 15:37
 * @Description:
 */
public class GetFilePath {
    /**
     * 获取路径下的所有文件/文件夹
     * @param directoryPath 需要遍历的文件夹路径
     * @return
     */
    public static List<String> getAllFile(String directoryPath) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
//                if(isAddDirectory){
//                    list.add(file.getAbsolutePath());
//                }
                list.addAll(getAllFile(file.getAbsolutePath()));
            } else {
                list.add(file.getAbsolutePath());
            }
        }
        return list;
    }

    public static void main(String[] args){
        List<String> list = getAllFile("D:\\xml\\");
        for (String path:list) {
            System.out.println(path);
        }
    }
}
