package main.java.com.codeant.index;

import main.java.com.codeant.common.Item;

import java.io.IOException;

/**
 * Created by Mingrui on 8/24/2017.
 */
public interface Indexer {
    void index(Item item) throws IOException;
}
