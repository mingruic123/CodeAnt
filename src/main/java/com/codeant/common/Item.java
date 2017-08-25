package main.java.com.codeant.common;

/**
 * Created by Mingrui on 8/22/2017.
 */
public abstract class Item{
    protected String absolutePath;
    private boolean indexed = false;
    protected ItemType type;

    public void setAbsolutePath(String absolutePath){
        this.absolutePath = absolutePath;
    }

    public String getAbsolutePath(){
        return this.absolutePath;
    }

    public void setStatus(boolean status){
        this.indexed = status;
    }

    public boolean getStatus(){
        return this.indexed;
    }

    public void setType(ItemType type){
        this.type = type;
    }
    public ItemType getType(){return type;}
}
