package org.xpdojo.spring.beanscope;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

class WebRequestTest {

    @Test
    @DisplayName("web scope 중 하나인 request scope 의 경우")
    void sut_request() {
        // HTTP 요청 하나가 들어오고 나갈 때까지 유지되는 스코프
        // 각 HTTP 요청마다 별도의 빈 인스턴스가 생성되고 관리된다.
        assertThat(WebApplicationContext.SCOPE_REQUEST).isEqualTo("request");

        // HTTP Session과 동일한 생명주기를 가지는 스코프
        assertThat(WebApplicationContext.SCOPE_SESSION).isEqualTo("session");

        // 서블릿 컨텍스트(ServletContext)와 동일한 생명주기를 가지는 스코프
        assertThat(WebApplicationContext.SCOPE_APPLICATION).isEqualTo("application");
        assertThat(WebApplicationContext.SERVLET_CONTEXT_BEAN_NAME).isEqualTo("servletContext"); // scope 아님
    }
}
