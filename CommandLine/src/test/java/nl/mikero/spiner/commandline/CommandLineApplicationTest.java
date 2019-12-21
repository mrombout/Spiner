package nl.mikero.spiner.commandline;

//import com.beust.jcommander.MissingCommandException;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.epub.embedder.EmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.HashEmbedderFactory;
import nl.mikero.spiner.core.transformer.epub.embedder.ResourceEmbedder;
import nl.mikero.spiner.core.twine.TwineArchiveParser;
import nl.mikero.spiner.core.twine.TwineArchiveRepairer;
import nl.mikero.spiner.core.twine.extended.ExtendTwineXmlTransformer;
import nl.mikero.spiner.core.twine.markdown.MarkdownProcessor;
import nl.mikero.spiner.core.twine.markdown.PegdownTransitionMarkdownRenderParser;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CommandLineApplicationTest {
    private CommandLineApplication application;

    @Before
    public void setUp() {
        GradleVersionService gradleVersionService = new GradleVersionService();
        TwineArchiveRepairer twineArchiveRepairer = new TwineArchiveRepairer();
        ExtendTwineXmlTransformer extendTwineXmlTransformer = new ExtendTwineXmlTransformer();
        TwineArchiveParser twineArchiveParser = new TwineArchiveParser();
        TransformService transformService = new TransformService(twineArchiveRepairer, extendTwineXmlTransformer, twineArchiveParser);
        MarkdownProcessor markdownProcessor = new PegdownTransitionMarkdownRenderParser();
        EmbedderFactory embedderFactory = new HashEmbedderFactory(DigestUtils.getSha256Digest());
        ResourceEmbedder resourceEmbedder = new ResourceEmbedder(embedderFactory);
        TwineStoryEpubTransformer twineStoryEpubTransformer = new TwineStoryEpubTransformer(markdownProcessor, resourceEmbedder);

        application = new CommandLineApplication(gradleVersionService, transformService, twineStoryEpubTransformer);
    }

    @Test
    public void execute_NoArguments_RunHelpCommand() {
        // Arrange

        // Act
        application.execute(new String[] {});

        // Assert
        // TODO: Assert invalid format message is being printed
        // TODO: Assert help is being printed to System.out
    }

    @Test
    public void execute_HelpParameter_RunHelpCommand() {
        // Arrange

        // Act
        application.execute(new String[] {"--help"});

        // Assert
        // TODO: Assert help is being printed to System.out
    }

    @Test
    public void execute_VersionParameter_RunVersionCommand() {
        // Arrange

        // Act
        application.execute(new String[] {"--version"});

        // Assert
        // TODO: Assert version info is being printed to System.out
    }

    @Test
    public void execute_TransformParameter_RunTransformCommand() {
        // Arrange

        // Act
        application.execute(new String[] { "transform", "--input", "MyStory.html" });

        // Assert
        // TODO: Assert transform is being called with the right parameters
    }

    @Test
    public void execute_InvalidCommand_Unknown() {
        // Arrange

        // Act
        application.execute(new String[] {"thisdoesnotexist"});

        // Assert
        // TODO: Assert invalid format message is being printed
        // TODO: Assert help is being printed to System.out
    }
}
