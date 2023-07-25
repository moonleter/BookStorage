package com.osu.semeestralniukolkunz.controller;

import com.osu.semeestralniukolkunz.utility.SimpleIBookFormatter;
import com.osu.semeestralniukolkunz.model.Book;
import com.osu.semeestralniukolkunz.model.BookManager;
import com.osu.semeestralniukolkunz.model.CSVFileStorage;
import com.osu.semeestralniukolkunz.view.BookListCell;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import java.io.File;

public class GUIHandler extends Application { //třída s komponenty GUI

    private BookManager bookManager;
    private ListView<Book> bookListView;
    private CSVFileStorage bookStorage;

    ComboBox<String> filterCriteriaComboBox;
    TextField filterValueTextField;
    Button applyFilterButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) { //vytváří a zobrazuje GUI komponenty
        bookManager = new BookManager();


        bookStorage = new CSVFileStorage("books.csv"); //defaultně nastvený soubor books.csv

        try {
            bookManager.setBooks(bookStorage.read());
        } catch (Exception e) {
            e.printStackTrace();
        }

        bookListView = new ListView<>();
        bookListView.setItems(FXCollections.observableArrayList(bookManager.getBooks()));
        bookListView.setCellFactory(bookListView -> new BookListCell(new SimpleIBookFormatter()));

        TextField titleField = new TextField();
        titleField.setPromptText("Název knihy");

        TextField authorField = new TextField();
        authorField.setPromptText("Autor");

        TextField publicationYearField = new TextField();
        publicationYearField.setPromptText("Rok vydání");

        Button addButton = new Button("Přidat knihu");
        addButton.setOnAction(e -> {
            String title = titleField.getText();
            String author = authorField.getText();
            int publicationYear = Integer.parseInt(publicationYearField.getText());

            bookManager.addBook(new Book(title, author, publicationYear));
            bookListView.getItems().add(new Book(title, author, publicationYear));

            titleField.clear();
            authorField.clear();
            publicationYearField.clear();
        });

        HBox inputBox = new HBox(titleField, authorField, publicationYearField, addButton);
        inputBox.setSpacing(10);
        inputBox.setAlignment(Pos.CENTER);

        Button loadButton = new Button("Načíst CSV");
        loadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Vyberte CSV soubor");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV soubory (*.csv)", "*.csv"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                bookStorage = new CSVFileStorage(selectedFile.getAbsolutePath());
                try {
                    bookManager.setBooks(bookStorage.read());
                    bookListView.getItems().setAll(bookManager.getBooks());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button saveButton = new Button("Uložit do CSV");
        saveButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Uložit CSV soubor");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV soubory (*.csv)", "*.csv"));
            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                bookStorage = new CSVFileStorage(selectedFile.getAbsolutePath());
                try {
                    bookStorage.write(bookManager.getBooks());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Button editButton = new Button("Upravit knihu");
        editButton.setOnAction(e -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                TextInputDialog inputDialog = new TextInputDialog(selectedBook.getTitle());
                inputDialog.setTitle("Upravit knihu");
                inputDialog.setHeaderText("Zadejte nový název, autora a rok vydání knihy");
                inputDialog.setContentText("Formát: název,autor,rok vydání");

                inputDialog.showAndWait().ifPresent(response -> {
                    String[] parts = response.split(",");
                    if (parts.length == 3) {
                        String title = parts[0];
                        String author = parts[1];
                        int publicationYear = Integer.parseInt(parts[2]);
                        Book newBook = new Book(title, author, publicationYear);
                        bookManager.editBook(selectedBook, newBook);
                        bookListView.getItems().set(bookListView.getSelectionModel().getSelectedIndex(), newBook);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Chyba");
                        alert.setHeaderText("Špatný formát");
                        alert.setContentText("Zkontrolujte formát vstupu.");
                        alert.showAndWait();
                    }
                });
            }
        });

        Button removeButton = new Button("Smazat knihu");
        removeButton.setOnAction(e -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                bookManager.removeBook(selectedBook);
                bookListView.getItems().remove(selectedBook);
            }
        });

        Label filterCriteriaLabel = new Label("Filtruj podle:");
        filterCriteriaComboBox = new ComboBox<>();
        filterCriteriaComboBox.getItems().addAll("Název", "Autor", "Rok vydání");
        filterCriteriaComboBox.getSelectionModel().selectFirst();

        filterValueTextField = new TextField();
        filterValueTextField.setPromptText("Zadejte hodnotu pro filtrování");

        applyFilterButton = new Button("Použít filtr");
        applyFilterButton.setOnAction(e -> {
            String criteria = filterCriteriaComboBox.getSelectionModel().getSelectedItem();
            String value = filterValueTextField.getText();
            bookListView.getItems().setAll(bookManager.filterBooks(criteria, value));
        });
        



        Button clearFilterButton = new Button("Zrušit filtr");
        clearFilterButton.setOnAction(e -> {
            bookListView.getItems().setAll(bookManager.getBooks());
        });

        HBox filterBox = new HBox(filterCriteriaLabel, filterCriteriaComboBox, filterValueTextField, applyFilterButton, clearFilterButton);
        filterBox.setSpacing(10);
        filterBox.setAlignment(Pos.CENTER);

        HBox buttonBox = new HBox(loadButton, saveButton, editButton, removeButton);
        buttonBox.setSpacing(10);
        buttonBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane(); //kontejenr na umístění prvku nahoru, dolu, vlevo, vpravo a uprostřed
        root.setTop(inputBox); //nahoru
        root.setCenter(bookListView); //doprostřed
        root.setBottom(new VBox(filterBox, buttonBox)); //dolů
        //root - hlavní kontejner uspořádávání prvků v GUI

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Knihovna");
        primaryStage.show();
    }

    @Override
    public void stop() { //ukončení aplikace
        try {
            bookStorage.write(bookManager.getBooks());
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Chyba");
            alert.setHeaderText(null);
            alert.setContentText("Nepodařilo se uložit soubor.");
            alert.showAndWait();

            e.printStackTrace();
        }

    }

}
