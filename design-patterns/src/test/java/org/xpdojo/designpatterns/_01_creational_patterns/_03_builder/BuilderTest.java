package org.xpdojo.designpatterns._01_creational_patterns._03_builder;

import lombok.Builder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BuilderTest {

    @Test
    @DisplayName("StringBuilder를 사용하면 문자열을 조합할 수 있다")
    void sut_string_builder() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("Hello, ")
                .append("World!");

        assertThat(stringBuilder).hasToString("Hello, World!");
    }

    @Test
    @DisplayName("Spring Web에서 제공하는 UriComponentsBuilder를 사용하면 URI를 조합할 수 있다")
    void sut_uri_component_builder() {
        UriComponents uriComponents =
                UriComponentsBuilder.newInstance()
                        .scheme("http")
                        .host("www.markruler.com")
                        .path("/api/v1/tours")
                        .queryParam("page", 1)
                        .fragment("plan")
                        .build();

        URI expected = URI.create("http://www.markruler.com/api/v1/tours?page=1#plan");

        assertThat(uriComponents.normalize().toUri())
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("Stream.Builder를 사용하여 문자열을 조합할 수 있다")
    void sut_stream_builder() {
        Stream<String> names =
                Stream.<String>builder()
                        .add("markruler")
                        .add("whiteship")
                        .build();

        assertThat(names)
                .containsExactlyInAnyOrder("markruler", "whiteship");
    }

    @Test
    @DisplayName("Lombok@Builder를 사용하여 Tour를 생성할 수 있다")
    void sut_lombok_builder() {
        Tour tour =
                Tour.builder()
                        .name("여행")
                        .price(10000)
                        .build();

        assertThat(tour)
                .hasFieldOrPropertyWithValue("name", "여행")
                .hasFieldOrPropertyWithValue("price", 10000);
    }

    static class Tour {
        private String name;
        private int price;

        @Builder
        public Tour(String name, int price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }
    }

}
