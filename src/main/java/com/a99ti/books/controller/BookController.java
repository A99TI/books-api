package com.a99ti.books.controller;

import com.a99ti.books.entities.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookController {

    private final List<Book> books = new ArrayList<>();

    public BookController (){
        initialiseBooks();
    }

    private void initialiseBooks() {
        books.addAll(List.of(
                new Book("The Pragmatic Programmer", "Andrew Hunt & David Thomas", "Software Development"),
                new Book("Clean Code", "Robert C. Martin", "Programming"),
                new Book("Effective Java", "Joshua Bloch", "Java"),
                new Book("Design Patterns", "Erich Gamma et al.", "Software Engineering"),
                new Book("Introduction to Algorithms", "Thomas H. Cormen", "Computer Science")
        ));
    }

    @GetMapping("/api/books")
    public List<Book> getBooks(){
        return books;
    }

}
