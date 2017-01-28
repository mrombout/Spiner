package nl.mikero.spiner.core.transformer.latex;

import com.google.inject.Inject;
import nl.mikero.spiner.core.exception.TwineTransformationWriteException;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.latex.model.GamebookLatexDocument;
import nl.mikero.spiner.core.transformer.latex.model.LatexDocument;
import nl.mikero.spiner.core.transformer.latex.model.command.*;
import nl.mikero.spiner.core.transformer.latex.pegdown.ToLatexSerializer;
import nl.mikero.spiner.core.twine.model.TwPassagedata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

import java.io.OutputStream;
import java.util.Objects;

/**
 * Transforms a Twine story into a LaTeX document based on the <code>gamebook</code> package.
 */
public class LatexTransformer implements Transformer {
    private static final String EXTENSION = "tex";

    private final PegDownProcessor processor;
    private final ToLatexSerializer serializer;

    /**
     * Constructs a new LatexTransformer.
     *
     * @param processor pegdown processor to use
     * @param serializer latex serializer to use
     */
    @Inject
    public LatexTransformer(final PegDownProcessor processor, final ToLatexSerializer serializer) {
        this.processor = Objects.requireNonNull(processor);
        this.serializer = Objects.requireNonNull(serializer);
    }

    /**
     * Transforms a Twine story into a LaTeX document.
     *
     * @param story story to transform
     * @param outputStream output stream to write transformed story to
     */
    @Override
    public void transform(final TwStorydata story, final OutputStream outputStream) {
        Objects.requireNonNull(story);
        Objects.requireNonNull(outputStream);

        // create a new document
        LatexDocument book = new GamebookLatexDocument();

        // add required packages
        book.addCommand(new UsePackageCommand().parameters().add("hyperref").done());
        book.addCommand(new UsePackageCommand().parameters().add("csquotes").done());
        book.addCommand(new UsePackageCommand().options().add("ulem").done().parameters().add("normalem").done());

        // set document information
        book.addCommand(new BasicCommand("title").parameters().add(story.getName()).done());
        book.addCommand(new BasicCommand("author").parameters().add(story.getCreator()).done());
        book.addCommand(new WhitelineCommand());

        // begin document
        Environment document = new Environment("document");
        book.addCommand(document);

        // gamebook settings
        document.addCommand(new BasicCommand("gbheader"));
        document.addCommand(new BasicCommand("pagenumbering").parameters().add("arabic").done());
        document.addCommand(new WhitelineCommand());

        // passages
        for(TwPassagedata passage : story.getTwPassagedata()) {
            String passageContent = transformPassageTextToLatex(passage.getValue());

            document.addCommand(new BasicCommand("gbsection").parameters().add(passage.getName()).done());
            document.addCommand(new RawTexCommand(passageContent));
            document.addCommand(new WhitelineCommand());
        }

        try {
            // write to stream
            LatexWriter latexWriter = new LatexWriter();
            latexWriter.write(book, outputStream);
        } catch(Exception e) {
            throw new TwineTransformationWriteException("Could not write transformed input to output stream.", e);
        }
    }

    /**
     * @return tex extension (tex)
     */
    @Override
    public String getExtension() {
        return EXTENSION;
    }

    /**
     * Transform passage markdown text to LaTex.
     *
     * @param passageText passage markdown text
     * @return converted LaTeX string
     */
    private String transformPassageTextToLatex(final String passageText) {
        RootNode node = processor.parseMarkdown(passageText.toCharArray());
        return serializer.toLatex(node);
    }
}
