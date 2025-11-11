import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;

public class BookObj implements Publication {
    public static HashMap<Object, Object> Genre; // klasa BookObj implementująca interfejs do obiektów
    private int id;
    private String title;
    private String genre;
    private Date releaseDate;
    private int authorID;
    private int publisherID;
    private int quantityInStock;
    private String language;
    private int pagesAmmount;

    public BookObj(int id, String title, String genre, Date releaseDate, int authorID, int publisherID, int quantityInStock, String language, int pagesAmmount) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.authorID = authorID;
        this.publisherID = publisherID;
        this.quantityInStock = quantityInStock;
        this.language = language;
        this.pagesAmmount = pagesAmmount;
    }

    public BookObj(String title, String genre, Date releaseDate, int authorID, int publisherID, int quantityInStock, String language, int pagesAmmount) {
        this(0, title, genre, releaseDate, authorID, publisherID, quantityInStock, language, pagesAmmount);
    }

    public BookObj(String bookTitle, String author, int pages, Date releaseDate){
        this(0,"","", Date.valueOf(LocalDate.now()),0,0,0, "", 0);
    }

    @Override
    public void displayInfo() { // metoda wyświetlająca informacje o książce / wyświetlająca obiekt
        StringBuilder book = new StringBuilder("Book nr. " + id +
                "\n Title: " + title +
                "\n Genre: " + genre +
                "\n Release date: " + releaseDate +
                "\n Author ID: " + authorID +
                "\n Publisher ID: " + publisherID +
                "\n Quantity in stock: " + quantityInStock +
                "\n Language: " + language +
                "\n Pages ammount: " + pagesAmmount);
        System.out.println(book);
    }

    // Gettery
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public Date getReleaseDate() { return releaseDate; }
    public int getAuthorID() { return authorID; }
    public int getPublisherID() { return publisherID; }
    public int getQuantityInStock() { return quantityInStock; }
    public String getLanguage() { return language; }
    public int getPagesAmmount() { return pagesAmmount; }

    // Sortowanie po tytule (A–Z)
    public static class TitleComparator implements Comparator<BookObj> {
        @Override
        public int compare(BookObj b1, BookObj b2) {
            return b1.getTitle().compareToIgnoreCase(b2.getTitle());
        }
    }

    // Sortowanie po dacie wydania (od najstarszej do najnowszej)
    public static class ReleaseDateComparator implements Comparator<BookObj> {
        @Override
        public int compare(BookObj b1, BookObj b2) {
            return b1.getReleaseDate().compareTo(b2.getReleaseDate());
        }
    }

    // Sortowanie po liczbie stron (od najmniejszej do największej)
    public static class PagesAmountComparator implements Comparator<BookObj> {
        @Override
        public int compare(BookObj b1, BookObj b2) {
            return Integer.compare(b1.getPagesAmmount(), b2.getPagesAmmount());
        }
    }
}
