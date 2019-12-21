package nl.mikero.spiner.core.transformer.epub;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import nl.mikero.spiner.core.transformer.epub.embedder.ResourceEmbedder;
import nl.mikero.spiner.core.twine.extended.model.XtwMetadata;
import nl.mikero.spiner.core.twine.markdown.MarkdownProcessor;
import nl.mikero.spiner.core.twine.model.Style;
import nl.mikero.spiner.core.twine.model.TwPassagedata;
import nl.mikero.spiner.core.twine.model.TwStoriesdata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.Resources;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TwineStoryEpubTransformerTest {

    private MarkdownProcessor mockPegDownProcessor;
    private ResourceEmbedder mockResourceEmbedder;

    private TwineStoryEpubTransformer convertor;

    @Before
    public void setUp() {
        mockPegDownProcessor = mock(MarkdownProcessor.class);
        mockResourceEmbedder = mock(ResourceEmbedder.class);

        this.convertor = new TwineStoryEpubTransformer(mockPegDownProcessor, mockResourceEmbedder);
    }

    @Test(expected =  NullPointerException.class)
    public void construct_NullProcessor_ThrowsNullPointerException() {
        // Arrange

        // Act
        new TwineStoryEpubTransformer(null, mockResourceEmbedder);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void construct_NullLinkRenderer_ThrowsNullPointerException() {
        // Arrange

        // Act
        new TwineStoryEpubTransformer(mockPegDownProcessor, null);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void convert_NullStory_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        try(OutputStream out = new ByteArrayOutputStream()) {
            convertor.transform(null, out);
        }

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void convert_NullOutput_ThrowsNullPointerException() throws Exception {
        // Arrange

        // Act
        convertor.transform(new TwStorydata(), null);

        // Assert
    }

    @Test
    public void convert_ValidStory_WritesEpubFile() throws Exception {
        // Arrange
        when(mockPegDownProcessor.markdownToHtml(anyString()))
            .thenAnswer(invocation -> "<p>" + String.valueOf(invocation.getArguments()[0]) + "</p>");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TwStoriesdata twStoriesdata = new TwStoriesdata();

        TwStorydata twStorydata = new TwStorydata();
        twStoriesdata.getTwStorydata().add(twStorydata);

        TwPassagedata twPassagedata = new TwPassagedata();
        twPassagedata.setPid(1);
        twPassagedata.setName("Passage 1");
        twPassagedata.setValue("Lorum ipsum dolor sit amet.");
        twPassagedata.setTags("");

        TwPassagedata twPassagedata2 = new TwPassagedata();
        twPassagedata2.setPid(2);
        twPassagedata2.setName("Passage 2");
        twPassagedata2.setValue("Consectetur amet pizza.");
        twPassagedata2.setTags("");

        twStorydata.getTwPassagedata().add(twPassagedata);
        twStorydata.getTwPassagedata().add(twPassagedata2);

        // Act
        convertor.transform(twStorydata, outputStream);

        // Assert
        try(InputStream in = new ByteArrayInputStream(outputStream.toByteArray())) {
            EpubReader epubReader = new EpubReader();

            Book book = epubReader.readEpub(in);
            assertEquals(twStorydata.getTwPassagedata().size() + 1, book.getResources().getAll().size());
        }
    }

    @Test
    public void transform_StyledStory_StylesheetAdded() throws Exception {
        // Arrange
        when(mockPegDownProcessor.markdownToHtml(anyString()))
                .thenAnswer(invocation -> "<p>" + String.valueOf(invocation.getArguments()[0]) + "</p>");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TwStoriesdata twStoriesdata = new TwStoriesdata();

        TwStorydata twStorydata = new TwStorydata();
        twStoriesdata.getTwStorydata().add(twStorydata);

        Style style = new Style();
        style.setRole("stylesheet");
        style.setType("text/css");
        style.setValue("* { background-color: #000000;");
        twStorydata.setStyle(style);

        // Act
        convertor.transform(twStorydata, outputStream);

        // Assert
        try(InputStream in = new ByteArrayInputStream(outputStream.toByteArray())) {
            EpubReader epubReader = new EpubReader();

            Book book = epubReader.readEpub(in);
            Resources resources = book.getResources();
            List<Resource> resourcesByMediaType = resources.getResourcesByMediaType(MediatypeService.CSS);
            assertFalse(resourcesByMediaType.isEmpty());
            assertEquals("Story.css", resourcesByMediaType.get(0).getHref());
        }
    }

    @Test
    public void convert_StoryExtendedMetadata_WritesMetadataToEpub() throws Exception {
        // Arrange
        String expectedTitle = "A Story with a Title";
        String expectedLanguage = "en-US";

        when(mockPegDownProcessor.markdownToHtml(anyString()))
                .thenAnswer(invocation -> "<p>" + String.valueOf(invocation.getArguments()[0]) + "</p>");

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        TwStoriesdata twStoriesdata = new TwStoriesdata();

        TwStorydata twStorydata = new TwStorydata();
        twStoriesdata.getTwStorydata().add(twStorydata);

        XtwMetadata xtwMetadata = new XtwMetadata();
        xtwMetadata.getTitle().add(expectedTitle);
        xtwMetadata.setLanguage(expectedLanguage);
        twStorydata.setXtwMetadata(xtwMetadata);

        TwPassagedata twPassagedata = new TwPassagedata();
        twPassagedata.setPid(1);
        twPassagedata.setName("Passage 1");
        twPassagedata.setValue("Lorum ipsum dolor sit amet.");
        twPassagedata.setTags("");

        TwPassagedata twPassagedata2 = new TwPassagedata();
        twPassagedata2.setPid(2);
        twPassagedata2.setName("Passage 2");
        twPassagedata2.setValue("Consectetur amet pizza.");
        twPassagedata2.setTags("");

        twStorydata.getTwPassagedata().add(twPassagedata);
        twStorydata.getTwPassagedata().add(twPassagedata2);

        // Act
        convertor.transform(twStorydata, outputStream);

        // Assert
        try(InputStream in = new ByteArrayInputStream(outputStream.toByteArray())) {
            EpubReader epubReader = new EpubReader();

            Book book = epubReader.readEpub(in);
            assertEquals(twStorydata.getTwPassagedata().size() + 1, book.getResources().getAll().size());

            assertEquals(expectedTitle, book.getMetadata().getFirstTitle());
            assertEquals(expectedLanguage, book.getMetadata().getLanguage());
        }
    }

    @Test
    public void getExtension_Default_ReturnsEpub() {
        // Arrange

        // Act
        String result = convertor.getExtension();

        // Assert
        assertEquals("epub", result);
    }
}
