package main.java.com.codeant.index;

import main.java.com.codeant.common.FileItem;
import main.java.com.codeant.common.Item;
import main.java.com.codeant.common.ItemFactory;
import main.java.com.codeant.common.ItemType;

import java.io.IOException;

/**
 * Created by Mingrui on 8/24/2017.
 */
public class IndexTask {
    private IndexService iService;

    public IndexTask(IndexService service){
        this.iService = service;
    }

    public void indexItem(String docsPath) throws IOException {
        Item item = ItemFactory.getItem(docsPath);
        iService.getLuceneIndexer().index(item);
    }
}
