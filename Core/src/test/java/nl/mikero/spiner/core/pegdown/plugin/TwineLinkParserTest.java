package nl.mikero.spiner.core.pegdown.plugin;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
//import org.parboiled.Parboiled;
//import org.parboiled.parserunners.RecoveringParseRunner;
//import org.parboiled.support.ParsingResult;
//import org.pegdown.PegDownProcessor;

import static org.junit.Assert.assertEquals;

@Ignore
public class TwineLinkParserTest {

    private TwineLinkParser parser;
//    private PegDownProcessor pdParser;

    @Before
    public void setUp() {
//        parser = Parboiled.createParser(TwineLinkParser.class);
    }

    @Test
    public void Link_PlainLink_TextIsLabelAndHref() {
        // Arrange
        String input = "[[ABCdef]]";

        // Act
//        ParsingResult<?> result = new RecoveringParseRunner<TwineLinkParser>(parser.Link()).run(input);
//        TwineLinkNode twineLinkNode = (TwineLinkNode)result.resultValue;

        // Assert
//        assertEquals("ABCdef", twineLinkNode.getText());
//        assertEquals("ABCdef", twineLinkNode.getHref());
    }

    @Test
    public void Link_LabelLink_FirstIsLabelSecondIsHref() {
        // Arrange
        String input = "[[SomeLabel|SomeHref]]";

        // Act
//        ParsingResult<?> result = new RecoveringParseRunner<TwineLinkParser>(parser.Link()).run(input);
//        TwineLinkNode twineLinkNode = (TwineLinkNode)result.resultValue;

        // Assert
//        assertEquals("SomeLabel", twineLinkNode.getText());
//        assertEquals("SomeHref", twineLinkNode.getHref());
    }

}
