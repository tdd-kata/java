package org.xpdojo.demo.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.xpdojo.demo.http.HttpResponse;
import org.xpdojo.demo.http.HttpStatus;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 루트 경로("/")의 HTTP 요청을 처리합니다.
 */
public class ContextRootHandler implements HttpHandler {
    private static final Logger logger = Logger.getGlobal();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        final String method = exchange.getRequestMethod();
        final String path = exchange.getRequestURI().getPath();

        // RSPEC-3457:Printf-style format strings should be used correctly
        logger.log(Level.FINE, () -> method + " " + path);

        if (!path.equals("/")) {
            HttpResponse.text(exchange, HttpStatus.NOT_FOUND);
            return;
        }

        HttpResponse.text(exchange, HttpStatus.OK, "<h1>Hello, World!</h1>");
    }
}
