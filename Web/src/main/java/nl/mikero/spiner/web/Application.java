package nl.mikero.spiner.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;

import static spark.Spark.*;

import static spark.Spark.port;

public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }

    private void start() {
        // Configure Spark
        port(4568);

        // Set up routes
        get("/", (req, res) -> getClass().getClassLoader().getResourceAsStream("index.html"));
    }
}
