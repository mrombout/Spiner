package nl.mikero.spiner.core.transformer.latex;

import nl.mikero.spiner.core.exception.TwineTransformationWriteException;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.latex.model.GamebookLatexDocument;
import nl.mikero.spiner.core.transformer.latex.model.LatexDocument;
import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.Environment;
import nl.mikero.spiner.core.transformer.latex.model.command.RawTexCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.WhitelineCommand;
import nl.mikero.spiner.core.twine.model.TwPassagedata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;

import java.io.OutputStream;
import java.util.Objects;

public class LatexTransformer implements Transformer {
    private static final String EXTENSION = "tex";

    @Override
    public void transform(TwStorydata story, OutputStream outputStream) {
        Objects.requireNonNull(story);
        Objects.requireNonNull(outputStream);

        // create a new document
        LatexDocument book = new GamebookLatexDocument();

        // set document information
        book.addCommand(new AbstractCommand("title").parameters().add(story.getName()).done());
        book.addCommand(new AbstractCommand("author").parameters().add(story.getCreator()).done());
        book.addCommand(new WhitelineCommand());

        // begin document
        Environment document = new Environment("document");
        book.addCommand(document);

        // gamebook settings
        document.addCommand(new AbstractCommand("gbheader"));
        document.addCommand(new AbstractCommand("pagenumbering").parameters().add("arabic").done());
        document.addCommand(new WhitelineCommand());

        // passages
        for(TwPassagedata passage : story.getTwPassagedata()) {
            String passageContent = transformPassageTextToLatex(passage.getValue());

            document.addCommand(new AbstractCommand("gbsection").parameters().add(passage.getName()).done());
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

    @Override
    public String getExtension() {
        return EXTENSION;
    }

    private String transformPassageTextToLatex(String passageText) {
        String optionsLinkTemplate      = new AbstractCommand("gbitem").parameters().add("$1").add("$2").done().toString().replace("\\", "\\\\");
        String optionsLabelLinkTemplate = new AbstractCommand("gbitem").parameters().add("$1").add("$1").done().toString().replace("\\", "\\\\");

        String linkTemplate      = String.format("$1 (%s)", new AbstractCommand("gbturn").parameters().add("$1").done().toString().replace("\\", "\\\\"));
        String labelLinkTemplate = String.format("$1 (%s)", new AbstractCommand("gbturn").parameters().add("$2").done().toString().replace("\\", "\\\\"));

        passageText = passageText.replaceAll("-\\[\\[(.*?)\\|(.*?)\\]\\]", optionsLinkTemplate);
        passageText = passageText.replaceAll("-\\[\\[(.*?)\\]\\]", optionsLabelLinkTemplate);
        passageText = passageText.replaceAll("\\[\\[(.*?)\\|(.*?)\\]\\]", labelLinkTemplate);
        passageText = passageText.replaceAll("\\[\\[(.*?)\\]\\]", linkTemplate);

        return passageText;
    }
}
