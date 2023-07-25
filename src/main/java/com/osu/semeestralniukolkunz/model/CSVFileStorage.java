package com.osu.semeestralniukolkunz.model;


import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CSVFileStorage { //trida pro ukladani a nacitani dat z CSV souboru
    private String fileName;

    public CSVFileStorage(String fileName) {
        this.fileName = fileName;
    }

    public List<Book> read() throws IOException { //metoda pro nacitani dat z CSV souboru a jejich vypis
        List<Book> books = new ArrayList<>();
        //používám BufferReader místo FileInputStreamu, ptz čte soubor po řádích a to je samozřejmě u CSV soubpru rychlejší
        //FileInputStream čte soubory po bajtech -> v mém případě pomalejší
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Book book = new Book(parts[0], parts[1], Integer.parseInt(parts[2]));
                books.add(book);
            }
        }
        return books;
    }
    //write
    public void write(List<Book> books) throws IOException { //metoda pro zapisovani dat do CSV souboru
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Book book : books) {
                writer.write(book.getTitle() + "," + book.getAuthor() + "," + book.getPublicationYear());
                writer.newLine();
            }
        }
    }



}