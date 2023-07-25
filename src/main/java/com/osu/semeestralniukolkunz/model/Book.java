package com.osu.semeestralniukolkunz.model;

public class Book {
    private String title;
    private String author;
    private int publicationYear;

    public Book(String title, String author, int publicationYear) { //model toho, jak bude objekt Book vypadat
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
    }

    public String getTitle() {
        return title;
    } //gettery a settery

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
