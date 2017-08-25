package main.java.com.codeant.common; /**
 * Created by Mingrui on 8/22/2017.
 */

import java.io.File;
import java.util.List;

public class DirectoryItem extends Item {

    public DirectoryItem(String path){
        this.absolutePath = path;
    }

    private List<File> allFiles = null;
}
