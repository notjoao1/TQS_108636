package com.tqs108636;

import java.time.LocalDate;

public class Book {
    private String title;
    private String author;
    private LocalDate publicationDate;
    private String category;

    public Book(String title, String author, LocalDate publicationDate, String category) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPublicationDate(LocalDate publication_date) {
        this.publicationDate = publication_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s). Category: %s", title, author, publicationDate.toString(), category);
    }
}
