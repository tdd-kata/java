package org.xpdojo.designpatterns._02_structural_patterns._01_adapter;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.InvocableHandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @see <a href="https://github.com/spring-projects/spring-framework/blob/d57bc176f220aa9b70d69fb79ec86efbd3f99103/spring-webmvc/src/test/java/org/springframework/web/servlet/mvc/method/annotation/RequestMappingHandlerAdapterTests.java">RequestMappingHandlerAdapterTests.java</a>
 */
class HandlerAdapterTest {

    @Test
    void sut_spring_handler_adapter() throws Exception {
        StaticApplicationContext applicationContext = new StaticApplicationContext();
        RequestMappingHandlerAdapter handlerAdapter = new RequestMappingHandlerAdapter();
        handlerAdapter.setApplicationContext(applicationContext);

        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/");
        MockHttpServletResponse response = new MockHttpServletResponse();

        HandlerMethod handlerMethod = handlerMethod(new SimpleController(), "handle");
        handlerAdapter.setCacheSeconds(100);
        handlerAdapter.afterPropertiesSet();

        handlerAdapter.handle(request, response, handlerMethod);
        assertThat(response.getHeader("Cache-Control"))
                .contains("max-age");
    }

    private HandlerMethod handlerMethod(Object handler, String methodName, Class<?>... paramTypes) throws Exception {
        Method method = handler.getClass().getDeclaredMethod(methodName, paramTypes);
        return new InvocableHandlerMethod(handler, method);
    }


    @SuppressWarnings("unused")
    private static class SimpleController {

        @ModelAttribute
        public void addAttributes(Model model) {
            model.addAttribute("attr1", "lAttr1");
        }

        public String handle() {
            return null;
        }

        public ResponseEntity<Map<String, String>> handleWithResponseEntity() {
            return new ResponseEntity<>(Collections.singletonMap("foo", "bar"), HttpStatus.OK);
        }

        public ResponseEntity<String> handleBadRequest() {
            return new ResponseEntity<>("body", HttpStatus.BAD_REQUEST);
        }

    }
}
