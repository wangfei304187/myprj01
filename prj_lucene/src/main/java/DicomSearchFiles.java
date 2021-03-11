/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

/** Simple command-line based search demo. */
public class DicomSearchFiles
{

    private DicomSearchFiles()
    {
    }

    /** Simple command-line based search demo. */
    public static void main(String[] args) throws Exception
    {
        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get("/home/wf/db.index")));
        IndexSearcher searcher = new IndexSearcher(reader);
        Analyzer analyzer = DicomSearchFiles.getAnalyzer();
        QueryParser parser = new QueryParser("contents", analyzer);
        // Query query = parser.parse("头颅");
        Query query = parser.parse("128\\*0.625");
        // query=contents:128 contents:0.625; getClass=class org.apache.lucene.search.BooleanQuery
        System.out.println("query=" + query + "; getClass=" + query.getClass());
        TopDocs tds = null; 
        Sort sort = null;
        int topN = Integer.MAX_VALUE;
        if (sort != null)
        {
            tds = searcher.search(query, topN, sort);
        }
        else
        {
            tds = searcher.search(query, topN);
        }

        System.out.println("size: " + tds.scoreDocs.length);
        for (ScoreDoc sd : tds.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
           
            DicomSearchFiles.print(d, sd);
        }
    }

    private static Analyzer getAnalyzer()
    {
        // return new StandardAnalyzer();
        return new IKAnalyzer(true);
    }

    private static void print(Document d, ScoreDoc sd)
    {
        System.out.print(sd.doc + ":(" + sd.score + ") ");
        System.out.print(d.get("path"));
        System.out.println();
    }
}
