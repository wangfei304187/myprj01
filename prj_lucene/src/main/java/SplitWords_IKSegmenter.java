import java.io.StringReader;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class SplitWords_IKSegmenter {
    
    public static void main(String[] args)
    {
        System.out.println(spiltWords("腾讯游戏学院成立于2016年12月,致力于打造游戏知识分享和行业交流平台,专注于推动专业人才培养、游戏学研究和发展、开发者生态建设。"));
    }
    
    public static String spiltWords(String srcString) {
        StringBuffer wordsBuffer = new StringBuffer();
        try {
            IKSegmenter ik = new IKSegmenter(new StringReader(srcString), true);
            Lexeme lex = null;
            while ((lex = ik.next()) != null) {
                wordsBuffer.append(lex.getLexemeText()).append("|");
            }
        } catch (Exception e) {
        }
        return wordsBuffer.toString();
    }
}