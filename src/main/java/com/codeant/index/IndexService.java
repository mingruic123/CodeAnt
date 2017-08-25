package main.java.com.codeant.index;

import main.java.com.codeant.common.Item;
import main.java.com.codeant.common.ItemFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Mingrui on 8/24/2017.
 */
public final class IndexService {
    private String indexPath;
    private Directory dir;
    private Analyzer analyzer;
    private IndexWriterConfig iwc;
    private LuceneIndexer luceneIndexer;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private static String DEFAULT_INDEX_DIR = "index";

    public IndexService(String indexPath, String docsPath){
        this.indexPath = indexPath.length() != 0 ? indexPath : DEFAULT_INDEX_DIR;
        init();
    }

    private void init(){
        if(initialized.equals(false)) {
            try {
                dir = FSDirectory.open(Paths.get(this.indexPath));
                analyzer = new StandardAnalyzer();
                iwc = new IndexWriterConfig(analyzer);
                initialized.set(true);
            }catch(Exception e){
                System.err.println("Initialization failed");
            }
        }

    }

    public void start() {
        if(initialized.equals(true)){
            luceneIndexer = new LuceneIndexer(dir, analyzer, iwc);
        }else{
            System.err.println("Index is not initialized!");
        }
    }

    public LuceneIndexer getLuceneIndexer(){
        return this.luceneIndexer;
    }

    public void stop() throws IOException{
        luceneIndexer.getIndexWriter().close();
    }

}
