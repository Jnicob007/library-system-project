import java.awt.print.Book;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main() {
        DBConnection.initDatabase();

        Scanner keyboard = new Scanner(System.in);
        BookDB bookDB = new BookDB();
        bookDB.createTable();

        while(true){
            System.out.println("Menu" +
                    "\n 1. Add book" +
                    "\n 2. Display all books" +
                    "\n 3. Update price of specific book" +
                    "\n 4. Delete book" +
                    "\n 0. Shut down" +
                    "\n Choice: ");

            int choice = keyboard.nextInt();
            keyboard.nextLine();

            switch (choice){
                case 0 -> {
                    System.out.println("Thanks for using our program");
                    return;
                }
                case 1 -> {
                    System.out.println("Title: ");
                    String title = keyboard.nextLine();
                    System.out.println("Price: ");
                    float price = keyboard.nextFloat();
                    System.out.println("Release date: ");
                    keyboard.nextLine();
                    Date releaseDate = Date.valueOf(keyboard.nextLine());
                    System.out.println("Author ID: ");
                    int authorID = keyboard.nextInt();
                    System.out.println("Publisher ID: ");
                    int publisherID = keyboard.nextInt();
                    System.out.println("Quantity in stock: ");
                    int quantityInStock = keyboard.nextInt();
                    System.out.println("Is on discount: ");
                    boolean isOnDiscount = keyboard.nextBoolean();
                    int discount = 0;
                    if(isOnDiscount) {
                        System.out.println("Discount: ");
                        discount = keyboard.nextInt();
                    }
                    BookObj newBook = new BookObj(title, price, releaseDate, authorID, publisherID, quantityInStock, isOnDiscount, discount);
                    bookDB.addBook(newBook);
                }
                case 2 -> {
                    ArrayList<BookObj> books = bookDB.getAllBooks();
                    if(books.isEmpty()){
                        System.out.println("No books to display");
                    }
                    else{
                        for(BookObj b : books){
                            System.out.println(b);
                        }
                    }
                }
                case 3 ->{
                    System.out.println("Input book's ID: ");
                    int bookID = keyboard.nextInt();
                    System.out.println("New price: ");
                    float newPrice = keyboard.nextFloat();
                    bookDB.updateBooksPrice(bookID, newPrice);
                }
                case 4 -> {
                    System.out.println("Input book's ID: ");
                    int bookID = keyboard.nextInt();
                    bookDB.deleteBook(bookID);
                }
                default -> System.out.println("Invalid value inputed!");
            }
        }
    }
}