package nl.mikero.turntopassage.core.pegdown.plugin;

import org.junit.Before;
import org.junit.Test;
import org.pegdown.LinkRenderer;
import org.pegdown.ast.WikiLinkNode;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class TwineLinkRendererTest {

    private TwineLinkRenderer linkRenderer;

    @Before
    public void setUp() {
        this.linkRenderer = new TwineLinkRenderer();
    }

    @Test(expected = NullPointerException.class)
    public void render_NullNode_ThrowsNullPointerException() {
        // Arrange

        // Act
        linkRenderer.render((WikiLinkNode) null);

        // Assert
    }

    @Test
    public void render_DirectPassageLink_TextAndUrlArePassageName() {
        // Arrange
        WikiLinkNode node = mock(WikiLinkNode.class);
        when(node.getText()).thenReturn("SomePassageName");

        // Act
        LinkRenderer.Rendering result = linkRenderer.render(node);

        // Assert
        assertEquals("SomePassageName", result.text);
        assertEquals("SomePassageName.xhtml", result.href);
    }

    @Test
    public void render_NamedPassageLink_TextIsNameUrlIsPassageName() {
        // Arrange
        String text = "Some Nice Text";
        String href = "SomePassageName";

        WikiLinkNode node = mock(WikiLinkNode.class);
        when(node.getText()).thenReturn(text + "|" + href);

        // Act
        LinkRenderer.Rendering result = linkRenderer.render(node);

        // Assert
        assertEquals(text, result.text);
        assertEquals(href + ".xhtml", result.href);
    }

}
