package nl.mikero.spiner.web;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import nl.mikero.spiner.core.exception.TwineTransformationFailedException;
import nl.mikero.spiner.core.transformer.TransformService;
import nl.mikero.spiner.core.transformer.epub.TwineStoryEpubTransformer;
import nl.mikero.spiner.web.inject.TwineModule;

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

    public static void main(String[] args) {
        SparkApplication application = new SparkApplication();
        application.start();
    }

    private void start() {
        final Injector injector = Guice.createInjector(new TwineModule());
        injector.injectMembers(this);

        // Configure Spark
        port(4567);

        // Set up static resources
        staticFiles.location("/public");

        // Set up routes
        get("/", (req, res) -> getClass().getClassLoader().getResourceAsStream("index.html"));

        post("/:filename.epub", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream input = req.raw().getPart("file").getInputStream();
                OutputStream output = res.raw().getOutputStream()) {
                transformService.transform(input, output, epubTransformer);

                res.type("application/epub+zip");
                res.header("Content-Disposition", String.format("attachment; filename=%s.epub", req.params(":filename")));
                return res.raw();
            } catch (TwineTransformationFailedException e) {
                return "Oh no! An error occured!";
            }
        });
    }
}
