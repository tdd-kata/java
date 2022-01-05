package com.markruler.spec.reflection.dynamicproxy;

import com.markruler.spec.reflection.Book;

public class DefaultBookService implements BookService {

    @Override
    public String rent(final Book book) {
        return "rent:" + book.getTitle();
    }

    @Override
    public String returns(final Book book) {
        return "returns:" + book.getTitle();
    }
}
