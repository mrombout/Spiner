package nl.mikero.turntopassage.core.pegdown.plugin;

import org.junit.Before;
import org.junit.Test;
import org.parboiled.Parboiled;
import org.parboiled.parserunners.RecoveringParseRunner;
import org.parboiled.support.ParsingResult;
import org.pegdown.PegDownProcessor;

import static org.junit.Assert.assertEquals;

public class TwineLinkParserTest {

    private TwineLinkParser parser;
    private PegDownProcessor pdParser;

    @Before
    public void setUp() {
        parser = Parboiled.createParser(TwineLinkParser.class);
    }

    @Test
    public void shouldParseSingleLink() {
        String input = "[[ABCdef]]";
        ParsingResult<?> result = new RecoveringParseRunner<TwineLinkParser>(parser.Link()).run(input);
        TwineLinkNode twineLinkNode = (TwineLinkNode)result.resultValue;

        assertEquals("ABCdef", twineLinkNode.getText());
        assertEquals("ABCdef", twineLinkNode.getHref());
    }

    @Test
    public void shouldParseLabeledLink() {
        String input = "[[ABCdef|SomeLabel]]";
        ParsingResult<?> result = new RecoveringParseRunner<TwineLinkParser>(parser.Link()).run(input);
        TwineLinkNode twineLinkNode = (TwineLinkNode)result.resultValue;

        assertEquals("SomeLabel", twineLinkNode.getText());
        assertEquals("ABCdef", twineLinkNode.getHref());
    }

}
