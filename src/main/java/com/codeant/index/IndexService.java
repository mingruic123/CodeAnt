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
 *
 * An index Service is to create a service for indexing. Once service is started, users can index documents.
 * It is created as a Singleton in order to guarantee only one index service is running.
 *
 */
public final class IndexService {
    private String indexPath;
    private Directory dir;
    private Analyzer analyzer;
    private IndexWriterConfig iwc;
    private LuceneIndexer luceneIndexer;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private AtomicBoolean serviceStatus = new AtomicBoolean(false);
    private static String DEFAULT_INDEX_DIR = "index";

    private static IndexService serviceInstance = null;
    private IndexService(String indexPath){
        this.indexPath = indexPath.length() != 0 ? indexPath : DEFAULT_INDEX_DIR;
        init();
    }

    /**
     * A singleton method to get instance of index service.
     * @param indexPath The directory where index is saved
     */
    public static IndexService getIndexServiceInstance(String indexPath){
        if(serviceInstance == null){
            serviceInstance = new IndexService(indexPath);
        }
        return serviceInstance;
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
            luceneIndexer = new LuceneIndexer(dir, iwc);
            serviceStatus.set(true);
        }else{
            System.err.println("Index is not initialized!");
        }
    }

    public LuceneIndexer getLuceneIndexer(){
        return this.luceneIndexer;
    }

    public Analyzer getIndexAnalyzer(){
        return this.analyzer;
    }

    public boolean isStarted(){
        return serviceStatus.equals(true);
    }

    public boolean isStopped(){
        return serviceStatus.equals(false);
    }

    public void stop() throws IOException{
        luceneIndexer.getIndexWriter().close();
    }

}
