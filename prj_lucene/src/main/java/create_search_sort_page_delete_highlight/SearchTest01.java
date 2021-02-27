package create_search_sort_page_delete_highlight;

import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

// https://blog.csdn.net/weixin_42633131/article/details/82873731

public class SearchTest01 {

    @Test
    public void testTermQuery() throws Exception {
        System.out.println("testTermQuery");
        // 创建词条查询对象
        Query query = new TermQuery(new Term("title", "Wave项目"));
        search(query);
    }

    /*
     * 测试通配符查询 ? 可以代表任意一个字符 * 可以任意多个任意字符
     */
    @Test
    public void testWildCardQuery() throws Exception {
        System.out.println("testWildCardQuery");
        // 创建查询对象
        Query query = new WildcardQuery(new Term("title", "*歌*"));
        search(query);
    }

    /*
     * 测试模糊查询
     */
    @Test
    public void testFuzzyQuery() throws Exception {
        System.out.println("testFuzzyQuery");
        // 创建模糊查询对象:允许用户输错。但是要求错误的最大编辑距离不能超过2
        // 编辑距离：一个单词到另一个单词最少要修改的次数 facebool --> facebook 需要编辑1次，编辑距离就是1
//    Query query = new FuzzyQuery(new Term("title","fscevool"));
        // 可以手动指定编辑距离，但是参数必须在0~2之间
        Query query = new FuzzyQuery(new Term("title", "facevool"), 1);
        search(query);
    }

    /*
     * 测试：数值范围查询 注意：数值范围查询，可以用来对非String类型的ID进行精确的查找
     */
    @Test
    public void testNumericRangeQuery() throws Exception {
        System.out.println("testNumericRangeQuery");
        // 数值范围查询对象，参数：字段名称，最小值、最大值、是否包含最小值、是否包含最大值
        Query query = NumericRangeQuery.newLongRange("id", 2L, 2L, true, true);

        search(query);
    }
    
    /*
     * 布尔查询：
     *  布尔查询本身没有查询条件，可以把其它查询通过逻辑运算进行组合！
     * 交集：Occur.MUST + Occur.MUST
     * 并集：Occur.SHOULD + Occur.SHOULD
     * 非：Occur.MUST_NOT
     */
    @Test
    public void testBooleanQuery() throws Exception{
        System.out.println("testBooleanQuery");
        Query query1 = NumericRangeQuery.newLongRange("id", 1L, 3L, true, true);
        Query query2 = NumericRangeQuery.newLongRange("id", 2L, 4L, true, true);
        // 创建布尔查询的对象
        BooleanQuery query = new BooleanQuery();
        // 组合其它查询
        query.add(query1, BooleanClause.Occur.MUST_NOT);
        query.add(query2, BooleanClause.Occur.SHOULD);

        search(query);
    }

    public void search(Query query) throws Exception {
        // 索引目录对象
        Directory directory = FSDirectory.open(Paths.get("yyy.index"));
        // 索引读取工具
        IndexReader reader = DirectoryReader.open(directory);
        // 索引搜索工具
        IndexSearcher searcher = new IndexSearcher(reader);

        // 搜索数据,两个参数：查询条件对象要查询的最大结果条数
        // 返回的结果是 按照匹配度排名得分前N名的文档信息（包含查询到的总条数信息、所有符合条件的文档的编号信息）。
        TopDocs topDocs = searcher.search(query, 10);
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
}
