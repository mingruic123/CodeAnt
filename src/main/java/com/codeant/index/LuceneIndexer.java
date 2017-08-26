package main.java.com.codeant.index;

import main.java.com.codeant.common.Item;
import org.apache.lucene.index.IndexWriter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;

import java.nio.file.attribute.BasicFileAttributes;

/**
 * Created by Mingrui on 8/22/2017.
 */
public final class LuceneIndexer implements Indexer {
    private IndexWriter writer = null;
    private Directory indexDirectory = null;
    private IndexWriterConfig iwc = null;
    private String indexDirPath = "";


    public LuceneIndexer() {}


    public LuceneIndexer(Directory indexDirectory,  IndexWriterConfig iwc) {
        this.indexDirectory = indexDirectory;
        this.iwc = iwc;
        createIndexWriter();
    }

    private void createIndexWriter() {
        try {
            writer = new IndexWriter(indexDirectory, iwc);
        } catch (Exception e) {
            System.err.println("Failed to initialize indexWriter");
        }
    }

    /**
     * Indexes the given item using the given writer, or if item is a directory,
     * recurses over files and directories found under the given directory.
     *
     * @param item The item to be indexed
     */

    public void index(Item item) throws IOException{
        Path path = Paths.get(item.getAbsolutePath());

        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                    } catch (IOException ignore) {
                        // don't index files that can't be read.
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
        }
        item.setStatus(true);

    }

    static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {
        try (InputStream stream = Files.newInputStream(file)) {
            // make a new, empty document
            Document doc = new Document();

            // Add the path of the file as a field named "path".  Use a
            // field that is indexed (i.e. searchable), but don't tokenize
            // the field into separate words and don't index term frequency
            // or positional information:
            Field pathField = new StringField("path", file.toString(), Field.Store.YES);
            doc.add(pathField);

            // Add the last modified date of the file a field named "modified".
            // Use a LongPoint that is indexed (i.e. efficiently filterable with
            // PointRangeQuery).  This indexes to milli-second resolution, which
            // is often too fine.  You could instead create a number based on
            // year/month/day/hour/minutes/seconds, down the resolution you require.
            // For example the long value 2011021714 would mean
            // February 17, 2011, 2-3 PM.
            doc.add(new LongPoint("modified", lastModified));

            // Add the contents of the file to a field named "contents".  Specify a Reader,
            // so that the text of the file is tokenized and indexed, but not stored.
            // Note that FileReader expects the file to be in UTF-8 encoding.
            // If that's not the case searching for special characters will fail.
            doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))));

            if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                // New index, so we just add the document (no old document can be there):
                System.out.println("adding " + file);
                writer.addDocument(doc);
            } else {
                // Existing index (an old copy of this document may have been indexed) so
                // we use updateDocument instead to replace the old one matching the exact
                // path, if present:
                System.out.println("updating " + file);
                writer.updateDocument(new Term("path", file.toString()), doc);
            }
        }
    }

    public IndexWriter getIndexWriter(){
        return this.writer;
    }


}
