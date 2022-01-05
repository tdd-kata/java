package com.markruler.spec.reflection.proxy;

import com.markruler.spec.reflection.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Proxy Pattern")
class BookServiceTest {

    BookService bookService = new BookServiceProxy(new DefaultBookService());

    @Test
    @DisplayName("Proxy를 만들면 Real subject에 부가적인 기능을 제공할 수 있다")
    void sut_proxy_pattern() {
        // given
        final Book spring = new Book("Spring");

        // when
        final String rent = bookService.rent(spring);

        // then
        assertThat(rent).isEqualTo("proxy:Spring");
    }
}
