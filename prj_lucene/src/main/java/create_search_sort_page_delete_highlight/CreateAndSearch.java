package create_search_sort_page_delete_highlight;

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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

//1 创建文档对象
//2 创建存储目录
//3 创建分词器
//4 创建索引写入器的配置对象
//5 创建索引写入器对象
//6 将文档交给索引写入器
//7 提交
//8 关闭

// https://blog.csdn.net/weixin_42633131/article/details/82873731

public class CreateAndSearch {
    @Test
    public void testCreate2() throws Exception {
        // 创建文档的集合
        Collection<Document> docs = new ArrayList<>();
        Document document0 = new Document();
        document0.add(new StringField("id", "0", Field.Store.YES));
        document0.add(new TextField("title", "Wave项目", Field.Store.YES));
        
        document0.add(new SortedDocValuesField("id", new BytesRef("0".getBytes())));
        
        docs.add(document0);
        
        // 创建文档对象
        Document document1 = new Document();
        document1.add(new StringField("id", "1", Field.Store.YES));
        document1.add(new TextField("title", "谷歌地图之父跳槽facebook", Field.Store.YES));
        
        document1.add(new SortedDocValuesField("id", new BytesRef("1".getBytes())));
        
        docs.add(document1);
        
        // 创建文档对象
        Document document2 = new Document();
        document2.add(new StringField("id", "2", Field.Store.YES));
        document2.add(new TextField("title", "谷歌地图之父加盟FaceBook", Field.Store.YES));
        
        document2.add(new SortedDocValuesField("id", new BytesRef("2".getBytes())));
        
        docs.add(document2);
        // 创建文档对象
        Document document3 = new Document();
        document3.add(new StringField("id", "3", Field.Store.YES));
        document3.add(new TextField("title", "谷歌地图创始人拉斯离开谷歌加盟Facebook", Field.Store.YES));
        
        document3.add(new SortedDocValuesField("id", new BytesRef("3".getBytes())));
        
        docs.add(document3);
        // 创建文档对象
        Document document4 = new Document();
        document4.add(new StringField("id", "4", Field.Store.YES));
        document4.add(new TextField("title", "谷歌地图之父跳槽Facebook与Wave项目取消有关", Field.Store.YES));
        
        document4.add(new SortedDocValuesField("id", new BytesRef("4".getBytes())));
        
        docs.add(document4);
        // 创建文档对象
        Document document5 = new Document();
        document5.add(new StringField("id", "5", Field.Store.YES));
        document5.add(new TextField("title", "谷歌地图之父拉斯加盟社交网站Facebook", Field.Store.YES));
        
        document5.add(new SortedDocValuesField("id", new BytesRef("5".getBytes())));
        
        docs.add(document5);
        
        Document document6 = new Document();
        document6.add(new StringField("id", "6", Field.Store.YES));
        document6.add(new TextField("title", "谷歌地图之父拉斯加盟社交网站Facebook6", Field.Store.YES));
        
        document6.add(new SortedDocValuesField("id", new BytesRef("6".getBytes())));
        
        docs.add(document6);
        
        Document document7 = new Document();
        document7.add(new StringField("id", "7", Field.Store.YES));
        document7.add(new TextField("title", "谷歌地图盟社交网站Facebook7", Field.Store.YES));
        
        document7.add(new SortedDocValuesField("id", new BytesRef("7".getBytes())));
        
        docs.add(document7);
        
        Document document8 = new Document();
        document8.add(new StringField("id", "8", Field.Store.YES));
        document8.add(new TextField("title", "谷歌网站Facebook", Field.Store.YES));
        
        document8.add(new SortedDocValuesField("id", new BytesRef("8".getBytes())));
        
        docs.add(document8);

        // 索引目录类,指定索引在硬盘中的位置
        Directory directory = FSDirectory.open(Paths.get("yyy.index"));
        // 引入IK分词器
        Analyzer analyzer = new IKAnalyzer();
//        Analyzer analyzer = new StandardAnalyzer();
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
    public void testSearch() throws Exception {
        // 索引目录对象
        Directory directory = FSDirectory.open(Paths.get("yyy.index"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);

        // 创建查询解析器,两个参数：默认要查询的字段的名称，分词器
        QueryParser parser = new QueryParser("title", new IKAnalyzer());
        // 创建查询对象
        Query query = parser.parse("谷歌2");

        // 搜索数据,两个参数：查询条件对象要查询的最大结果条数
        // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
        TopDocs topDocs = searcher.search(query, 10);
//        TopDocs topDocs = searcher.search(query, 10, Sort.RELEVANCE);
        // 获取总条数
        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for (ScoreDoc scoreDoc : scoreDocs) {
            // 取出文档编号
            int docID = scoreDoc.doc;
            // 根据编号去找文档
            Document doc = reader.document(docID);
            System.out.println("id: " + doc.get("id"));
            System.out.println("title: " + doc.get("title"));
            // 取出文档得分
            System.out.println("得分： " + scoreDoc.score);
        }
    }

//    public void search(Query query) throws Exception {
//        // 索引目录对象
//        Directory directory = FSDirectory.open(Paths.get("yyy.index"));
//        // 索引读取工具
//        IndexReader reader = DirectoryReader.open(directory);
//        // 索引搜索工具
//        IndexSearcher searcher = new IndexSearcher(reader);
//
//        // 搜索数据,两个参数：查询条件对象要查询的最大结果条数
//        // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
//        TopDocs topDocs = searcher.search(query, 10);
//        // 获取总条数
//        System.out.println("本次搜索共找到" + topDocs.totalHits + "条数据");
//        // 获取得分文档对象（ScoreDoc）数组.SocreDoc中包含：文档的编号、文档的得分
//        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//
//        for (ScoreDoc scoreDoc : scoreDocs) {
//            // 取出文档编号
//            int docID = scoreDoc.doc;
//            // 根据编号去找文档
//            Document doc = reader.document(docID);
//            System.out.println("id: " + doc.get("id"));
//            System.out.println("title: " + doc.get("title"));
//            // 取出文档得分
//            System.out.println("得分： " + scoreDoc.score);
//        }
//    }
//
//    /* 测试：修改索引
//     * 注意：
//     *  A：Lucene修改功能底层会先删除，再把新的文档添加。
//     *  B：修改功能会根据Term进行匹配，所有匹配到的都会被删除。这样不好
//     *  C：因此，一般我们修改时，都会根据一个唯一不重复字段进行匹配修改。例如ID
//     *  D：但是词条搜索，要求ID必须是字符串。如果不是，这个方法就不能用。
//     * 如果ID是数值类型，我们不能直接去修改。可以先手动删除deleteDocuments(数值范围查询锁定ID)，再添加。
//     */
//@Test
//public void testUpdate() throws Exception{
//    // 创建目录对象
//    Directory directory = FSDirectory.open(Paths.get("yyy.index"));
//    // 创建配置对象
//    IndexWriterConfig conf = new IndexWriterConfig(new IKAnalyzer());
//    // 创建索引写出工具
//    IndexWriter writer = new IndexWriter(directory, conf);
//
//    // 创建新的文档数据
//    Document doc = new Document();
//    doc.add(new StringField("id","1",Store.YES));
//    doc.add(new TextField("title","谷歌地图之父跳槽facebook ",Store.YES));
//    /* 修改索引。参数：
//         *  词条：根据这个词条匹配到的所有文档都会被修改
//         *  文档信息：要修改的新的文档数据
//         */
//    writer.updateDocument(new Term("id","1"), doc);
//    // 提交
//    writer.commit();
//    // 关闭
//    writer.close();
//}
}
