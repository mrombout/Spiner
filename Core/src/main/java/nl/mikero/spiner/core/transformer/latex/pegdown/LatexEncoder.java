package nl.mikero.spiner.core.transformer.latex.pegdown;

import java.util.regex.Matcher;

/**
 * Encodes special characters to their LaTeX equivalents.
 */
public final class LatexEncoder {
    private LatexEncoder() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Encodes special characters to their LaTeX equivalents.
     *
     * @param inputText input text to encode
     * @return encoded input text
     */
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
