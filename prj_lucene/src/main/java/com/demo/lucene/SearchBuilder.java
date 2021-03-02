package com.demo.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class SearchBuilder
{

    public static void doSearch(String indexDir, String queryStr) throws IOException, ParseException, InvalidTokenOffsetsException
    {
        Path p = Paths.get(indexDir);
        System.out.println(p.toString());

        Directory directory = FSDirectory.open(p);
        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);

        // Analyzer analyzer = new SmartChineseAnalyzer();
        Analyzer analyzer = new IKAnalyzer();
        // QueryParser parser = new QueryParser("tcontent", analyzer);
        QueryParser parser = new MultiFieldQueryParser(new String[] { "title", "tcontent" }, analyzer);
        parser.setPhraseSlop(3);
        // 设置短语搜索的坡度为3,默认为0
        parser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = parser.parse(queryStr);

        long startTime = System.currentTimeMillis();
        TopDocs docs = searcher.search(query, 10);

        System.out.println("查找'" + queryStr + "'所用时间：" + (System.currentTimeMillis() - startTime));
        System.out.println("查询到'" + docs.totalHits + "'条记录");

        // 加入高亮显示的
        SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter("<b><font color=red>", "</font></b>");
        QueryScorer scorer = new QueryScorer(query);// 计算查询结果最高的得分
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);// 根据得分算出一个片段
        Highlighter highlighter = new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);// 设置显示高亮的片段

        // 遍历查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs)
        {
            Document doc = searcher.doc(scoreDoc.doc);
            String tcontent = doc.get("tcontent");
            if (tcontent != null)
            {
                TokenStream tokenStream = analyzer.tokenStream("tcontent", new StringReader(tcontent));
                String summary = highlighter.getBestFragment(tokenStream, tcontent);
                System.out.println(summary);
            }

            String title = doc.get("title");
            if (title != null)
            {
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String summary = highlighter.getBestFragment(tokenStream, title);
                System.out.println(summary);
            }
        }
        reader.close();
    }

    public static void main(String[] args)
    {
        String indexDir = "xxx.index";
        String q = "内容"; // 查询这个字符串
        try
        {
            SearchBuilder.doSearch(indexDir, q);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}