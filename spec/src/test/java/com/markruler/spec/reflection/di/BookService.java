package com.markruler.spec.reflection.di;

public class BookService {

    @Inject
    BookRepository bookRepository;

    public String save() {
        return bookRepository.save();
    }
}
