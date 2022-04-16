package org.xpdojo.spring.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.xpdojo.spring.ApiDocumentUtils;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @see <a href="https://beanvalidation.org/1.0/spec/">JSR 303: Bean Validation v1.0</a>
 * @see <a href="https://jcp.org/en/jsr/detail?id=303">JSR 303: Bean Validation v1.0</a>
 * @see <a href="https://jcp.org/en/jsr/detail?id=349">JSR 349: Bean Validation v1.1</a>
 * @see <a href="https://jcp.org/en/jsr/detail?id=380">JSR 380: Bean Validation v2.0</a>
 */
@DisplayName("ValidationController 클래스")
@WebMvcTest(ValidationController.class)
@AutoConfigureRestDocs
class ValidationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("GET /validation/get")
    class describe_validation_get {

        @Nested
        @DisplayName("유효성 검사가 실패한다면")
        class context_invalid_request {

            @Test
            @DisplayName("400 Bad Request를 응답한다")
            void should_respond_400_bad_request() throws Exception {
                mockMvc.perform(MockMvcRequestBuilders.get("/validation/get"))
                        .andExpect(status().isBadRequest());
            }
        }

        @Nested
        @DisplayName("유효성 검사가 통과한다면")
        class context_valid_request {

            @Test
            @DisplayName("요청 데이터를 그대로 응답한다")
            void should_respond_request() throws Exception {
                final UserDto user = UserDto.builder()
                        .email("markruler@xpdojo.org")
                        .name("test-markruler")
                        .age(20)
                        .build();

                given(userService.findUser(any(UserDto.class)))
                        .willReturn(user);

                MockHttpServletRequestBuilder request =
                        RestDocumentationRequestBuilders.get("/validation/get")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(user));

                mockMvc.perform(request)
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.name", containsString(user.getName())))
                        .andDo(MockMvcRestDocumentation.document("users-get",
                                ApiDocumentUtils.getDocumentRequest(),
                                ApiDocumentUtils.getDocumentResponse(),
                                // pathParameters(
                                //         parameterWithName("id").description("식별자")),
                                responseFields(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("age").type(JsonFieldType.NUMBER).description("나이")
                                        // subsectionWithPath("book").type(JsonFieldType.OBJECT).description("소장 도서")
                                )));

                verify(userService).findUser(any(UserDto.class));
            }
        }
    }
}
