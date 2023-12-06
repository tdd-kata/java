package com.demo.springbootopenfeign.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Tag("end-to-end")
    @DisplayName("Controller를 Mocking해서 실제 외부 서버에 요청하는 테스트")
    // @Disabled
    void test_hello_with_real_server() throws Exception {

        // Controller 부분을 Mocking
        final MockHttpServletRequestBuilder request = get("/hello");

        mockMvc
                .perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", containsString("hi markruler")))
                .andDo(print());
    }

}
