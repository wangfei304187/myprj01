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

public class AnalyzerUtils
{

    public static void main(String[] args) throws IOException
    {

        System.out.println("1. SimpleAnalyzer");
        AnalyzerUtils.displayAllToken(new SimpleAnalyzer(), "The quick brown fox....");
        AnalyzerUtils.displayAllToken(new SimpleAnalyzer(), "com.my.uiapp.ImageDisplaySunPanel.java");

        System.out.println("2. StandardAnalyzer");
        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), "I'll e-mail you at xyz@example.com");
        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), "XY&Z Corporation - xyz@example.com");
        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), "16*1.125");
        String testStr = "(0029,0028) [LO] Private Creator Data Element [Head]\n" +
                "(0029,0029) [LO] Private Creator Data Element [16*1.125]\n" +
                "(0029,0030) [LO] Private Creator Data Element [1]";
        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), testStr);
        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), "头颅常规");

        System.out.println("3. IKAnalyzer");
        AnalyzerUtils.displayAllToken(new IKAnalyzer(true), "I'll e-mail you at xyz@example.com");
        AnalyzerUtils.displayAllToken(new IKAnalyzer(true), "XY&Z Corporation - xyz@example.com");
        AnalyzerUtils.displayAllToken(new IKAnalyzer(true), "com.my.uiapp.ImageDisplaySunPanel.java");
        AnalyzerUtils.displayAllToken(new IKAnalyzer(true), "16*1.125");
        AnalyzerUtils.displayAllToken(new IKAnalyzer(true), "头颅常规");
        AnalyzerUtils.displayAllToken(new IKAnalyzer(), "头颅常规");

        System.out.println("-------------------------");
        System.out.println("StandardAnalyzer");
        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), "Image Display SubPanel.java");

        System.out.println("StandardAnalyzer");
        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), "Image-Display-ThumbnailPanel.java");

        System.out.println("StandardAnalyzer");
        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), "display");

        AnalyzerUtils.displayAllToken(new StandardAnalyzer(), "com.my.uiapp.ImageDisplaySunPanel.java");

    }

    // https://lucene.apache.org/core/8_6_1/core/index.html
    // https://blog.csdn.net/qiruiduni/article/details/38295817
    public static void displayAllToken(Analyzer analyzer, String str)
    {
        try
        {
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
            while (stream.incrementToken())
            {
                System.out.print(pia.getPositionIncrement() + ":");
                System.out.print(term + ":[" + oa.startOffset() + "-" + oa.endOffset() + "]-->" + ta.type() + "\n");
            }
            System.out.println();

            stream.end();
            stream.close();
            reader.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
