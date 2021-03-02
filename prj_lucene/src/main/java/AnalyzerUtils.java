
import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class AnalyzerUtils {

    public static void main(String[] args) throws IOException {

        System.out.println("1. SimpleAnalyzer");
        displayAllToken(new SimpleAnalyzer(), "The quick brown fox....");

        System.out.println("2. StandardAnalyzer");
        displayAllToken(new StandardAnalyzer(), "I'll e-mail you at xyz@example.com");
        displayAllToken(new StandardAnalyzer(), "XY&Z Corporation - xyz@example.com");

        System.out.println("3. IKAnalyzer");
        displayAllToken(new IKAnalyzer(true), "I'll e-mail you at xyz@example.com");
        displayAllToken(new IKAnalyzer(true), "XY&Z Corporation - xyz@example.com");
        
        System.out.println("-------------------------");
        System.out.println("StandardAnalyzer");
        displayAllToken(new StandardAnalyzer(), "Image Display SubPanel.java");
        
        System.out.println("StandardAnalyzer");
        displayAllToken(new StandardAnalyzer(), "Image-Display-ThumbnailPanel.java");
        
        System.out.println("StandardAnalyzer");
        displayAllToken(new StandardAnalyzer(), "display");

    }

//    https://lucene.apache.org/core/8_6_1/core/index.html
    // https://blog.csdn.net/qiruiduni/article/details/38295817
    public static void displayAllToken(Analyzer analyzer, String str) {
        try {
            // 所有的分词器都必须含有分词流
            StringReader reader = new StringReader(str);
            TokenStream stream = analyzer.tokenStream("content", reader);// 放回一个TokenStream;
            /**
             * 创建一个属性，这个属性会添加到流里，随着这个TokenStream流增加 这个属性中保存中所有的分词信息
             */
            stream.reset();
            CharTermAttribute term = stream.addAttribute(CharTermAttribute.class);

            // 位置增量的属性，存储词之间的距离
            PositionIncrementAttribute pia = stream.addAttribute(PositionIncrementAttribute.class);
            // 储存每个词直接的偏移量
            OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);
            // 使用的每个分词器直接的类型信息
            TypeAttribute ta = stream.addAttribute(TypeAttribute.class);
            while (stream.incrementToken()) {
                System.out.print(pia.getPositionIncrement() + ":");
                System.out.print(term + ":[" + oa.startOffset() + "-" + oa.endOffset() + "]-->" + ta.type() + "\n");
            }
            System.out.println();

            stream.end();
            stream.close();
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
