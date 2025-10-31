import java.sql.Date;
import java.time.LocalDate;

public class MagazineObj implements Publication {
    private int id;
    private String title;
    private String topic;
    private Date releaseDate;
    private int publisherID;
    private int quantityInStock;
    private int articlesAmmount;

    public MagazineObj(int id, String title, String topic, Date releaseDate, int publisherID, int quantityInStock, int articlesAmmount) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.releaseDate = releaseDate;
        this.publisherID = publisherID;
        this.quantityInStock = quantityInStock;
        this.articlesAmmount = articlesAmmount;
    }

    public MagazineObj(String title, String topic, Date releaseDate, int publisherID, int quantityInStock, int articlesAmmount) {
//        this.id = id;
//        this.title = title;
//        this.topic = topic;
//        this.releaseDate = releaseDate;
//        this.publisherID = publisherID;
//        this.quantityInStock = quantityInStock;
//        this.articlesAmmount = articlesAmmount;
        this(0, title, topic, releaseDate, publisherID, quantityInStock, articlesAmmount);
    }

    public MagazineObj(){
        this(0,"", "", Date.valueOf(LocalDate.now()), 0, 0, 0);
    }

    @Override
    public void displayInfo() {
        StringBuilder book = new StringBuilder("Mazagine nr. " + id +
                "\n Title: " + title +
                "\n Topic: " + topic +
                "\n Release date: " + releaseDate +
                "\n Publisher ID: " + publisherID +
                "\n Quantity in stock: " + quantityInStock +
                "\n Articles ammount: " + articlesAmmount);
        System.out.println(book);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getPublisherID() {
        return publisherID;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public int getArticlesAmmount() {
        return articlesAmmount;
    }
}
