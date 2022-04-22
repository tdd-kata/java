package org.xpdojo.designpatterns._02_structural_patterns._04_decorator;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollectionsTest {

    @Test
    void sut_collections_list() {
        ArrayList list = new ArrayList<>();
        list.add(new Book());

        List unmodifiableList = Collections.unmodifiableList(list);
        list.add(new Item());
        assertThatThrownBy(() -> unmodifiableList.add(new Book()))
                .isInstanceOf(UnsupportedOperationException.class);


        List books = Collections.checkedList(list, Book.class);
        assertThatThrownBy(() -> books.add(new Item()))
                .isInstanceOf(ClassCastException.class);


        // Servlet
        // HttpServletRequestWrapper requestWrapper;
        // HttpServletResponseWrapper responseWrapper;

        // Spring
        // BeanDefinitionDecorator
        // ServerHttpRequestDecorator
    }

    private static class Book {

    }

    private static class Item {

    }
}
