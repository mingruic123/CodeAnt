package main.java.com.codeant.common;

/**
 * Created by Mingrui on 8/22/2017.
 */
public class FileItem extends Item {
    private String fileType;

    public FileItem(String path){
        this.absolutePath = path;
    }

    public void setFileType(String fileType){
        this.fileType = fileType;
    }




}
