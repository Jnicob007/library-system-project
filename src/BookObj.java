import java.sql.Date;

public class BookObj {
    private int id;
    private String title;
    private float price;
    private Date releaseDate;
    private int authorID;
    private int publisherID;
    private int quantityInStock;
    private boolean isOnDiscount;
    private int discount;

    public BookObj(int id, String title, float price, Date releaseDate, int authorID, int publisherID, int quantityInStock, boolean isOnDiscount, int discount) {
        this.id = id;
        this.title = title;
        if(isOnDiscount){
            this.price = price - (price * discount);
        }
        else{
            this.price = price;
        }
        this.releaseDate = releaseDate;
        this.authorID = authorID;
        this.publisherID = publisherID;
        this.quantityInStock = quantityInStock;
        this.isOnDiscount = isOnDiscount;
        this.discount = discount;
    }

    public BookObj(String title, float price, Date releaseDate, int authorID, int publisherID, int quantityInStock, boolean isOnDiscount, int discount) {
        this.id = 0;
        this.title = title;
        if(isOnDiscount){
            this.price = price - (price * discount);
        }
        else{
            this.price = price;
        }
        this.releaseDate = releaseDate;
        this.authorID = authorID;
        this.publisherID = publisherID;
        this.quantityInStock = quantityInStock;
        this.isOnDiscount = isOnDiscount;
        this.discount = discount;
    }

    @Override
    public String toString() {
        StringBuilder book = new StringBuilder("Book nr. " + id +
                "\n Title: " + title +
                "\n Price: " + price +
                "\n Release date: " + releaseDate +
                "\n Author ID: " + authorID +
                "\n Publisher ID: " + publisherID +
                "\n Quantity in stock: " + quantityInStock +
                "\n Is on discount: " + isOnDiscount);
        if(isOnDiscount){
            book.append("\n Discount: " + discount);
        }
        return book.toString();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public float getPrice() {
        return price;
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

    public boolean getIsOnDiscount() {
        return isOnDiscount;
    }

    public int getDiscount() {
        return discount;
    }
}


