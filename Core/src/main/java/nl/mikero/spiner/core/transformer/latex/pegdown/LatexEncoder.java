package nl.mikero.spiner.core.transformer.latex.pegdown;

import java.util.regex.Matcher;

/**
 * Encodes special characters to their LaTeX equivalents.
 */
public final class LatexEncoder {
    /**
     * Utility class.
     *
     * @throws IllegalAccessError since utility classes can't be constructed
     */
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
            .replaceAll("\\{", Matcher.quoteReplacement("\\textbraceleft"))
            .replaceAll("\\}", Matcher.quoteReplacement("\\textbraceright"))
            .replaceAll("\\>", Matcher.quoteReplacement("\\textgreater"));
    }
}
