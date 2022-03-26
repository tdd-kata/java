package org.xpdojo.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MultipartController 클래스")
@WebMvcTest(MultipartController.class)
class MultipartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Nested
    @DisplayName("POST /upload")
    class Describe_upload {

        @Nested
        @DisplayName("file 내용이 있을 경우")
        class Context_with_file {

            @Test
            @DisplayName("file 내용을 응답한다")
            void should_respond_file_contents() throws Exception {

                final String fileContent = "Hello, World!";
                final MockMultipartFile file =
                        new MockMultipartFile(
                                "file",
                                "hello.txt",
                                MediaType.TEXT_PLAIN_VALUE,
                                fileContent.getBytes()
                        );

                mockMvc.perform(multipart("/upload").file(file))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(fileContent)))
                ;
            }

        }

        @Nested
        @DisplayName("file은 있지만 내용이 없을 경우")
        class Context_with_empty_file {

            @Test
            @DisplayName("400 BAD REQUEST 상태 코드를 응답한다")
            void whenFileUploaded_thenVerifyStatus2() throws Exception {
                final MockMultipartFile file =
                        new MockMultipartFile(
                                "file",
                                "hello.txt",
                                MediaType.TEXT_PLAIN_VALUE,
                                new byte[0]
                        );

                mockMvc.perform(multipart("/upload").file(file))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString("File is empty.")))
                ;
            }
        }

        @Nested
        @DisplayName("file 파라미터가 비었을 경우")
        class Context_with_empty_param {

            @Test
            @DisplayName("400 BAD REQUEST 상태 코드를 응답한다")
            void whenFileUploaded_thenVerifyStatus3() throws Exception {
                mockMvc.perform(multipart("/upload"))
                        .andExpect(status().isBadRequest())
                ;
            }
        }
    }
}
