package com.my.lucene.sep;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.SortedDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class CustomSeparatorTest {

    private Path pp = Paths.get("sep.index");
    
    @Test
    public void testCreate2() throws Exception {
        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        
        int i = 1;
        Document doc0 = new Document();
        doc0.add(new StringField("id", String.valueOf(i), Field.Store.YES));
        doc0.add(new TextField("title", "你可能认为标题中出|现关|键的文章比正文中出现关键的文章更有价值", Field.Store.YES));
        
        doc0.add(new SortedDocValuesField("id", new BytesRef(String.valueOf(i).getBytes())));
        
        docs.add(doc0);
        
        i++;
        Document doc1 = new Document();
        doc1.add(new StringField("id", String.valueOf(i), Field.Store.YES));
        doc1.add(new TextField("title", "搜索结果会优先显示标题中出现词的文章", Field.Store.YES));
        
        doc1.add(new SortedDocValuesField("id", new BytesRef(String.valueOf(i).getBytes())));
        
        docs.add(doc1);
        
        i++;
        Document doc2 = new Document();
        doc2.add(new StringField("id", String.valueOf(i), Field.Store.YES));
        doc2.add(new TextField("title", "的词语与词库中的项匹配时，我们就认为这种切分是正确的", Field.Store.YES));
        
        doc2.add(new SortedDocValuesField("id", new BytesRef(String.valueOf(i).getBytes())));
        
        docs.add(doc2);
        
        i++;
        Document doc3 = new Document();
        doc3.add(new StringField("id", String.valueOf(i), Field.Store.YES));
        doc3.add(new TextField("title", "Image Dispaly SubPanel.java", Field.Store.YES));
        
        doc3.add(new SortedDocValuesField("id", new BytesRef(String.valueOf(i).getBytes())));
        
        docs.add(doc3);
        
        i++;
        Document doc4 = new Document();
        doc4.add(new StringField("id", String.valueOf(i), Field.Store.YES));
        doc4.add(new TextField("title", "Image-Dispaly-ThumbnailPanel.java", Field.Store.YES));
        
        
        doc4.add(new SortedDocValuesField("id", new BytesRef(String.valueOf(i).getBytes())));
        
        docs.add(doc4);
        
        // 索引目录类,指定索引在硬盘中的位置
        Directory directory = FSDirectory.open(pp);
        // 引入IK分词器
//        Analyzer analyzer = new IKAnalyzer();
        Analyzer analyzer = new StandardAnalyzer();
        // 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        // 设置打开方式：OpenMode.APPEND 会在索引库的基础上追加新索引。OpenMode.CREATE会先清空原来数据，再提交新的索引
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        // 创建索引的写出工具类。参数：索引的目录和配置信息
        IndexWriter indexWriter = new IndexWriter(directory, conf);
        // 把文档集合交给IndexWriter
        indexWriter.addDocuments(docs);
        // 提交
        indexWriter.commit();
        // 关闭
        indexWriter.close();
    }
    
    
    @Test
    public void testSortQuery() throws Exception {
        // 目录对象
        Directory directory = FSDirectory.open(pp);
        // 创建读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 创建搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);

//      Analyzer analyzer = new IKAnalyzer();
        Analyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("title", analyzer);
        parser.setPhraseSlop(1);
//        Query query = parser.parse("关键词");
//        Query query = parser.parse("现关");
        Query query = parser.parse("display");

        // 创建排序对象,需要排序字段SortField，参数：字段的名称、字段的类型、是否反转如果是false，升序。true降序
        Sort sort = new Sort(new SortField("id", SortField.Type.STRING, false));
        // 搜索
        TopDocs topDocs = searcher.search(query, 10, sort);
        System.out.println("本次搜索共" + topDocs.totalHits + "条数据");

        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 获取文档编号
            int docID = scoreDoc.doc;
            Document doc = reader.document(docID);
            System.out.println("id: " + doc.get("id"));
            System.out.println("title: " + doc.get("title"));
            System.out.println("得分： " + scoreDoc.score);
        }
    }
}
