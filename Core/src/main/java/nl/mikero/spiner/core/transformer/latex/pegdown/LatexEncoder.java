package nl.mikero.spiner.core.transformer.latex.pegdown;

import java.util.regex.Matcher;

public class LatexEncoder {
    public static String encode(String inputText) {
        String encoded = inputText
            .replaceAll("\\\\", Matcher.quoteReplacement("\\textbackslash"))
            .replaceAll("\\_", Matcher.quoteReplacement("\\_"))
            .replaceAll("\\{", Matcher.quoteReplacement("\\textbraceleft"))
            .replaceAll("\\}", Matcher.quoteReplacement("\\textbraceright"))
            .replaceAll("\\>", Matcher.quoteReplacement("\\textgreater"))
            .replaceAll("\\#", Matcher.quoteReplacement("\\#"));
        return encoded;
    }
}
