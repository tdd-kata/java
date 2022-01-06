package com.markruler.spec.reflection.di;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dependency Injection")
class ContainerServiceTest {

    @Test
    @DisplayName("직접 클래스를 참조해서 인스턴스를 생성할 수 있다")
    void test_repository() {
        BookRepository bookRepository = ContainerService.getObject(BookRepository.class);
        assertThat(bookRepository).isNotNull();
    }

    @Test
    @DisplayName("Annotation을 사용해서 인스턴스를 생성할 수 있다")
    void test_service() {
        BookService bookService = ContainerService.getObject(BookService.class);
        assertThat(bookService).isNotNull();
        assertThat(bookService.bookRepository).isNotNull();

        assertThat(bookService.save()).isEqualTo("return save");
    }

}
