package nl.mikero.spiner.api;

import ro.pippo.core.Application;
import ro.pippo.core.Pippo;

public class PippoApplication {

    public static void main(String[] args) {
        Pippo pippo = new Pippo();
        pippo.GET("/{filename}.{filetype}", routeContext -> {
            String filename = routeContext.getParameter("filename").toString();
            String filetype = routeContext.getParameter("filetype").toString();

            routeContext.send("Hello World");
        });
        pippo.start();
    }
}
