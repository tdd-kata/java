package org.xpdojo.spring.multipart;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("MultipartController 클래스")
@WebMvcTest(MultipartController.class)
class MultipartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MultipartService multipartService;

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

                given(multipartService.upload(any(MultipartFile.class)))
                        .willReturn(fileContent);

                mockMvc.perform(multipart("/upload").file(file))
                        .andExpect(status().isOk())
                        .andExpect(content().string(containsString(fileContent)))
                ;
            }

        }

        @Nested
        @DisplayName("file은 있지만 내용이 없을 경우")
        class Context_with_empty_file {
            final String errorMessage = "File is empty.";

            @Test
            @DisplayName("400 BAD REQUEST 상태 코드를 응답한다")
            void should_respond_400_bad_request() throws Exception {

                given(multipartService.upload(any(MultipartFile.class)))
                        .willThrow(new FileUploadException(errorMessage));

                final MockMultipartFile file =
                        new MockMultipartFile(
                                "file",
                                "hello.txt",
                                MediaType.TEXT_PLAIN_VALUE,
                                new byte[0]
                        );

                mockMvc.perform(multipart("/upload").file(file))
                        .andExpect(status().isBadRequest())
                        .andExpect(content().string(containsString(errorMessage)))
                ;
            }
        }

        @Nested
        @DisplayName("file 파라미터가 비었을 경우")
        class Context_with_empty_param {

            @Test
            @DisplayName("400 BAD REQUEST 상태 코드를 응답한다")
            void should_respond_400_bad_request() throws Exception {
                mockMvc.perform(multipart("/upload"))
                        .andExpect(status().isBadRequest())
                ;
            }
        }
    }

    @Nested
    @DisplayName("GET /download/{fileName}")
    class Describe_download {

        @Test
        @DisplayName("파일을 응답한다")
        void should_respond_file() throws Exception {
            final String fileName = "filename.txt";

            given(multipartService.download(fileName))
                    .willReturn(new byte[1]);

            MvcResult mvcResult = mockMvc.perform(
                            get("/download/{fileName}", fileName)
                                    .contentType(MediaType.APPLICATION_OCTET_STREAM))
                    .andExpect(status().isOk())
                    .andReturn()
            ;

            assertThat(mvcResult.getResponse().getContentAsByteArray()).isNotEmpty();

            then(multipartService)
                    .should()
                    .download(anyString());
        }
    }

}
