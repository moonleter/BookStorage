package com.osu.semeestralniukolkunz.utility;

import com.osu.semeestralniukolkunz.model.Book;
import com.osu.semeestralniukolkunz.utility.IBookFormatter;

public class SimpleIBookFormatter implements IBookFormatter { //implemnetuje rozhrani IBookFormatter a formatuje knihu
    @Override
    public String format(Book book) {
        return book.getTitle() + " (" + book.getAuthor() + ", " + book.getPublicationYear() + ")";
    }
}
