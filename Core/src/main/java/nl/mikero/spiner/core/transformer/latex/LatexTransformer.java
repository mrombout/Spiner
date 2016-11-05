package nl.mikero.spiner.core.transformer.latex;

import nl.mikero.spiner.core.exception.TwineTransformationWriteException;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.latex.model.GamebookLatexDocument;
import nl.mikero.spiner.core.transformer.latex.model.LatexDocument;
import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.Environment;
import nl.mikero.spiner.core.transformer.latex.model.command.RawTexCommand;
import nl.mikero.spiner.core.twine.model.TwPassagedata;
import nl.mikero.spiner.core.twine.model.TwStorydata;
import org.pegdown.LinkRenderer;
import org.pegdown.PegDownProcessor;

import java.io.OutputStream;
import java.util.Objects;

public class LatexTransformer implements Transformer {
    @Override
    public void transform(TwStorydata story, OutputStream outputStream) {
        Objects.requireNonNull(story);
        Objects.requireNonNull(outputStream);

        // create a new document
        LatexDocument book = new GamebookLatexDocument();

        // set document information
        book.addCommand(new AbstractCommand("title").parameters().add(story.getName()).done());
        book.addCommand(new AbstractCommand("author").parameters().add(story.getCreator()).done());

        // renew commands
        book.addCommand(new AbstractCommand("renewcommand").parameters().add("\\gbturntext").add("").done());

        // begin document
        Environment document = new Environment("document");

        // begin titlepage
        Environment titlePage = new Environment("titlepage")
                .addCommand(new AbstractCommand("ThisCenterWallpaper").parameters().add("1.09").add("cover.png").done())
                .addCommand(new AbstractCommand("null"))
                .addCommand(new AbstractCommand("newpage"));
        document.addCommand(titlePage);

        // gamebook settings
        document.addCommand(new AbstractCommand("gbheader"));
        document.addCommand(new AbstractCommand("pagenumbering").parameters().add("arabic").done());

        // passages
        for(TwPassagedata passage : story.getTwPassagedata()) {
            String passageContent = transformPassageTextToLatex(passage.getValue());

            document.addCommand(new AbstractCommand("gbsection").parameters().add(passage.getName()).done());
            document.addCommand(new RawTexCommand(passageContent));
        }

        try {
            // write to stream
            LatexWriter latexWriter = new LatexWriter();
            latexWriter.write(book, outputStream);
        } catch(Exception e) {
            throw new TwineTransformationWriteException("Could not write transformed input to output stream.", e);
        }
    }

    private String transformPassageTextToLatex(String passageText) {
        return passageText.replace("[[", "\\gbturn{").replace("]]", "}");
    }
}
