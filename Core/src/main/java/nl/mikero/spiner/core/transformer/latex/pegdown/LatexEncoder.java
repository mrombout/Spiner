package nl.mikero.spiner.core.transformer.latex.pegdown;

import java.util.regex.Matcher;

public final class LatexEncoder {
    private LatexEncoder() {
        throw new IllegalAccessError("Utility class");
    }

    public static String encode(final String inputText) {
        return inputText
            .replaceAll("\\\\", Matcher.quoteReplacement("\\textbackslash"))
            .replaceAll("\\_", Matcher.quoteReplacement("\\_"))
            .replaceAll("\\{", Matcher.quoteReplacement("\\textbraceleft"))
            .replaceAll("\\}", Matcher.quoteReplacement("\\textbraceright"))
            .replaceAll("\\>", Matcher.quoteReplacement("\\textgreater"))
            .replaceAll("\\#", Matcher.quoteReplacement("\\#"));
    }
}
