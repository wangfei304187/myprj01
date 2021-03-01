package create_search_sort_page_delete_highlight;

import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class HighlighterTest {
    // 高亮显示
        @Test
        public void testHighlighter() throws Exception {
            // 目录对象
            Directory directory = FSDirectory.open(Paths.get("yyy.index"));
            // 创建读取工具
            IndexReader reader = DirectoryReader.open(directory);
            // 创建搜索工具
            IndexSearcher searcher = new IndexSearcher(reader);
    
            QueryParser parser = new QueryParser("title", new IKAnalyzer());
            Query query = parser.parse("谷歌地图");
    
            // 格式化器
            Formatter formatter = new SimpleHTMLFormatter("<em>", "</em>");
            QueryScorer scorer = new QueryScorer(query);
            // 准备高亮工具
            Highlighter highlighter = new Highlighter(formatter, scorer);
            // 搜索
            TopDocs topDocs = searcher.search(query, 10);
            System.out.println("本次搜索共" + topDocs.totalHits + "条数据");
    
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            for (ScoreDoc scoreDoc : scoreDocs) {
                // 获取文档编号
                int docID = scoreDoc.doc;
                Document doc = reader.document(docID);
                System.out.println("id: " + doc.get("id"));
    
                String title = doc.get("title");
                // 用高亮工具处理普通的查询结果,参数：分词器，要高亮的字段的名称，高亮字段的原始值
                String hTitle = highlighter.getBestFragment(new IKAnalyzer(), "title", title);
    
                System.out.println("title: " + hTitle);
                // 获取文档的得分
                System.out.println("得分：" + scoreDoc.score);
            }
    
        }
}

