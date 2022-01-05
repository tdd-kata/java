package com.markruler.spec.reflection.dynamicproxy;

import com.markruler.spec.reflection.Book;

public interface BookService {

    String rent(Book book);

    String returns(Book book);
}
