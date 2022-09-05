package com.markruler.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Scanner;

public abstract class HttpServletRequestUtil {

    private static final Logger log = LoggerFactory.getLogger(HttpServletRequestUtil.class.getName());

    private HttpServletRequestUtil() {
        // Utility Class
    }

    public static String getRemoteAddress(final HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public static String getPayLoad(final HttpServletRequest request) {
        final String method = request.getMethod().toUpperCase();
        if ("POST".equals(method) || "PUT".equals(method)) {
            return extractPostRequestBody(request);
        }
        return "Not a POST or PUT method";
    }

    public static String getRequestParams(final HttpServletRequest request) {
        final StringBuilder params = new StringBuilder();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
            if ("password".equalsIgnoreCase(paramName) || "pwd".equalsIgnoreCase(paramName)) {
                paramValue = "*****";
            }
            params.append(paramName).append(": ").append(paramValue).append(System.lineSeparator());
        }
        return params.toString();

    }

    private static String extractPostRequestBody(final HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner scanner = null;
            try {
                scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
            } catch (IOException e) {
                log.error(e.toString(), e);
            }
            assert scanner != null;
            return scanner.hasNext() ? scanner.next() : "";
        }
        return "";
    }
}
