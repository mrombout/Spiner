package nl.mikero.turntopassage.core.transformer;

import nl.mikero.turntopassage.core.model.TwPassagedata;
import nl.mikero.turntopassage.core.model.TwStoriesdata;
import nl.mikero.turntopassage.core.model.TwStorydata;
import nl.mikero.turntopassage.core.pegdown.plugin.TwineLinkRenderer;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;
import org.junit.Before;
import org.junit.Test;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;

import java.io.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TwineStoryEpubTransformerTest {

    private PegDownProcessor mockPegDownProcessor;
    private TwineLinkRenderer mockTwineLinkRenderer;

    private TwineStoryEpubTransformer convertor;

    @Before
    public void setUp() {
        mockPegDownProcessor = mock(PegDownProcessor.class);
        mockTwineLinkRenderer = mock(TwineLinkRenderer.class);

        this.convertor = new TwineStoryEpubTransformer(mockPegDownProcessor, mockTwineLinkRenderer);
    }

    @Test(expected =  NullPointerException.class)
    public void construct_NullProcessor_ThrowsNullPointerException() {
        // Arrange

        // Act
        new TwineStoryEpubTransformer(null, mockTwineLinkRenderer);

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
        when(mockPegDownProcessor.markdownToHtml(anyString(), any(LinkRenderer.class)))
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

}
