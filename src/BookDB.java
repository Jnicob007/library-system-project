import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class BookDB implements PublicationDB<BookObj> { // klasa BookDB rozszerzająca interfejs do baz danych dla obiektów BookObj
    @Override
    public void createTable(){ // metoda tworząca tabele
        String sql = "CREATE TABLE IF NOT EXISTS books(" +
                "book_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "title varchar(100) NOT NULL," +
                "genre VARCHAR(50) NOT NULL," +
                "release_date varchar(10) NOT NULL," +
                "author_ID INT NOT NULL," +
                "publisher_ID INT NOT NULL," +
                "quantity_in_stock INT NOT NULL," +
                "language VARCHAR(50) NOT NULL," +
                "pages_ammount INT NOT NULL" +
                ");";

        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()){
            stmt.executeUpdate(sql);
            System.out.println("Books table created successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addPublication(BookObj book){ // metoda dodająca nową książkę do tabeli
        String sql = "INSERT INTO books (title, genre, release_date, author_ID, publisher_ID, quantity_in_stock, language, pages_ammount)" +
                "VALUES(?,?,?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getGenre());
            ps.setDate(3, book.getReleaseDate());
            ps.setInt(4, book.getAuthorID());
            ps.setInt(5, book.getPublisherID());
            ps.setInt(6, book.getQuantityInStock());
            ps.setString(7, book.getLanguage());
            ps.setInt(8, book.getPagesAmmount());
            ps.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<BookObj> getAllPublications(){ // metoda zwracająca wszystkie książki z tabeli
        ArrayList<BookObj> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            if(!rs.next()){
                System.out.println("No books to display.");
            }
            else{
                while(rs.next()){
                    BookObj newBook = new BookObj(
                            rs.getInt("book_ID"),
                            rs.getString("title"),
                            rs.getString("genre"),
                            rs.getDate("release_date"),
                            rs.getInt("author_ID"),
                            rs.getInt("publisher_ID"),
                            rs.getInt("quantity_in_stock"),
                            rs.getString("language"),
                            rs.getInt("pages_ammount")
                    );
                    books.add(newBook);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return books;
    }

    @Override
    public void rentPublication(int publicationID, int userID){ // metoda wypożyczająca daną książkę (po publicationID) przez danego użytkownika (userID)
        String sql0 = "SELECT * FROM users WHERE user_ID = ?;";
        String sql1 = "UPDATE books SET quantity_in_stock = quantity_in_stock - 1 WHERE book_ID = ?;";
        String sql2 = "INSERT INTO rented_publications (publication_ID, user_ID, publication_type) VALUES (?,?,?);";
        String sql3 = "SELECT * FROM books WHERE book_ID = ?;";
        String sql4 = "SELECT * FROM rented_publications WHERE publication_ID = ? AND user_ID = ? AND publication_type = ?;";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps0 = conn.prepareStatement(sql0);
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            PreparedStatement ps4 = conn.prepareStatement(sql4)) {
            ps0.setInt(1, userID);
            ps3.setInt(1, publicationID);
            ps4.setInt(1, publicationID);
            ps4.setInt(2, userID);
            ps4.setString(3, "book");

            // tutaj zrobiłem trochę walidacji typu:
            // nie ma książki o podanym ID,
            // nie ma usera o podanym ID,
            // dana książka ma quantity_in_stock równe 0,
            // podczas wypożyczania użytkownik podał złe hasło do konta (kolumna password w tabeli users),
            // dany użytkownik już wypożyczył daną książkę
            try(ResultSet rsUser = ps0.executeQuery();
            ResultSet rsBook = ps3.executeQuery();
            ResultSet rsRented = ps4.executeQuery()){
                if(!rsUser.next()){
                    System.out.println("There is no user of given ID");
                } else if (!rsBook.next()) {
                    System.out.println("There is no book of given ID");
                }
                else{
                    if(rsBook.getInt("quantity_in_stock") > 0){
                        if(rsRented.next()){
                            System.out.println("Given user has already rented given book.");
                        }
                        else{
                            System.out.println("Input users password: ");
                            System.out.println(rsUser.getString("password"));
                            Scanner keyboard = new Scanner(System.in);
                            String input = keyboard.nextLine();
                            if(input.equals(rsUser.getString("password"))){
                                ps1.setInt(1, publicationID);
                                ps1.executeUpdate();
                                ps2.setInt(1, publicationID);
                                ps2.setInt(2, userID);
                                ps2.setString(3, "book");
                                ps2.executeUpdate();
                                System.out.println("Book rented successfully!");
                            }
                            else{
                                System.out.println("Invalid password");
                            }
                        }
                    }
                    else{
                        System.out.println("Chosen book was sold out.");
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void returnPublication(int publicationID, int userID){ // metoda zwracająca daną książkę (po publicationID) przez danego użytkownika (po userID)
        String sql0 = "SELECT * FROM rented_publications WHERE publication_ID = ? AND user_ID = ? AND publication_type = ?;";
        String sql1 = "DELETE FROM rented_publications WHERE publication_ID = ? AND user_ID = ? AND publication_type = ?;";
        String sql2 = "UPDATE books SET quantity_in_stock = quantity_in_stock + 1 WHERE book_ID = ?";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps0 = conn.prepareStatement(sql0);
        PreparedStatement ps1 = conn.prepareStatement(sql1);
        PreparedStatement ps2 = conn.prepareStatement(sql2)){
            ps0.setInt(1, publicationID);
            ps0.setInt(2, userID);
            ps0.setString(3, "book");

            // noi tutaj też trochę walidacji:
            // dany użytkownik nie wypożyczył danej książki
            try(ResultSet rsRented = ps0.executeQuery()){
                if(!rsRented.next()){
                    System.out.println("Given user has not rented given book.");
                }
                else{
                    ps1.setInt(1, publicationID);
                    ps1.setInt(2, userID);
                    ps1.setString(3, "book");
                    ps1.executeUpdate();
                    ps2.setInt(1, publicationID);
                    ps2.executeUpdate();
                    System.out.println("Book returned successfully!");
                }

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deletePublication(int bookID){ // metoda usuwająca daną książkę z tabeli (po publicationID)
        String sql = "DELETE FROM books WHERE book_ID = ?";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, bookID);
            ps.executeUpdate();
            System.out.println("Book nr. " + bookID + " deleted successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
