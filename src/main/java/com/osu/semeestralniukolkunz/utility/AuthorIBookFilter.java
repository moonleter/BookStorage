package com.osu.semeestralniukolkunz.utility;

import com.osu.semeestralniukolkunz.model.Book;

public class AuthorIBookFilter implements IBookFilter { //implemnetuje rozhrani IBookFilter a filtruje podle Autora knihy
    private String author;

    public AuthorIBookFilter(String author) {
        this.author = author;
    }

    @Override
    public boolean filter(Book book) {
        return book.getAuthor().equals(author);
    }
}
