package org.xpdojo.demo;

import com.sun.net.httpserver.HttpServer;
import org.xpdojo.demo.handler.ContextRootHandler;
import org.xpdojo.demo.handler.TaskHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final int PORT_NUMBER = 8080;
    private static final Logger logger = Logger.getGlobal();

    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        logger.setLevel(Level.FINE);

        var consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.FINE);
        logger.addHandler(consoleHandler);

        final var address = new InetSocketAddress(PORT_NUMBER);

        logger.log(Level.FINE, new App().getGreeting());
        logger.log(Level.FINE, "HTTP server started on : {0}", Long.toString(address.getPort()));

        try {
            var httpServer = HttpServer.create();
            httpServer.bind(address, 0);

            // http -v :8080
            httpServer.createContext("/", new ContextRootHandler());
            // http -v post ":8080/tasks" title=java
            // http -v get ":8080/tasks"
            httpServer.createContext("/tasks", new TaskHandler());

            httpServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
