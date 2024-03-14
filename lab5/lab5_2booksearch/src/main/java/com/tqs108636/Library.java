package com.tqs108636;

import java.util.ArrayList;
import java.util.List;

/*
 * Library holds a list of books
 */

public class Library {
    private List<Book> books;

    public Library() {
        books = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public boolean addBook(Book b) {
        return books.add(b);
    }

    public List<Book> getBooksByAuthor(String author) {
        return books.stream().filter((b) -> b.getAuthor().equals(author)).toList();
    }

    public List<Book> getBooksByCategory(String category) {
        return books.stream().filter((b) -> b.getCategory().equals(category)).toList();
    }

    public List<Book> getBooksInYearInterval(int yearStart, int yearEnd) {
        return books.stream()
                .filter((b) -> b.getPublicationDate().getYear() >= yearStart
                        && b.getPublicationDate().getYear() <= yearEnd)
                .toList();
    }
}
