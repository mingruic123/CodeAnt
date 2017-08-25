package main.java.com.codeant.index;

import java.io.Serializable;

/**
 * Created by Mingrui on 8/22/2017.
 */
public class Index implements Serializable {
    private String name;

    private Index(){}

    public Index(String name){
         this.name = name.intern();
    }

    public String getName(){
         return this.name;
    }




}
