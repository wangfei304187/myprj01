package create_search_sort_page_delete_highlight;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

//1 创建文档对象
//2 创建存储目录
//3 创建分词器
//4 创建索引写入器的配置对象
//5 创建索引写入器对象
//6 将文档交给索引写入器
//7 提交
//8 关闭

// https://blog.csdn.net/weixin_42633131/article/details/82873731

public class CreateTest {
    // 创建索引
    @Test
    public void testCreate() throws Exception {
        // 1 创建文档对象
        Document document = new Document();
        // 创建并添加字段信息。参数：字段的名称、字段的值、是否存储，这里选Store.YES代表存储到文档列表。Store.NO代表不存储
        document.add(new StringField("id", "1", Field.Store.YES));
        // 这里我们title字段需要用TextField，即创建索引又会被分词。
        document.add(new TextField("title", "谷歌地图之父跳槽facebook", Field.Store.YES));

        // 2 索引目录类,指定索引在硬盘中的位置
        Directory directory = FSDirectory.open(Paths.get("yyy.index"));
        // 3 创建分词器对象
        Analyzer analyzer = new StandardAnalyzer();
        // 4 索引写出工具的配置对象
        IndexWriterConfig conf = new IndexWriterConfig(analyzer);
        conf.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        // 5 创建索引的写出工具类。参数：索引的目录和配置信息
        IndexWriter indexWriter = new IndexWriter(directory, conf);

        // 6 把文档交给IndexWriter
        indexWriter.addDocument(document);
        // 7 提交
        indexWriter.commit();
        // 8 关闭
        indexWriter.close();
    }

}
