package com.markruler.spec.reflection.proxy;

import com.markruler.spec.reflection.Book;

public class DefaultBookService implements BookService {
    @Override
    public String rent(final Book book) {
        return book.getTitle();
    }
}
