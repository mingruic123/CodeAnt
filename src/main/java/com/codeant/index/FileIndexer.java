package main.java.com.codeant.index;

import main.java.com.codeant.common.Item;

/**
 * Created by Mingrui on 8/22/2017.
 */
public class FileIndexer extends LuceneIndexer {
    public void addToIndex(Item item){
        if(!item.getStatus()){
            System.out.println("Item is not available. Please check again");
        }
        else{

        }
    }
}
