package com.my.dirlucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.junit.Test;

// https://blog.csdn.net/weixin_34235105/article/details/94542542

public class DirTest {
    
    @Test
    public void test01(){
        searchByContent("ImageDisplaySubPanel", Sort.RELEVANCE);
        System.out.println("------------");
        
        searchByContent("usb", null);
        System.out.println("______________");
        
        searchByPath("storage", null);
        System.out.println("==============");
        
        searchByFileName("RowTaskStorage.java", null);
        System.out.println("###############");
        
        searchByFileNameWildcard("*export*", null); // Export ?
    }
    
    private static IndexReader reader = null;
    static {
        try {
            // FileIndexUtils.createIndex(true);  // create index db if running at the first time.
            
            reader = DirectoryReader.open(FileIndexUtils.getDirectory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public IndexSearcher getSearcher() {
        try {
            if (reader == null) {
                reader = DirectoryReader.open(FileIndexUtils.getDirectory());
            } else {
                IndexReader tr = DirectoryReader.openIfChanged((DirectoryReader) reader);
                if (tr != null) {
                    reader.close();
                    reader = tr;
                }
            }
            return new IndexSearcher(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void searchByContent(String queryStr, Sort sort) {
        try {
            IndexSearcher searcher = getSearcher();
            QueryParser parser = new QueryParser("content", new StandardAnalyzer());
            Query query = parser.parse(queryStr);
            TopDocs tds = null;
            if (sort != null)
                tds = searcher.search(query, 50, sort);
            else {
                tds = searcher.search(query, 50);
            }
            for (ScoreDoc sd : tds.scoreDocs) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc + ":(" + sd.score + ") " 
                        + "[" + d.get("fileName") + ", " + d.get("path")
                        + "], size: " + Integer.valueOf(d.get("size")) / 1024);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void searchByPath(String queryStr, Sort sort) {
        try {
            IndexSearcher searcher = getSearcher();
            QueryParser parser = new QueryParser("path", new StandardAnalyzer());
            Query query = parser.parse(queryStr);
            TopDocs tds = null;
            if (sort != null)
                tds = searcher.search(query, 50, sort);
            else {
                tds = searcher.search(query, 50);
            }
            for (ScoreDoc sd : tds.scoreDocs) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc + ":(" + sd.score + ") " 
                        + "[" + d.get("fileName") + ", " + d.get("path")
                        + "], size: " + Integer.valueOf(d.get("size")) / 1024);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void searchByFileName(String queryStr, Sort sort) {
        try {
            IndexSearcher searcher = getSearcher();
            QueryParser parser = new QueryParser("fileName", new StandardAnalyzer());
            Query query = parser.parse(queryStr);
            TopDocs tds = null;
            if (sort != null)
                tds = searcher.search(query, 50, sort);
            else {
                tds = searcher.search(query, 50);
            }
            for (ScoreDoc sd : tds.scoreDocs) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc + ":(" + sd.score + ") " 
                        + "[" + d.get("fileName") + ", " + d.get("path")
                        + "], size: " + Integer.valueOf(d.get("size")) / 1024);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void searchByFileNameWildcard(String queryStr, Sort sort) {
        try {
            IndexSearcher searcher = getSearcher();
//            QueryParser parser = new QueryParser("fileName", new StandardAnalyzer());
//            Query query = parser.parse(queryStr);
            Query query = new WildcardQuery(new Term("fileName", queryStr));
            TopDocs tds = null;
            if (sort != null)
                tds = searcher.search(query, 50, sort);
            else {
                tds = searcher.search(query, 50);
            }
            for (ScoreDoc sd : tds.scoreDocs) {
                Document d = searcher.doc(sd.doc);
                System.out.println(sd.doc + ":(" + sd.score + ") " 
                        + "[" + d.get("fileName") + ", " + d.get("path")
                        + "], size: " + Integer.valueOf(d.get("size")) / 1024);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
