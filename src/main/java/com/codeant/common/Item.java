package main.java.com.codeant.common;

/**
 * Created by Mingrui on 8/22/2017.
 */
public abstract class Item{
    protected String absolutePath;
    private boolean status = false;
    private ItemType type;

    public void setAbsolutePath(String absolutePath){
        this.absolutePath = absolutePath;
    }

    public String getAbsolutePath(){
        return this.absolutePath;
    }

    public void setStatus(boolean status){
        this.status = status;
    }

    public boolean getStatus(){
        return this.status;
    }

    public void setType(ItemType type){
        this.type = type;
    }
}
