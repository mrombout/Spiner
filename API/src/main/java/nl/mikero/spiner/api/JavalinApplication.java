package nl.mikero.spiner.api;

import io.javalin.Javalin;

public class JavalinApplication {
    public static void main(String[] args) {
        Javalin application = Javalin.create().start(7000);
        application.post("/:filename.epub", ctx -> ctx.result("Hello World!"));
    }
}
