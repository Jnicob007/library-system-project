import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class MagazineDB implements PublicationDB<MagazineObj> {
    @Override
    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS magazines(" +
                "book_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "title varchar(100) NOT NULL," +
                "topic VARCHAR(50) NOT NULL" +
                "release_date varchar(10) NOT NULL," +
                "publisher_ID INT NOT NULL," +
                "quantity_in_stock INT NOT NULL," +
                "articles_ammount INT NOT NULL" +
                ");";

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.executeUpdate(sql);
            System.out.println("Magazines table created successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addPublication(MagazineObj magazine){
        String sql = "INSERT INTO magazines (title, topic, release_date, publisher_ID, quantity_in_stock, articles_ammount)" +
                "VALUES(?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, magazine.getTitle());
            ps.setString(2, magazine.getTopic());
            ps.setDate(3, magazine.getReleaseDate());
            ps.setInt(4, magazine.getPublisherID());
            ps.setInt(5, magazine.getQuantityInStock());
            ps.setInt(6, magazine.getArticlesAmmount());
            ps.executeUpdate();
            System.out.println("Magazine added successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<MagazineObj> getAllPublications(){
        ArrayList<MagazineObj> magazines = new ArrayList<>();
        String sql = "SELECT * FROM magazines";

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            if(!rs.next()){
                System.out.println("No magazines to display");
            }
            else{
                while(rs.next()){
                    MagazineObj newMagazine = new MagazineObj(
                            rs.getInt("magazine_ID"),
                            rs.getString("title"),
                            rs.getString("topic"),
                            rs.getDate("release_date"),
                            rs.getInt("publisher_ID"),
                            rs.getInt("quantity_in_stock"),
                            rs.getInt("articles_ammount")
                    );
                    magazines.add(newMagazine);
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return magazines;
    }

    public void rentPublication(int publicationID, int userID){
        String sql0 = "SELECT * FROM users WHERE user_ID = ?;";
        String sql1 = "UPDATE magazines SET quantity_in_stock = quantity_in_stock - 1 WHERE magazine_ID = ?;";
        String sql2 = "INSERT INTO rented_publications (publication_ID, user_ID, publication_type) VALUES (?,?,?);";
        String sql3 = "SELECT * FROM magazines WHERE magazine_ID = ?;";
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
            ps4.setString(3, "magazine");

            try(ResultSet rsUser = ps0.executeQuery();
                ResultSet rsMagazine = ps3.executeQuery();
                ResultSet rsRented = ps4.executeQuery()){
                if(!rsUser.next()){
                    System.out.println("There is no user of given ID");
                } else if (!rsMagazine.next()) {
                    System.out.println("There is no magazine of given ID");
                }
                else{
                    if(rsMagazine.getInt("quantity_in_stock") > 0){
                        if(rsRented.next()){
                            System.out.println("Given user has already rented given magazine.");
                        }
                        else{
                            System.out.println("Input users password: ");
                            Scanner keyboard = new Scanner(System.in);
                            String input = keyboard.nextLine();
                            if(input.equals(rsUser.getString("password"))){
                                ps1.setInt(1, publicationID);
                                ps1.executeUpdate();
                                ps2.setInt(1, publicationID);
                                ps2.setInt(2, userID);
                                ps2.setString(3, "magazine");
                                ps2.executeUpdate();
                                System.out.println("Magazine rented successfully!");
                            }
                            else{
                                System.out.println("Invalid password");
                            }
                        }
                    }
                    else{
                        System.out.println("Chosen magazine was sold out.");
                    }
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void returnPublication(int publicationID, int userID){
        String sql0 = "SELECT * FROM rented_publications WHERE publication_ID = ? AND user_ID = ? AND publication_type = ?;";
        String sql1 = "DELETE FROM rented_publications WHERE publication_ID = ? AND user_ID = ? AND publication_type = ?;";
        String sql2 = "UPDATE magazines SET quantity_in_stock = quantity_in_stock + 1 WHERE magazine_ID = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps0 = conn.prepareStatement(sql0);
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2)){
            ps0.setInt(1, publicationID);
            ps0.setInt(2, userID);
            ps0.setString(3, "magazine");

            try(ResultSet rsRented = ps0.executeQuery()){
                if(!rsRented.next()){
                    System.out.println("Given user has not rented given magazine.");
                }
                else{
                    ps1.setInt(1, publicationID);
                    ps1.setInt(2, userID);
                    ps1.setString(3, "magazine");
                    ps1.executeUpdate();
                    ps2.setInt(1, publicationID);
                    ps2.executeUpdate();
                    System.out.println("Magazine returned successfully!");
                }

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deletePublication(int magazineID){
        String sql = "DELETE FROM magazines WHERE magazine_ID = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, magazineID);
            ps.executeUpdate();
            System.out.println("Magazine nr. " + magazineID + " deleted successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
