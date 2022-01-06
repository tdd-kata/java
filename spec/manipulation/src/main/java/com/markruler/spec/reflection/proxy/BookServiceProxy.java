package com.markruler.spec.reflection.proxy;

import com.markruler.spec.reflection.Book;

public class BookServiceProxy implements BookService {

    private final BookService bookService;

    public BookServiceProxy(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public String rent(final Book book) {
        return "proxy:" + bookService.rent(book);
    }
}
