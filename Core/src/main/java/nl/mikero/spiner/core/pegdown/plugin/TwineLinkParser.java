package nl.mikero.spiner.core.pegdown.plugin;

//import org.parboiled.Rule;
//import org.parboiled.support.StringBuilderVar;
//import org.pegdown.Parser;
//import org.pegdown.plugins.InlinePluginParser;

/**
 * Parses links in the default Twine format.
 * <p>
 * Formats supported:
 * <ul>
 * <li>[[passage_name|Link Name]]</li>
 * <li>[[passage_name]]</li>
 * </ul>
 */
public class TwineLinkParser /*extends Parser implements InlinePluginParser*/ {
    private static final long MAX_PARSING_TIME_IN_MILLIS = 1000L;

    private static final String LINK_START = "[[";
    private static final char LINK_SEP = '|';
    private static final String LINK_END = "]]";

    /**
     * Constructs a new TwineLinkParser.
     *
     * See documentation on {@link TwineLinkParser} for more information about the format this parser supports.
     *
     * @see TwineLinkParser
     */
    public TwineLinkParser() {
//        super(NONE, MAX_PARSING_TIME_IN_MILLIS, DefaultParseRunnerProvider);
    }

//    @Override
//    public Rule[] inlinePluginRules() {
//        return new Rule[]{Link()};
//    }

//    @Override
//    public Rule Link() {
//        StringBuilderVar href = new StringBuilderVar();
//        StringBuilderVar text = new StringBuilderVar();
//        return Sequence(
//                String(LINK_START),
//                OneOrMore(
//                        TestNot(String(LINK_END)),
//                        ANY,
//                        text.append(matchedChar()),
//                        Optional(
//                                Ch(LINK_SEP),
//                                OneOrMore(
//                                        TestNot(String(LINK_END)),
//                                        ANY,
//                                        href.append(matchedChar())
//                                )
//                        )
//                ),
//                String(LINK_END),
//                push(new TwineLinkNode(text.getString(), href.isEmpty() ? text.getString() : href.getString()))
//        );
//    }
}
