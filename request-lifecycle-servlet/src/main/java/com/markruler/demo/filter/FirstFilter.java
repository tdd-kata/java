package com.markruler.demo.filter;

import com.markruler.demo.util.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @see org.apache.catalina.core.ApplicationFilterChain#doFilter(ServletRequest, ServletResponse)
 */
@Component
@org.springframework.core.annotation.Order(1)
public class FirstFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("Request first filter...{}", request.getServerName());

        final RereadableRequestWrapper wrappedRequest = new RereadableRequestWrapper((HttpServletRequest)request);
        log.info("User IP address: {}", HttpServletRequestUtil.getRemoteAddress(wrappedRequest));
        log.info("Request Params: {}", HttpServletRequestUtil.getRequestParams(wrappedRequest));
        log.info("Request Payload: {}", HttpServletRequestUtil.getPayLoad(wrappedRequest)); // request.getInputStream()

        chain.doFilter(wrappedRequest, response);

        log.info("Response first filter...{}", response.getCharacterEncoding());
    }
}
