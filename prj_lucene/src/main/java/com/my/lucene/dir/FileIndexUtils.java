package com.my.lucene.dir;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class FileIndexUtils {
    
    static int index = 0;
    
    static File dir = new File("D:\\BowingSVN\\branches\\Parameters\\src\\com");
    
    private static Directory directory = null;
    static {
        try {
            directory = FSDirectory.open(Paths.get("db.index"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Directory getDirectory() {
        return directory;
    }

    public static void createIndex(boolean hasNew) {
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory, new IndexWriterConfig(new StandardAnalyzer()));
            if (hasNew) {
                writer.deleteAll();
            }

//            Document document = null;
//            int index = 0;
//            // 随机数，为排序数字
//            Random random = new Random();
//            // 为源文件创建索引
//            File file = new File("D:\\BowingSVN");
//            for (File f : file.listFiles()) {
//                int score = random.nextInt();
//                document = new Document();
//                document.add(new Field("id", String.valueOf(index++), Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
//                document.add(new Field("fileName", f.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
//                document.add(new Field("path", f.getPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
//                document.add(new Field("content", new FileReader(f)));
//                // document.add(new IntField("score", score, Field.Store.YES));
//                document.add(new LongField("size", f.length(), Field.Store.YES));
//                document.add(new IntField("date", (int) f.lastModified(), Field.Store.YES));
//                
//                document.add(new NumericDocValuesField("size", (long) f.length()));
//                
//                writer.addDocument(document);
//            }
            
            long lo1 = System.currentTimeMillis();
            createDocuments(dir, writer);
            System.out.println("create docs, time=" + (System.currentTimeMillis() - lo1) / 1000 + "s");
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    private static void createDocuments(File dir, IndexWriter writer) {
        // 为源文件创建索引
        Document doc;
        for (File f : dir.listFiles()) {
            if (f.isDirectory())
            {
                createDocuments(f, writer);
            }
            else
            {
                try {
                    doc = new Document();
                    doc.add(new StringField("id", String.valueOf(index++), Field.Store.YES));
                    doc.add(new TextField("fileName", f.getName(), Field.Store.YES));
                    doc.add(new TextField("path", f.getPath(), Field.Store.YES));
                    doc.add(new TextField("content", new FileReader(f)));
//                    doc.add(new LongField("size", f.length(), Field.Store.YES));
//                    doc.add(new IntField("date", (int) f.lastModified(), Field.Store.YES));
                    doc.add(new LongPoint("size", f.length()));
                    doc.add(new IntPoint("date", (int) f.lastModified()));
                    
                    doc.add(new NumericDocValuesField("size", (long) f.length()));
                    
                    writer.addDocument(doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
               
            }
        }
    }
}