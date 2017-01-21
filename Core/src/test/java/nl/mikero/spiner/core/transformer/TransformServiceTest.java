package nl.mikero.spiner.core.transformer;

import nl.mikero.spiner.core.exception.TwineTransformationFailedException;
import nl.mikero.spiner.core.twine.TwineArchiveParser;
import nl.mikero.spiner.core.twine.TwineRepairer;
import nl.mikero.spiner.core.twine.extended.ExtendTwineXmlTransformer;
import nl.mikero.spiner.core.twine.model.TwStoriesdata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

public class TransformServiceTest {
    private TransformService service;

    private TwineRepairer mockTwineRepairer;
    private ExtendTwineXmlTransformer mockExtendTwineXmlTransformer;
    private TwineArchiveParser mockTwineArchiveParser;

    @Before
    public void setUp() {
        mockTwineRepairer = Mockito.mock(TwineRepairer.class);
        mockExtendTwineXmlTransformer = Mockito.mock(ExtendTwineXmlTransformer.class);
        mockTwineArchiveParser = Mockito.mock(TwineArchiveParser.class);

        service = new TransformService(mockTwineRepairer, mockExtendTwineXmlTransformer, mockTwineArchiveParser);
    }

    @Test(expected = NullPointerException.class)
    public void transform_NullInputStream_ThrowsNullPointerException() {
        // Arrange
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        Transformer transformer = Mockito.mock(Transformer.class);

        // Act
        service.transform(null, outputStream, transformer);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void transform_NullOutputStream_ThrowsNullPointerException() {
        // Arrange
        InputStream inputStream = Mockito.mock(InputStream.class);
        Transformer transformer = Mockito.mock(Transformer.class);

        // Act
        service.transform(inputStream, null, transformer);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void transform_NullTransformer_ThrowsNullPointerException() throws JAXBException, SAXException {
        // Arrange
        InputStream inputStream = Mockito.mock(InputStream.class);
        OutputStream outputStream = Mockito.mock(OutputStream.class);

        // Act
        service.transform(inputStream, outputStream, null);

        // Assert
    }

    @Test
    public void transform_ThreeStories_TransformerCalledForEach() throws JAXBException, SAXException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("Foo".getBytes());
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        Transformer transformer = Mockito.mock(Transformer.class);

        TwStorydata story1 = new TwStorydata();
        TwStorydata story2 = new TwStorydata();
        TwStorydata story3 = new TwStorydata();

        Mockito.when(mockTwineArchiveParser.parse(Matchers.any())).thenAnswer(invocation -> {
            TwStoriesdata data = new TwStoriesdata();

            data.getTwStorydata().add(story1);
            data.getTwStorydata().add(story2);
            data.getTwStorydata().add(story3);

            return data;
        });

        // Act
        service.transform(inputStream, outputStream, transformer);

        // Assert
        Mockito.verify(transformer, Mockito.times(1)).transform(story1, outputStream);
        Mockito.verify(transformer, Mockito.times(1)).transform(story2, outputStream);
        Mockito.verify(transformer, Mockito.times(1)).transform(story3, outputStream);
    }

    @Test
    public void transform_NoStories_TransformerNeverCalled() throws JAXBException, SAXException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("Foo".getBytes());
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        Transformer transformer = Mockito.mock(Transformer.class);

        Mockito.when(mockTwineArchiveParser.parse(Matchers.any())).thenAnswer(invocation -> new TwStoriesdata());

        // Act
        service.transform(inputStream, outputStream, transformer);

        // Assert
        Mockito.verify(transformer, Mockito.never()).transform(Matchers.any(), Matchers.eq(outputStream));
    }

    @Test
    public void transform_InvalidInputSource_ThrowsTwineTransformationFailedException() throws IOException, TransformerException, ParserConfigurationException, SAXException, JAXBException {
        // Arrange
        InputStream inputStream = new ByteArrayInputStream("Foo".getBytes());
        OutputStream outputStream = Mockito.mock(OutputStream.class);
        Transformer transformer = Mockito.mock(Transformer.class);

        Mockito.when(mockTwineArchiveParser.parse(Matchers.any())).thenAnswer(invocation -> {
            throw new TooManyListenersException();
        });

        // Act
        try {
            service.transform(inputStream, outputStream, transformer);
            Assert.fail(String.format("'%s' was not thrown", TwineTransformationFailedException.class.getSimpleName()));
        } catch (Exception e) {
            // Assert
            assertThat(e, instanceOf(TwineTransformationFailedException.class));
            assertThat(e.getCause(), instanceOf(TooManyListenersException.class));
        }
    }
}
