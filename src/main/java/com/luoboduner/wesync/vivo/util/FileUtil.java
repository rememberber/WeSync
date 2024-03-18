package com.luoboduner.wesync.vivo.util;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liweiqing
 * @date 2023/5/4 0:31
 * @description
 */
public class FileUtil {

    public static List<File> readyAllFiel(String sourceDir) throws IOException {

        List<File> fileList =new ArrayList<>();
        readyAllFiel(sourceDir,fileList);
        return fileList;
    }
    public static List<File> readyAllFiel(String sourceDir,String filterSuffix) throws IOException {

        List<File> fileList =new ArrayList<>();
        List<File> fileListResult =new ArrayList<>();
        readyAllFiel(sourceDir,fileList);
        if (StringUtils.isNotEmpty(filterSuffix)) {
            for (File file : fileList) {
                if(file.getName().toLowerCase().lastIndexOf("."+filterSuffix.toLowerCase())!=-1){
                    fileListResult.add(file);
                }
            }
        }
        return fileListResult;
    }

    private static void readyAllFiel(String sourceDir, List<File> fileList) throws IOException {
        File[] file = (new File(sourceDir)).listFiles();

        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                //System.out.println(file[i].getPath());
                if(file[i].getPath().lastIndexOf(".db")!=-1){
                    continue;
                }
                fileList.add(file[i]);
            }
            if (file[i].isDirectory()) {
                //当前文件夹路径，继续读取下一层
                String forderPath = file[i].getPath();
                readyAllFiel(forderPath,fileList);
            }
        }
    }

}
