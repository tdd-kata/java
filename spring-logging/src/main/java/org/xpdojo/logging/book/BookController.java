package org.xpdojo.logging.book;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class BookController {

    private final BookRepository bookRepository;

    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * <pre>
     * curl -X POST -H "Content-Type: application/json" -d '{"title":"The Hobbit", "price": 3000}' http://host.docker.internal:8080/books
     * </pre>
     */
    @PostMapping("/books")
    public int saveBook(@RequestBody Book book) {
        log.info("Book: {}", book);
        int savedBookCount = bookRepository.save(book);
        log.info("Saved book: {}", savedBookCount);
        return savedBookCount;
    }

    /**
     * <pre>
     * curl -X GET http://host.docker.internal:8080/books
     * </pre>
     */
    @GetMapping("/books")
    public List<Book> listBooks() {
        return bookRepository.findAll();
    }

    /**
     * <pre>
     * curl -X GET http://host.docker.internal:8080/books/1
     * </pre>
     */
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));
    }
}
