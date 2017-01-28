package nl.mikero.spiner.core.pegdown.plugin;

import nl.mikero.spiner.core.transformer.epub.embedder.ImageEmbedder;
import org.junit.Before;
import org.junit.Test;
import org.pegdown.LinkRenderer;
import org.pegdown.ast.WikiLinkNode;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TwineLinkRendererTest {

    @SuppressWarnings("FieldCanBeLocal")
    private ImageEmbedder mockImageEmbedder;
    private TwineLinkRenderer linkRenderer;

    @Before
    public void setUp() {
        this.mockImageEmbedder = mock(ImageEmbedder.class);
        this.linkRenderer = new TwineLinkRenderer(mockImageEmbedder);
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

    @Test
    public void render_DirectExternalLink_XhtmlIsNotAppended() {
        // Arrange
        WikiLinkNode node = mock(WikiLinkNode.class);
        when(node.getText()).thenReturn("http://spiner.github.io");

        // Act
        LinkRenderer.Rendering result = linkRenderer.render(node);

        // Assert
        assertEquals("http://spiner.github.io", result.text);
        assertEquals("http://spiner.github.io", result.href);
    }

    @Test
    public void render_NamedExternalLink_XhtmlIsNotAppended() {
        // Arrange
        WikiLinkNode node = mock(WikiLinkNode.class);
        when(node.getText()).thenReturn("Spiner|http://spiner.github.io");

        // Act
        LinkRenderer.Rendering result = linkRenderer.render(node);

        // Assert
        assertEquals("Spiner", result.text);
        assertEquals("http://spiner.github.io", result.href);
    }

}
