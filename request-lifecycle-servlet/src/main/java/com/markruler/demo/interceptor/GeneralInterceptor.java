package com.markruler.demo.interceptor;

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

    /**
     * @see <a href="https://meetup.toast.com/posts/44">Spring Interceptor(혹은 Servlet Filter)에서 POST 방식으로 전달된 JSON 데이터 처리하기</a>
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("Pre handle method has been called");

        /**
         * @see org.springframework.web.servlet.DispatcherServlet#doDispatch(HttpServletRequest, HttpServletResponse)
         */
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
