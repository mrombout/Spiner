package nl.mikero.spiner.api;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.mikero.spiner.api.inject.TwineModule;
import nl.mikero.spiner.core.exception.TwineTransformationFailedException;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.Transformer;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.core.transformer.latex.LatexTransformer;

import javax.servlet.MultipartConfigElement;
import java.io.InputStream;
import java.io.OutputStream;

import static spark.Spark.*;

import static spark.Spark.port;

public class SparkApplication {

    @Inject
    private TransformService transformService;
    @Inject
    private TwineStoryEpubTransformer epubTransformer;
    @Inject
    private LatexTransformer latexTransformer;

    public static void main(String[] args) {
        SparkApplication application = new SparkApplication();
        application.start();
    }

    private void start() {
        final Injector injector = Guice.createInjector(new TwineModule());
        injector.injectMembers(this);

        // Configure Spark
        port(4567);

        // Set up routes
        post("/:filename.epub", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream input = req.raw().getPart("file").getInputStream();
                OutputStream output = res.raw().getOutputStream()) {
                transformService.transform(input, output, getTransformer(req.params("filetype")));

                res.type("application/epub+zip");
                res.header("Content-Disposition", "attachment; filename=mytest.epub");
                return res.raw();
            } catch (TwineTransformationFailedException e) {
                return "Oh no! An error occured!";
            }
        });
    }

    private Transformer getTransformer(String filetype) {
        if(filetype.equals("latex")) {
            return latexTransformer;
        } else if (filetype.equals("epub")) {
            return epubTransformer;
        }

        throw new UnsupportedOperationException(String.format("Transformer for filetype '%s' is not available.", filetype));
    }
}
