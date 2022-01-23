package com.markruler.demo.util;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Scanner;

public abstract class HttpServletRequestUtil {

    public static String getRemoteAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public static String getPayLoad(HttpServletRequest request) {
        final String method = request.getMethod().toUpperCase();
        if ("POST".equals(method) || "PUT".equals(method)) {
            return extractPostRequestBody(request);
        }
        return "Not a POST or PUT method";
    }

    public static String getRequestParams(HttpServletRequest request) {
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

    private static String extractPostRequestBody(HttpServletRequest request) {
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            Scanner s = null;
            try {
                s = new Scanner(request.getInputStream(), StandardCharsets.UTF_8).useDelimiter("\\A");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s.hasNext() ? s.next() : "";
        }
        return "";
    }
}
