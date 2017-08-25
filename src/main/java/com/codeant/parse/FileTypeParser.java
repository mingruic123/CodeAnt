package main.java.com.codeant.parse;

/**
 * Created by Mingrui on 8/25/2017.
 */
public class FileTypeParser {
    public static String getFileType(String path){
        if(path.lastIndexOf(".") != -1){
            return path.substring(path.lastIndexOf("."));
        }else{
            return null;
        }
    }
}
