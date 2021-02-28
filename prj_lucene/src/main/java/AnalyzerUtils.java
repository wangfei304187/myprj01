
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.lucene.queryparser.classic.Token;

import junit.framework.Assert;

public class AnalyzerUtils {
//  public static Token[] tokensFromAnalysis(Analyzer analyzer,
//                                 String text) throws IOException {
//    TokenStream stream =
//        analyzer.tokenStream("contents", new StringReader(text));
//    ArrayList tokenList = new ArrayList();
//    while (true) {
//      Token token = stream.next();
//      if (token == null) break;
//
//      tokenList.add(token);
//    }
//
//    return (Token[]) tokenList.toArray(new Token[0]);
//  }
//
//  public static void displayTokens(Analyzer analyzer,
//                                 String text) throws IOException {
//    Token[] tokens = tokensFromAnalysis(analyzer, text);
//
//    for (int i = 0; i < tokens.length; i++) {
//      Token token = tokens[i];
//
//      System.out.print("[" + token.termText() + "] ");
//    }
//  }
//
//  public static void displayTokensWithPositions(Analyzer analyzer,
//                                 String text) throws IOException {
//    Token[] tokens = tokensFromAnalysis(analyzer, text);
//
//    int position = 0;
//
//    for (int i = 0; i < tokens.length; i++) {
//      Token token = tokens[i];
//
//      int increment = token.getPositionIncrement();
//
//      if (increment > 0) {
//        position = position + increment;
//        System.out.println();
//        System.out.print(position + ": ");
//      }
//
//      System.out.print("[" + token.termText() + "] ");
//    }
//    System.out.println();
//  }
//
//  public static void displayTokensWithFullDetails(
//      Analyzer analyzer, String text) throws IOException {
//    Token[] tokens = tokensFromAnalysis(analyzer, text);
//
//    int position = 0;
//
//    for (int i = 0; i < tokens.length; i++) {
//      Token token = tokens[i];
//
//      int increment = token.getPositionIncrement();
//
//      if (increment > 0) {
//        position = position + increment;
//        System.out.println();
//        System.out.print(position + ": ");
//      }
//
//      System.out.print("[" + token.termText() + ":" +
//          token.startOffset() + "->" +
//          token.endOffset() + ":" +
//          token.type() + "] ");
//    }
//    System.out.println();
//  }
//
//  public static void assertTokensEqual(Token[] tokens,
//                                       String[] strings) {
//    Assert.assertEquals(strings.length, tokens.length);
//
//    for (int i = 0; i < tokens.length; i++) {
//      Assert.assertEquals("index " + i, strings[i], tokens[i].termText());
//    }
//  }

  public static void main(String[] args) throws IOException {
//    System.out.println("SimpleAnalyzer");
//    displayTokensWithFullDetails(new SimpleAnalyzer(),
//        "The quick brown fox....");
//
//    System.out.println("\n----");
//    System.out.println("StandardAnalyzer");
//    displayTokensWithFullDetails(new StandardAnalyzer(),
//        "I'll e-mail you at xyz@example.com");
      
      System.out.println("SimpleAnalyzer");
//      displayAllToken(new SimpleAnalyzer(),
//          "The quick brown fox....");

      System.out.println("\n----");
      System.out.println("StandardAnalyzer");
      displayAllToken(new StandardAnalyzer(),
          "I'll e-mail you at xyz@example.com");
  }
  
  
  public static void displayAllToken(Analyzer a, String str) {
      try {
          // 所有的分词器都必须含有分词流
          TokenStream stream = a.tokenStream("content", new StringReader(str));// 放回一个TokenStream;
          /**
           * 创建一个属性，这个属性会添加到流里，随着这个TokenStream流增加
           * 这个属性中保存中所有的分词信息
           */
          CharTermAttribute cta=stream.addAttribute(CharTermAttribute.class);
          //位置增量的属性，存储词之间的距离
          PositionIncrementAttribute pia = stream.addAttribute(PositionIncrementAttribute.class);
          //储存每个词直接的偏移量
          OffsetAttribute oa = stream.addAttribute(OffsetAttribute.class);
          //使用的每个分词器直接的类型信息
          TypeAttribute ta = stream.addAttribute(TypeAttribute.class);
          for (; stream.incrementToken();) {
              System.out.print(pia.getPositionIncrement()+":");
              System.out.print(cta+":["+oa.startOffset()+"-"+oa.endOffset()+"]-->"+ta.type()+"\n");
          }
          System.out.println();
      } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      }
  }
}
