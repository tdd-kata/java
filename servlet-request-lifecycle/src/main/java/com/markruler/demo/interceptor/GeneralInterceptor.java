package com.markruler.demo.interceptor;

import com.markruler.demo.filter.MyHttpServletRequestWrapper;
import com.markruler.demo.util.HttpServletRequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class GeneralInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(GeneralInterceptor.class.getName());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final MyHttpServletRequestWrapper wrappedRequest = new MyHttpServletRequestWrapper(request);
        log.info("Pre handle method has been called");
        log.info("User IP address: {}", HttpServletRequestUtil.getRemoteAddress(wrappedRequest));
        log.info("Request Params: {}", HttpServletRequestUtil.getRequestParams(wrappedRequest));
        log.info("Request Payload: {}", HttpServletRequestUtil.getPayLoad(wrappedRequest));
        log.info("Exiting Pre handle method");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("Post handle method has been called");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception exception) throws Exception {
        log.info("After Completion method has been called");
    }
}
