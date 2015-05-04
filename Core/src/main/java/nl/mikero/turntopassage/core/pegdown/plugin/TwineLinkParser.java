package nl.mikero.turntopassage.core.pegdown.plugin;

import org.parboiled.Rule;
import org.parboiled.support.StringBuilderVar;
import org.pegdown.Parser;
import org.pegdown.plugins.InlinePluginParser;

/**
 * Parses links in the default Twine format.
 * <p>
 * Formats supported:
 * <ul>
 * <li>[[passage_name|Link Name]]</li>
 * <li>[[passage_name]]</li>
 * </ul>
 */
public class TwineLinkParser extends Parser implements InlinePluginParser {
    public TwineLinkParser() {
        super(NONE, 1000L, DefaultParseRunnerProvider);
    }

    @Override
    public Rule[] inlinePluginRules() {
        return new Rule[]{Link()};
    }

    @Override
    public Rule Link() {
        StringBuilderVar href = new StringBuilderVar();
        StringBuilderVar text = new StringBuilderVar();
        return Sequence(
                String("[["),
                OneOrMore(
                        TestNot(String("]]")),
                        ANY,
                        href.append(matchedChar()),
                        Optional(
                                Ch('|'),
                                OneOrMore(
                                        TestNot(String("]]")),
                                        ANY,
                                        text.append(matchedChar())
                                )
                        )
                ),
                String("]]"),
                push(new TwineLinkNode(text.getString(), href.getString()))
        );
    }
}