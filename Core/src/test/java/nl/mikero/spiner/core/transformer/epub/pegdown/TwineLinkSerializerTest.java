package nl.mikero.spiner.core.transformer.epub.pegdown;

import nl.mikero.spiner.core.pegdown.plugin.TwineLinkNode;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;
//import org.pegdown.Printer;
//import org.pegdown.ast.TextNode;
//import org.pegdown.ast.Visitor;

import static org.junit.Assert.*;

@Ignore
public class TwineLinkSerializerTest {
    private TwineLinkSerializer serializer;
//    private Printer printer;

    @Before
    public void setUp() {
        serializer = new TwineLinkSerializer();
//        printer = new Printer();
    }

    @Test
    public void visit_NonTwineLinkNode_ReturnsFalse() {
        // Arrange
//        TextNode node = new TextNode("Foo");
//        Visitor visitor = Mockito.mock(Visitor.class);

        // Act
//        boolean result = serializer.visit(node, visitor, printer);

        // Assert
//        assertFalse(result);
    }

    @Test
    public void visit_ValidTwineLinkNodeWithTextAndHref_PrintsHTMLLinkAndReturnsTrue() {
        // Arrange
//        TwineLinkNode node = new TwineLinkNode("Foo", "Bar");
//        Visitor visitor = Mockito.mock(Visitor.class);

        // Act
//        boolean result = serializer.visit(node, visitor, printer);

        // Assert
//        assertTrue(result);
//        assertEquals("<a href=\"Bar\">Foo</a>", printer.getString());
    }

    @Test
    public void visit_ValidTwineLinkNodeSingleValue_PrintsHTMLLinkAndReturnsTrue() {
        // Arrange
//        TwineLinkNode node = new TwineLinkNode("Foo");
//        Visitor visitor = Mockito.mock(Visitor.class);

        // Act
//        boolean result = serializer.visit(node, visitor, printer);

        // Assert
//        assertTrue(result);
//        assertEquals("<a href=\"Foo\">Foo</a>", printer.getString());
    }

    @Test
    public void visit_ValidTwineLinkNodeWithEmptyTextAndHref_PrintsHTMLLinkAndReturnsTrue() {
        // Arrange
//        TwineLinkNode node = new TwineLinkNode("", "");
//        Visitor visitor = Mockito.mock(Visitor.class);

        // Act
//        boolean result = serializer.visit(node, visitor, printer);

        // Assert
//        assertTrue(result);
//        assertEquals("<a href=\"\"></a>", printer.getString());
    }

    @Test
    public void visit_ValidTwineLinkNodeSingleEmptyValue_PrintsHTMLLinkAndReturnsTrue() {
        // Arrange
//        TwineLinkNode node = new TwineLinkNode("");
//        Visitor visitor = Mockito.mock(Visitor.class);

        // Act
//        boolean result = serializer.visit(node, visitor, printer);

        // Assert
//        assertTrue(result);
//        assertEquals("<a href=\"\"></a>", printer.getString());
    }

    @Test(expected = NullPointerException.class)
    public void visit_NullNode_ThrowsNullPointerException() {
        // Arrange
//        Visitor visitor = Mockito.mock(Visitor.class);

        // Act
//        boolean result = serializer.visit(null, visitor, printer);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void visit_NullVisitor_ThrowsNullPointerException() {
        // Arrange
//        TwineLinkNode node = new TwineLinkNode("");

        // Act
//        boolean result = serializer.visit(node, null, printer);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void visit_NullPrinter_ThrowsNullPointerException() {
        // Arrange
//        TwineLinkNode node = new TwineLinkNode("");
//        Visitor visitor = Mockito.mock(Visitor.class);

        // Act
//        boolean result = serializer.visit(node, visitor, null);

        // Assert
    }
}
