package nl.mikero.spiner.core.transformer.latex;

import nl.mikero.spiner.core.transformer.latex.model.LatexDocument;

import java.io.*;

public class LatexWriter {
    public void write(LatexDocument document, OutputStream outputStream) throws IOException {
        Writer writer = new OutputStreamWriter(outputStream, "UTF-8");
        writer.write(document.toString());
        writer.close();
    }
}
