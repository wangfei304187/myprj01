package com.demo.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * <pre>
 * Lucene创建索引服务类
 * </pre>
 *
 * @author nicky
 * @version 1.00.00
 */
public class LuceneIndexer
{

    private volatile static LuceneIndexer instance;

    private final static String INDEX_DIR = "xxx.index";

    private static class SingletonHolder
    {
        private final static LuceneIndexer instance = new LuceneIndexer();
    }

    public static LuceneIndexer getInstance()
    {
        return SingletonHolder.instance;
    }

    public boolean createIndex(String indexDir) throws IOException
    {
        // 加点测试的静态数据
        Integer ids[] = { 1, 2, 3 };
        String titles[] = { "标题3", "标题2", "谷歌地图之父跳槽facebook" };
        String tcontents[] = {
                "内容1内容啊哈哈哈A",
                "内容2内容啊哈哈哈B",
                "啊哈哈哈C"
        };

        long startTime = System.currentTimeMillis();// 记录索引开始时间

        // Analyzer analyzer = new SmartChineseAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        Directory directory = FSDirectory.open(Paths.get(indexDir));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(OpenMode.CREATE);

        IndexWriter indexWriter = new IndexWriter(directory, config);

        for (int i = 0; i < ids.length; i++)
        {
            Document doc = new Document();
            // 添加字段
            // doc.add(new IntField("id", ids[i], Field.Store.YES)); //添加内容 // 5.3.1
            doc.add(new IntPoint("id", ids[i])); // 添加内容 // 8.8.1
            doc.add(new TextField("title", titles[i], Field.Store.YES)); // 添加文件名，并把这个字段存到索引文件里
            doc.add(new TextField("tcontent", tcontents[i], Field.Store.YES)); // 添加文件路径
            indexWriter.addDocument(doc);
            System.out.println("add doc " + i);
        }

        indexWriter.commit();
        // System.out.println("共索引了" + indexWriter.numDocs() + "个文件");
        // System.out.println("共索引了" + indexWriter.getMaxCompletedSequenceNumber() + "个文件");
        indexWriter.close();
        System.out.println("创建索引所用时间：" + (System.currentTimeMillis() - startTime) + "毫秒");

        return true;
    }

    public static void main(String[] args)
    {
        try
        {
            boolean r = LuceneIndexer.getInstance().createIndex(LuceneIndexer.INDEX_DIR);
            if (r)
            {
                System.out.println("索引创建成功!");
            }
            else
            {
                System.out.println("索引创建失败!");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

}