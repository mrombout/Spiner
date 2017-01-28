package nl.mikero.spiner.core.transformer.latex;

import nl.mikero.spiner.core.transformer.latex.model.LatexDocument;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Objects;

/**
 * Writes a {@link LatexDocument} to an {@link OutputStream}.
 */
public class LatexWriter {
    /**
     * Writes the document to the outputStream.
     *
     * @param document document to write to outputStream
     * @param outputStream outputStream to write to
     * @throws IOException if an I/O error occurs
     */
    public final void write(final LatexDocument document, final OutputStream outputStream) throws IOException {
        Objects.requireNonNull(document);
        Objects.requireNonNull(outputStream);

        try(Writer writer = new OutputStreamWriter(outputStream, "UTF-8")) {
            writer.write(document.toString());
        }
    }
}
