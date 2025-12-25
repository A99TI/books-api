package com.a99ti.books.controller;

import com.a99ti.books.entities.Book;
import com.a99ti.books.request.BookRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController (){
        initialiseBooks();
    }

    private void initialiseBooks() {
        books.addAll(List.of(
                new Book(1, "The Pragmatic Programmer", "Andrew Hunt & David Thomas", "Programming", 5),
                new Book(2, "Clean Code", "Robert C. Martin", "Programming", 5),
                new Book(3, "Effective Java", "Joshua Bloch", "Java", 5),
                new Book(4, "Design Patterns", "Erich Gamma et al.", "Software Engineering", 4),
                new Book(5, "Introduction to Algorithms", "Thomas H. Cormen", "Programming", 5),
                new Book(6, "Refactoring", "Martin Fowler", "Programming", 4),
                new Book(7, "Head First Java", "Kathy Sierra & Bert Bates", "Java", 4),
                new Book(8, "Cracking the Coding Interview", "Gayle Laakmann McDowell", "Programming", 5),
                new Book(9, "The Clean Coder", "Robert C. Martin", "Software Engineering", 4),
                new Book(10, "Working Effectively with Legacy Code", "Michael Feathers", "Software Engineering", 5)
        ));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category){
        if (category == null){
            return books;
        }

        return books.stream().filter(book -> book.getCategory().equalsIgnoreCase(category)).toList();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable @Min(value = 1) long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createBook(@Valid @RequestBody BookRequest bookRequest){
        long id = books.isEmpty() ? 1 : books.getLast().getId() + 1;

        Book book = convertToBook(id, bookRequest);

        books.add(book);

    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void updateBook(@PathVariable @Min(value = 1) long id, @Valid @RequestBody BookRequest bookRequest) {
        for (int i = 0; i < books.size(); i++){
            if (books.get(i).getId() == id){
                Book updatedBook = convertToBook(id, bookRequest);
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable @Min(value = 1)long id){
        books.removeIf(book -> book.getId() == id);
    }

    private Book convertToBook (long id, BookRequest bookRequest) {
        return new Book(
                id,
                bookRequest.getTitle(),
                bookRequest.getAuthor(),
                bookRequest.getCategory(),
                bookRequest.getRating()
        );
    }

}
