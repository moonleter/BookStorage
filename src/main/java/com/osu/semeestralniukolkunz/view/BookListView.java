package com.osu.semeestralniukolkunz.view;

import com.osu.semeestralniukolkunz.utility.IBookFilter;
import com.osu.semeestralniukolkunz.utility.SimpleIBookFormatter;
import com.osu.semeestralniukolkunz.model.Book;
import com.osu.semeestralniukolkunz.model.BookManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.util.List;

public class BookListView extends ListView<Book> { //reprezentuje seznam knih
    private BookManager bookManager;
    private IBookFilter filter;
    private List<Book> allBooks;

    public BookListView(BookManager bookManager) {//konstruktor nastavuje seznam(List) knih
        this.bookManager = bookManager;
        ObservableList<Book> bookList = FXCollections.observableArrayList(bookManager.getBooks());
        setItems(bookList);
        setCellFactory(bookListView -> new BookListCell(new SimpleIBookFormatter()));
    }

    private void updateList() {   //aktualizuje seznam knih např. po nastavení filtru
        ObservableList<Book> filteredBooks = FXCollections.observableArrayList();
        for (Book book : allBooks) {
            if (filter == null || filter.filter(book)) {
                filteredBooks.add(book);
            }
        }
        setItems(filteredBooks);
    }
}