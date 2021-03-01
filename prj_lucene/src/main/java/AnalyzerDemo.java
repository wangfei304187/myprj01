
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceTokenizer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;

/**
 * Adapted from code which first appeared in a java.net article
 * written by Erik
 */
public class AnalyzerDemo {
  private static final String[] examples = {
    "The quick brown fox jumped over the lazy dogs",
    "XY&Z Corporation - xyz@example.com"
  };

  private static final Analyzer[] analyzers = new Analyzer[]{
    new WhitespaceAnalyzer(),
    new SimpleAnalyzer(),
//    new StopAnalyzer(new CharArraySet(StopAnalyzer.ENGLISH_STOP_WORDS_SET)),
    new StandardAnalyzer()
  };

  public static void main(String[] args) throws IOException {
    // Use the embedded example strings, unless
    // command line arguments are specified, then use those.
    String[] strings = examples;
    if (args.length > 0) {
      strings = args;
    }

    for (int i = 0; i < strings.length; i++) {
      analyze(strings[i]);
    }
  }

  private static void analyze(String text) throws IOException {
    System.out.println("Analyzing \"" + text + "\"");
    for (int i = 0; i < analyzers.length; i++) {
      Analyzer analyzer = analyzers[i];
      String name = analyzer.getClass().getName();
      name = name.substring(name.lastIndexOf(".") + 1);
      System.out.println("  " + name + ":");
      System.out.print("    ");
      AnalyzerUtils.displayAllToken(analyzer, text);
      System.out.println("\n");
    }
  }
 }
