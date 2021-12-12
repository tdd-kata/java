package com.markruler.datajpa.controller;

import com.markruler.datajpa.entity.Member;
import com.markruler.datajpa.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberRepository memberRepository;

    @Nested
    @DisplayName("사용자 조회 시")
    class Describe_domain_converter_class {

        final String username = "userA";
        final Member userA = new Member(username, 10);

        @BeforeEach
        void setUp() {
            given(memberRepository.findById(anyLong()))
                    .willReturn(Optional.of(userA));
        }

        @Nested
        @DisplayName("PathVariable로 Long을 받으면")
        class Context_with_long_id {

            @Test
            @DisplayName("Repository를 직접 호출한다")
            void direct_call_repository() throws Exception {
                mockMvc.perform(
                                get("/members/{id}", 1)
                                        .accept(MediaType.APPLICATION_JSON_UTF8)
                                        .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string(username));

                verify(memberRepository).findById(anyLong());
            }
        }

        @Disabled("TODO: 실제 요청할 경우에는 username이 반환되지만 테스트에선 id가 반환됨")
        @Nested
        @DisplayName("PathVariable로 Entity를 받으면")
        class Context_with_entity {

            @Test
            @DisplayName("Domain Converter Class 기능을 활용한다")
            void domain_converter_class() throws Exception {
                mockMvc.perform(
                                get("/members2/{id}", 1)
                                        .accept(MediaType.APPLICATION_JSON_UTF8)
                                        .contentType(MediaType.APPLICATION_JSON)
                        )
                        .andExpect(status().isOk())
                        .andExpect(content().string(username));

                verify(memberRepository).findById(anyLong());
            }
        }
    }

}
