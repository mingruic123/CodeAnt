package main.java.com.codeant.common;

import java.io.File;
/**
 * Created by Mingrui on 8/24/2017.
 */
public class ItemFactory {
    public static Item getItem(String path){
        if(new File(path).isDirectory()){
            return new DirectoryItem(path);
        }

        if(new File(path).isFile()){
            return new FileItem(path);
        }

        return null;
    }
}
