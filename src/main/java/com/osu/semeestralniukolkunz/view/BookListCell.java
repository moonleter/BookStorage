package com.osu.semeestralniukolkunz.view;

import com.osu.semeestralniukolkunz.utility.IBookFormatter;
import com.osu.semeestralniukolkunz.model.Book;
import javafx.scene.control.ListCell;

public class BookListCell extends ListCell<Book> { //reprezentuje 1 položku(atribut) knihy a aktulizuje její atributy
    private com.osu.semeestralniukolkunz.utility.IBookFormatter IBookFormatter;

    public BookListCell(IBookFormatter IBookFormatter) {
        this.IBookFormatter = IBookFormatter;
    }

    @Override
    protected void updateItem(Book item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { //aktualizace atributu knihy, pokdu není prázdný nebo null, tak se zformátuje a aktualizuje
            setText(IBookFormatter.format(item));
        } else {
            setText(null);
        }
    }}