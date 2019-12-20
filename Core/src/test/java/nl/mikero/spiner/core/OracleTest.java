package nl.mikero.spiner.core;

import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.epub.embedder.EmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.HashEmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.ResourceEmbedder;
import nl.mikero.spiner.core.twine.TwineArchiveParser;
import nl.mikero.spiner.core.twine.TwinePublishedRepairer;
import nl.mikero.spiner.core.twine.extended.ExtendTwineXmlTransformer;
import nl.mikero.spiner.core.twine.markdown.PegdownTransitionMarkdownRenderParser;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Metadata;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import nl.siegmann.epublib.service.MediatypeService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;
import java.util.function.BiConsumer;

public class OracleTest {
    private String givenTwineStoryFilePath;
    private InputStream givenTwineStory;

    private TransformService transformerService;
    private TwineStoryEpubTransformer twineStoryEpubTransformer;
    private EpubReader epubReader;

    private Book actualBook;

    @Before
    public void setUp() {
        TwinePublishedRepairer publishedRepairer = new TwinePublishedRepairer();
        ExtendTwineXmlTransformer extendTwineXmlTransformer = new ExtendTwineXmlTransformer();
        TwineArchiveParser twineArchiveParser = new TwineArchiveParser();

        transformerService = new TransformService(publishedRepairer, extendTwineXmlTransformer, twineArchiveParser);

        PegdownTransitionMarkdownRenderParser parser = new PegdownTransitionMarkdownRenderParser();
        EmbedderFactory embedderFactory = new HashEmbedderFactory(DigestUtils.getSha256Digest());
        ResourceEmbedder resourceEmbedder = new ResourceEmbedder(embedderFactory);
        twineStoryEpubTransformer = new TwineStoryEpubTransformer(parser, resourceEmbedder);

        epubReader = new EpubReader();
    }

    @After
    public void tearDown() throws IOException {
        if (givenTwineStory != null) {
            givenTwineStory.close();
        }
    }

    @Test
    public void testFeatures() throws IOException {
        givenATwineStory("../example/Features.html");
        whenTransformedToEpub("../example/Features.epub");
        thenItsMetadataShouldMatch("../example/Features.epub");
        thenItsPagesShouldMatch("../example/Features.epub");
        thenItsStylesheetShouldMatch("../example/Features.epub");
    }

    private void givenATwineStory(String twineStoryFilePath) throws FileNotFoundException {
        System.out.println( System.getProperty("user.dir"));
        givenTwineStoryFilePath = twineStoryFilePath;
        givenTwineStory = new FileInputStream(twineStoryFilePath);
    }

    private void whenTransformedToEpub(String targetFile) throws IOException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        OutputStream outputStream = byteOutputStream;
        if (shouldUpdateOracleFile()) {
            outputStream = new TeeOutputStream(outputStream, new FileOutputStream(targetFile));
        }
        transformerService.transform(givenTwineStory, outputStream, twineStoryEpubTransformer);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());
        actualBook = epubReader.readEpub(inputStream);
    }

    private void thenItsPagesShouldMatch(String epubFilePath) throws IOException {
        Book expectedBook = epubReader.readEpub(new FileInputStream(epubFilePath));

        for(Resource expectedResource : expectedBook.getResources().getResourcesByMediaType(MediatypeService.XHTML)) {
            Resource actualResource = actualBook.getResources().getByHref(expectedResource.getHref());
            Assert.assertNotNull(actualResource);
            Assert.assertEquals(new String(expectedResource.getData()), new String(actualResource.getData()));
        }
        Assert.assertEquals(expectedBook.getResources().size(), actualBook.getResources().size());
    }

    private void thenItsMetadataShouldMatch(String epubFilePath) throws IOException {
        Book expectedBook = epubReader.readEpub(new FileInputStream(epubFilePath));

        Metadata expectedMetadata = expectedBook.getMetadata();
        Metadata actualMetadata = actualBook.getMetadata();

        assertListEquals(expectedMetadata.getIdentifiers(), actualMetadata.getIdentifiers(), (expected, actual) -> {
            Assert.assertEquals(expected.getScheme(), actual.getScheme());
            Assert.assertEquals(expected.getValue(), actual.getValue());
        });
        assertListEquals(expectedMetadata.getTitles(), actualMetadata.getTitles(), Assert::assertEquals);
        Assert.assertEquals(expectedMetadata.getLanguage(), actualMetadata.getLanguage());
        assertListEquals(expectedMetadata.getContributors(), actualMetadata.getContributors(), Assert::assertEquals);
        assertListEquals(expectedMetadata.getAuthors(), actualMetadata.getAuthors(), Assert::assertEquals);
        assertListEquals(expectedMetadata.getDates(), actualMetadata.getDates(), (expected, actual) -> {
            Assert.assertEquals(expected.getEvent(), actual.getEvent());
            Assert.assertEquals(expected.getValue(), actual.getValue());
        });
        assertListEquals(expectedMetadata.getDescriptions(), actualMetadata.getDescriptions(), Assert::assertEquals);
        Assert.assertEquals(expectedMetadata.getFormat(), actualMetadata.getFormat());
        assertListEquals(expectedMetadata.getPublishers(), actualMetadata.getPublishers(), Assert::assertEquals);
        assertListEquals(expectedMetadata.getRights(), actualMetadata.getRights(), Assert::assertEquals);
        assertListEquals(expectedMetadata.getSubjects(), actualMetadata.getSubjects(), Assert::assertEquals);
        assertListEquals(expectedMetadata.getTypes(), actualMetadata.getTypes(), Assert::assertEquals);
    }

    private void thenItsStylesheetShouldMatch(String epubFilePath) throws IOException {
        Book expectedBook = epubReader.readEpub(new FileInputStream(epubFilePath));

        for(Resource expectedResource : expectedBook.getResources().getResourcesByMediaType(MediatypeService.CSS)) {
            Resource actualResource = actualBook.getResources().getByHref(expectedResource.getHref());
            Assert.assertNotNull(actualResource);
            Assert.assertEquals(new String(expectedResource.getData()), new String(actualResource.getData()));
        }
        Assert.assertEquals(expectedBook.getResources().size(), actualBook.getResources().size());
    }

    private <T> void assertListEquals(List<T> expectedList, List<T> actualList, BiConsumer<T, T> assertion) {
        for (int i = 0; i < expectedList.size(); i++) {
            T expectedValue = expectedList.get(i);
            T actualValue = actualList.get(i);

            assertion.accept(expectedValue, actualValue);
        }
        Assert.assertEquals(expectedList.size(), actualList.size());
    }

    private boolean shouldUpdateOracleFile() {
        return false;
    }
}
