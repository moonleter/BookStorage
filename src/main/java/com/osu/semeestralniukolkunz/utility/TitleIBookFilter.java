package com.osu.semeestralniukolkunz.utility;

import com.osu.semeestralniukolkunz.model.Book;
import com.osu.semeestralniukolkunz.utility.IBookFilter;

public class TitleIBookFilter implements IBookFilter { //implemnetuje rozhrani IBookFilter a filtruje podle nazvu knihy
    private String title;

    public TitleIBookFilter(String title) {
        this.title = title.toLowerCase();
    }

    @Override
    public boolean filter(Book book) {
        return book.getTitle().toLowerCase().contains(title);
    }
}
