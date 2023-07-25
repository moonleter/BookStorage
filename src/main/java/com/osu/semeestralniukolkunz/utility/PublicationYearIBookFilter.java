package com.osu.semeestralniukolkunz.utility;

import com.osu.semeestralniukolkunz.model.Book;

public class PublicationYearIBookFilter implements IBookFilter { //implemnetuje rozhrani IBookFilter a filtruje podle roku vydani knihy
    private int publicationYear;

    public PublicationYearIBookFilter(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public boolean filter(Book book) {
        return book.getPublicationYear() == publicationYear;
    }
}
