package nl.mikero.spiner.core.transformer.latex;

import nl.mikero.spiner.core.transformer.latex.pegdown.ToLatexSerializer;
import nl.mikero.spiner.core.twine.model.TwPassagedata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
//import org.pegdown.PegDownProcessor;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;

@Ignore
public class LatexTransformerTest {
    private LatexTransformer transformer;

//    private PegDownProcessor mockPegDownProcessor;
    private ToLatexSerializer mockToLatexSerializer;

    @Before
    public void setUp() {
//        mockPegDownProcessor = Mockito.mock(PegDownProcessor.class);
        mockToLatexSerializer = Mockito.mock(ToLatexSerializer.class);
//        transformer = new LatexTransformer(mockPegDownProcessor, mockToLatexSerializer);
    }

    @Test(expected = NullPointerException.class)
    public void construct_NullProcessor_ThrowsNullPointerException() {
        // Arrange

        // Act
//        new LatexTransformer(null, mockToLatexSerializer);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void construct_NullSerializer_ThrowsNullPointerException() {
        // Arrange

        // Act
//        new LatexTransformer(mockPegDownProcessor, null);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void transform_NullStory_ThrowsNullPointerException() {
        // Arrange
        OutputStream mockOutputStream = Mockito.mock(OutputStream.class);

        // Act
        transformer.transform(null, mockOutputStream);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void transform_NullOutputStream_ThrowsNullPointerException() {
        // Arrange
        TwStorydata story = Mockito.mock(TwStorydata.class);

        // Act
        transformer.transform(story, null);

        // Assert
    }

    @Test
    public void transform_StoryWithTitleAndAuthor_TitleAndAuthorCorrectlySet() {
        // Arrange
        String expectedName = "Foo";
        String expectedAuthor = "Bar";

        TwStorydata story = new TwStorydata();
        story.setName(expectedName);
        story.setCreator(expectedAuthor);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Act
        transformer.transform(story, outputStream);

        // Assert
        String output = outputStream.toString();

        Assert.assertThat(output, containsString(String.format("\\title{%s}", expectedName)));
        Assert.assertThat(output, containsString(String.format("\\author{%s}", expectedAuthor)));
    }

    @Test
    public void transform_ThreePassages_GbSectionAddedForEach() {
        // Arrange
        String expectedPassage1Name = "Foo";
        String expectedPassage2Name = "Bar";
        String expectedPassage3Name = "Buzz";

        TwStorydata story = new TwStorydata();
        story.setName("Widget");
        story.setCreator("Gadget");

        TwPassagedata passage1 = new TwPassagedata();
        passage1.setName(expectedPassage1Name);
        passage1.setValue(expectedPassage1Name);
        TwPassagedata passage2 = new TwPassagedata();
        passage2.setName(expectedPassage2Name);
        passage2.setValue(expectedPassage2Name);
        TwPassagedata passage3 = new TwPassagedata();
        passage3.setName(expectedPassage3Name);
        passage3.setValue(expectedPassage3Name);

        story.getTwPassagedata().add(passage1);
        story.getTwPassagedata().add(passage2);
        story.getTwPassagedata().add(passage3);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

//        Mockito.when(mockToLatexSerializer.toLatex(ArgumentMatchers.any())).thenReturn("Foo");

        // Act
        transformer.transform(story, outputStream);

        // Assert
        String output = outputStream.toString();
        Assert.assertThat(output, containsString(String.format("\\gbsection{%s}", expectedPassage1Name)));
        Assert.assertThat(output, containsString(String.format("\\gbsection{%s}", expectedPassage2Name)));
        Assert.assertThat(output, containsString(String.format("\\gbsection{%s}", expectedPassage3Name)));
    }

    @Test
    public void getExtension_Default_ReturnsTex() {
        // Arrange

        // Act
        String result = transformer.getExtension();

        // Assert
        assertEquals("tex", result);
    }
}
