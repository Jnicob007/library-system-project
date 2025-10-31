import java.sql.Date;
import java.time.LocalDate;

public class AudiobookObj implements Publication {
    private int id;
    private String title;
    private String genre;
    private Date releaseDate;
    private int authorID;
    private int publisherID;
    private int quantityInStock;
    private String recordingLength;
    private int availableLanguagesAmmount;

    public AudiobookObj(int id, String title, String genre, Date releaseDate, int authorID, int publisherID, int quantityInStock, String recordingLength, int availableLanguagesAmmount) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.authorID = authorID;
        this.publisherID = publisherID;
        this.quantityInStock = quantityInStock;
        this.recordingLength = recordingLength;
        this.availableLanguagesAmmount = availableLanguagesAmmount;
    }

    public AudiobookObj(String title, String genre, Date releaseDate, int authorID, int publisherID, int quantityInStock, String recordingLength, int availableLanguagesAmmount) {
        this(0, title, genre, releaseDate, authorID, publisherID, quantityInStock, recordingLength, availableLanguagesAmmount);
    }

    public AudiobookObj(){
        this(0, "", "", Date.valueOf(LocalDate.now()), 0, 0, 0, "00:00:00", 0);
    }

    @Override
    public void displayInfo() {
        StringBuilder audiobook = new StringBuilder("Mazagine nr. " + id +
                "\n Title: " + title +
                "\n Genre: " + genre +
                "\n Release date: " + releaseDate +
                "\n Author ID: " + authorID +
                "\n Publisher ID: " + publisherID +
                "\n Quantity in stock: " + quantityInStock +
                "\n Recording length: " + recordingLength +
                "\n Available languages ammount: " + availableLanguagesAmmount);
        System.out.println(audiobook);
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

    public String getRecordingLength() {
        return recordingLength;
    }

    public int getAvailableLanguagesAmmount() {
        return availableLanguagesAmmount;
    }
}
