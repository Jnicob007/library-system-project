import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class BookDB {
    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS books(" +
                "book_ID INT PRIMARY KEY AUTO_INCREMENT," +
                "title varchar(100) NOT NULL," +
                "price DECIMAL(6,2) NOT NULL," +
                "release_date varchar(10) NOT NULL," +
                "author_ID INT NOT NULL," +
                "publisher_ID INT NOT NULL," +
                "quantity_in_stock INT NOT NULL," +
                "is_on_discount BOOLEAN NOT NULL," +
                "discount INT NOT NULL" +
                ");";

        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement()){
            stmt.executeUpdate(sql);
            System.out.println("Books table created successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addBook(BookObj book){
        String sql = "INSERT INTO books (title, price, release_date, author_ID, publisher_ID, quantity_in_stock, is_on_discount, discount)" +
                "VALUES(?,?,?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, book.getTitle());
            ps.setFloat(2, book.getPrice());
            ps.setDate(3, book.getReleaseDate());
            ps.setInt(4, book.getAuthorID());
            ps.setInt(5, book.getPublisherID());
            ps.setInt(6, book.getQuantityInStock());
            ps.setBoolean(7, book.getIsOnDiscount());
            ps.setInt(8, book.getDiscount());
            ps.executeUpdate();
            System.out.println("Book added successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<BookObj> getAllBooks(){
        ArrayList<BookObj> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                BookObj newBook = new BookObj(
                        rs.getInt("book_ID"),
                        rs.getString("title"),
                        rs.getFloat("price"),
                        rs.getDate("release_date"),
                        rs.getInt("author_ID"),
                        rs.getInt("publisher_ID"),
                        rs.getInt("quantity_in_stock"),
                        rs.getBoolean("is_on_discount"),
                        rs.getInt("discount")
                );
                books.add(newBook);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return books;
    }

    public void updateBooksPrice(int bookID, float price){
        String sql = "UPDATE books SET price = ? WHERE book_ID = ?;";

        try(Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setFloat(1, price);
            ps.setInt(2, bookID);
            ps.executeUpdate();
            System.out.println("Book's nr. " + bookID + " price updated successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void deleteBook(int bookID){
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
