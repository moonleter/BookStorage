package com.osu.semeestralniukolkunz.model;

import com.osu.semeestralniukolkunz.utility.AuthorIBookFilter;
import com.osu.semeestralniukolkunz.utility.IBookFilter;
import com.osu.semeestralniukolkunz.utility.PublicationYearIBookFilter;
import com.osu.semeestralniukolkunz.utility.TitleIBookFilter;

import java.util.ArrayList;
import java.util.List;

public class BookManager { //třída pro správu seznamu knih
    private List<Book> books;


    public BookManager() {
        this.books = new ArrayList<>();}

    public void editBook(Book oldBook, Book newBook) { //editace knihy
        int index = books.indexOf(oldBook);
        if (index >= 0) {
            books.set(index, newBook);
        }
    }

    public List<Book> getBooks() { //vrací seznam všech knih
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        books.add(book);
    } //přidání knihy do seznamu
    public void removeBook(Book book) {
        books.remove(book);
    } //ostranněí knihy ze seznamu
    public List<Book> filterBooks(String criteria, String value) {   //filtrování knih podle kritérií
        List<Book> filteredBooks = new ArrayList<>();
        IBookFilter filter = null;

        switch (criteria) {
            case "Název":
              filter = new TitleIBookFilter(value);
                break;
            case "Autor":
                filter = new AuthorIBookFilter(value);
                break;
            case "Rok vydání":
                filter = new PublicationYearIBookFilter(Integer.parseInt(value));
                break;
            default:
                throw new IllegalArgumentException("Neplatné kriterium: " + criteria);
        }

        for (Book book : books) {
            if (filter.filter(book)) {
                filteredBooks.add(book); //přidání vyfiltorovaných knih do listu
            }
        }

        return filteredBooks;
    }
    }