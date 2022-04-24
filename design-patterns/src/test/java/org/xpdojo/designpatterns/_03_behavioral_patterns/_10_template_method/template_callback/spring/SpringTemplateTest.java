package org.xpdojo.designpatterns._03_behavioral_patterns._10_template_method.template_callback.spring;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 이름이 Template으로 끝나는 클래스
 */
class SpringTemplateTest {

    @Test
    void sut_jdbc_template() {
        // Template Callback
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "select * from user where id = ?";
        assertThat(sql).contains("?");

        assertThatThrownBy(() -> jdbcTemplate.queryForObject(sql, Integer.class, 1))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void sut_rest_template() {
        // Template Callback
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("X-COM-PERSIST", "NO");
        headers.set("X-COM-LOCATION", "USA");

        HttpEntity<String> entity = new HttpEntity<>(headers);
        // ResponseEntity<String> responseEntity =
        assertThatThrownBy(() -> restTemplate.exchange("http://127.0.0.1", HttpMethod.GET, entity, String.class))
                .isInstanceOf(ResourceAccessException.class);

        // assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }
}
