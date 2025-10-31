import java.sql.Date;
import java.time.LocalDate;

public class BookObj implements Publication {
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

    public BookObj(){
        this(0,"","", Date.valueOf(LocalDate.now()),0,0,0, "", 0);
    }

//    @Override
//    public String toString() {
//        StringBuilder book = new StringBuilder("Book nr. " + id +
//                "\n Title: " + title +
//                "\n Price: " + price +
//                "\n Release date: " + releaseDate +
//                "\n Author ID: " + authorID +
//                "\n Publisher ID: " + publisherID +
//                "\n Quantity in stock: " + quantityInStock +
//                "\n Is on discount: " + isOnDiscount);
//        if(isOnDiscount){
//            book.append("\n Discount: " + discount);
//        }
//        return book.toString();
//    }

    @Override
    public void displayInfo() {
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

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getAuthorID() {
        return authorID;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public String getLanguage() {
        return language;
    }

    public int getPagesAmmount() {
        return pagesAmmount;
    }
}


