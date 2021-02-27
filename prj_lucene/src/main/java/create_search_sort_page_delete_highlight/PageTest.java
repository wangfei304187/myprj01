package create_search_sort_page_delete_highlight;

import java.awt.Window.Type;
import java.io.File;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class PageTest {

 // 分页
    @Test
    public void testPageQuery() throws Exception {
        // 实际上Lucene本身不支持分页。因此我们需要自己进行逻辑分页。我们要准备分页参数：
        int pageSize = 2;// 每页条数
        int pageNum = 3;// 当前页码
        int start = (pageNum - 1) * pageSize;// 当前页的起始条数
        int end = start + pageSize;// 当前页的结束条数（不能包含）
        
        // 目录对象
        Directory directory = FSDirectory.open(Paths.get("yyy.index"));
        // 创建读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 创建搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);
        
        QueryParser parser = new QueryParser("title", new IKAnalyzer());
        Query query = parser.parse("谷歌");
        
        // 创建排序对象,需要排序字段SortField，参数：字段的名称、字段的类型、是否反转如果是false，升序。true降序
//        Sort sort = new Sort(new SortField("id", SortField.Type.LONG, false));
        Sort sort = new Sort(new SortField("id", SortField.Type.STRING, true));
        // 搜索数据，查询0~end条
        TopDocs topDocs = searcher.search(query, end, sort); // Return only the top n results
        System.out.println("本次搜索共" + topDocs.totalHits + "条数据");
        
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        
        System.out.println("Top " + end + " results:");
        for (int i = 0; i < end; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
            // 获取文档编号
            int docID = scoreDoc.doc;
            Document doc = reader.document(docID);
            System.out.println("id: " + doc.get("id"));
            System.out.println("title: " + doc.get("title"));
        }
        System.out.println("============================");
        
        System.out.println("Page results:");
        for (int i = start; i < end; i++) {
            ScoreDoc scoreDoc = scoreDocs[i];
            // 获取文档编号
            int docID = scoreDoc.doc;
            Document doc = reader.document(docID);
            System.out.println("id: " + doc.get("id"));
            System.out.println("title: " + doc.get("title"));
        }
    }
}
