package com.siwind.util;

import java.io.File;

/**
 * Created by admin on 2017/3/5.
 */
public class FileUtil {

    public static String curDir = "";
    static {
        curDir = System.getProperty("user.dir");
        if( curDir.endsWith("/") && curDir.endsWith("\\") ){ //make sure it ends with slash "/"
            curDir += File.separatorChar;
        }
    }

    /**
     * D:\test.java will return {"D:\test", "java"}
     *
     * @param filePath
     * @return
     */
    public static String[] getFilePathPrefixSufix(String filePath){
        String[] prefix = new String[3];

        int index = filePath.lastIndexOf('.');
        if( index>0 ) {
            prefix[0] = filePath.substring(0, index );  // not include char of '.'
            prefix[1] = filePath.substring(index+1,filePath.length());
            prefix[2] = ".";
        } else{
            prefix[0] = filePath;
            prefix[1] = "";
            prefix[2] = "";
        }
        return prefix;
    }

    /**
     *
     * @param path
     * @return
     */
    public static String getAbsoluteFilePath(String path){
        String filePath = "";
        path = path.trim(); //
        if (path.startsWith("/") || path.indexOf(":") == 1){
            filePath = path;
        } else {
            filePath = curDir + path;
        }
            return filePath;
    }
}
