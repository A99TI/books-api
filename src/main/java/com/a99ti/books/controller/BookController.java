package com.a99ti.books.controller;

import com.a99ti.books.entities.Book;
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

    @GetMapping
    public List<Book> getBooks(@RequestParam(required = false) String category){
        if (category == null){
            return books;
        }

        return books.stream().filter(book -> book.getCategory().equalsIgnoreCase(category)).toList();
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable long id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping
    public void createBook(@RequestBody Book newBook){
        boolean isNewBook = books.stream().noneMatch(book -> book.getTitle().equalsIgnoreCase(newBook.getTitle()));

        if (isNewBook) {
            books.add(newBook);
        }
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable long id, @RequestBody Book updatedBook) {
        for (int i = 0; i < books.size(); i++){
            if (books.get(i).getId() == id){
                books.set(i, updatedBook);
                return;
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id){
        books.removeIf(book -> book.getId() == id);
    }

}
