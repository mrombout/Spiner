package nl.mikero.spiner.core.pegdown.plugin;

import org.junit.Assert;
import org.junit.Test;
import org.pegdown.ast.Node;
import org.pegdown.ast.Visitor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TwineLinkNodeTest {

    @Test
    public void construct_EmptyText_TextIsSetToHref() {
        // Arrange
        String href = "MyHref";

        // Act
        TwineLinkNode node = new TwineLinkNode("", href);

        // Assert
        assertEquals(href, node.getText());
        assertEquals(href, node.getHref());
    }

    @Test(expected = NullPointerException.class)
    public void construct_NullText_ThrowsNullPointerException() {
        // Arrange
        String href = "MyHref";

        // Act
        new TwineLinkNode(null, href);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void construct_NullHref_ThrowsNullPointerException() {
        // Arrange
        String text = "MyText";

        // Act
        new TwineLinkNode(text, null);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void accept_NullVisitor_ThrowsException() {
        // Arrange
        TwineLinkNode node = new TwineLinkNode("MyText", "MyHref");

        // Act
        node.accept(null);

        // Assert
    }

    @Test
    public void accept_NotNullVisitor_VisitIsCalledWithSelf() {
        // Arrange
        TwineLinkNode node = new TwineLinkNode("MyText", "MyHref");
        Visitor visitor = mock(Visitor.class);

        // Act
        node.accept(visitor);

        // Assert
        verify(visitor, times(1)).visit((Node) node);
    }

    @Test
    public void toString_ValidData_ReturnsValueTwiceSeperatedByPipe() {
        // Arrange
        TwineLinkNode node = new TwineLinkNode("MyTest");

        // Act
        String result = node.toString();

        // Assert
        assertEquals("TwineLinkNode [0-0] 'MyTest'|MyTest", result);
    }
}
