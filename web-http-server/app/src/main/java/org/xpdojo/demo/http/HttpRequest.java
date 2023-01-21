package org.xpdojo.demo.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * 클라이언트의 HTTP 요청을 나타냅니다.
 */
public class HttpRequest {
    private final HttpExchange exchange;

    public HttpRequest(final HttpExchange exchange) {
        this.exchange = exchange;
    }

    public String body() {
        var inputStream = exchange.getRequestBody();
        var inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        return new BufferedReader(inputStreamReader)
            .lines()
            .collect(Collectors.joining(System.lineSeparator()));
    }
}
