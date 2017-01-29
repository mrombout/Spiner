package nl.mikero.spiner.core;

import com.google.inject.Inject;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import cucumber.runtime.java.guice.ScenarioScoped;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubReader;
import org.junit.Assert;
import sun.misc.IOUtils;

@ScenarioScoped
public class MyStepDefinitions {
    @Inject
    private TransformService transformerService;

    @Inject
    private TwineStoryEpubTransformer epubTransformer;

    @Inject
    private EpubReader epubReader;

    private InputStream givenTwineStory;
    private Book actualBook;

    @Given("^I have the Twine story \"([^\"]*)\"$")
    public void iHaveTheTwineStory(String twineFile) throws Throwable {
        givenTwineStory = MyStepDefinitions.class.getClassLoader().getResourceAsStream(twineFile);
    }

    @When("^I transform it to \"([^\"]*)\"$")
    public void iTransformItTo(String format) throws Throwable {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        transformerService.transform(givenTwineStory, outputStream, epubTransformer);

        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        actualBook = epubReader.readEpub(inputStream);
    }

    @Then("^page \"([^\"]*)\" should look like \"([^\"]*)\" in \"([^\"]*)\"$")
    public void pageShouldLookLikeIn(String actualPage, String expectedPage, String expectedEpubFile) throws Throwable {
        InputStream expectedInputStream = MyStepDefinitions.class.getClassLoader().getResourceAsStream(expectedEpubFile);
        Book expectedBook = epubReader.readEpub(expectedInputStream);

        Resource actualFirst = actualBook.getResources().getByHref(actualPage + ".xhtml");
        Resource expectedFirst = expectedBook.getResources().getByHref(expectedPage + ".xhtml");

        String actualData = new String(actualFirst.getData());
        String expectedData = new String(expectedFirst.getData());

        Assert.assertNotEquals("", expectedData);
        Assert.assertNotEquals("", actualData);
        Assert.assertEquals(expectedData, actualData);
    }
}
