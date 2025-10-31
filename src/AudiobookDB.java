import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class AudiobookDB implements PublicationDB<AudiobookObj> {
    @Override
    public void createTable(){
        String sql = "CREATE TABLE IF NOT EXISTS audiobooks(" +
                "audiobook_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "title varchar(100) NOT NULL," +
                "genre VARCHAR(50) NOT NULL" +
                "release_date varchar(10) NOT NULL," +
                "author_ID INT NOT NULL," +
                "publisher_ID INT NOT NULL," +
                "quantity_in_stock INT NOT NULL," +
                "recording_length VARCHAR(50) NOT NULL" +
                "available_languages_ammount INT NOT NULL" +
                ");";

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.executeUpdate(sql);
            System.out.println("Audiobooks table created successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void addPublication(AudiobookObj audiobook){

        String sql = "INSERT INTO audiobooks (title, genre, release_date, author_ID, publisher_ID, quantity_in_stock, recording_length, available_languages_ammount)" +
                "VALUES(?,?,?,?,?,?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, audiobook.getTitle());
            ps.setString(2, audiobook.getGenre());
            ps.setDate(3, audiobook.getReleaseDate());
            ps.setInt(4, audiobook.getAuthorID());
            ps.setInt(5, audiobook.getPublisherID());
            ps.setInt(6, audiobook.getQuantityInStock());
            ps.setString(7, audiobook.getRecordingLength());
            ps.setInt(8, audiobook.getAvailableLanguagesAmmount());
            ps.executeUpdate();
            System.out.println("Audiobook added successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ArrayList<AudiobookObj> getAllPublications(){
        ArrayList<AudiobookObj> audiobooks = new ArrayList<>();
        String sql = "SELECT * FROM audiobooks";

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)){
            while(rs.next()){
                AudiobookObj newAudiobook = new AudiobookObj(
                        rs.getInt("audiobook_ID"),
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getDate("release_date"),
                        rs.getInt("author_ID"),
                        rs.getInt("publisher_ID"),
                        rs.getInt("quantity_in_stock"),
                        rs.getString("recording_length"),
                        rs.getInt("available_languages_ammount")
                );
                audiobooks.add(newAudiobook);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return audiobooks;
    }

    public void rentPublication(int publicationID, int userID){
        String sql0 = "SELECT * FROM users WHERE user_ID = ?;";
        String sql1 = "UPDATE audiobooks SET quantity_in_stock = quantity_in_stock - 1 WHERE audiobook_ID = ?;";
        String sql2 = "INSERT INTO rented_publications (publication_ID, user_ID, publication_type) VALUES (?,?,?);";
        String sql3 = "SELECT * FROM audiobooks WHERE audiobook_ID = ?;";
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
            ps4.setString(3, "audiobook");

            try(ResultSet rsUser = ps0.executeQuery();
                ResultSet rsAudiobook = ps3.executeQuery();
                ResultSet rsRented = ps4.executeQuery()){
                if(!rsUser.next()){
                    System.out.println("There is no user of given ID");
                } else if (!rsAudiobook.next()) {
                    System.out.println("There is no audiobook of given ID");
                }
                else{
                    if(rsAudiobook.getInt("quantity_in_stock") > 0){
                        if(rsRented.next()){
                            System.out.println("Given user has already rented given audiobook.");
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
                                ps2.setString(3, "audiobook");
                                ps2.executeUpdate();
                                System.out.println("Audiobook rented successfully!");
                            }
                            else{
                                System.out.println("Invalid password");
                            }
                        }
                    }
                    else{
                        System.out.println("Chosen audiobook was sold out.");
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
        String sql2 = "UPDATE audiobooks SET quantity_in_stock = quantity_in_stock + 1 WHERE audiobook_ID = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps0 = conn.prepareStatement(sql0);
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            PreparedStatement ps2 = conn.prepareStatement(sql2)){
            ps0.setInt(1, publicationID);
            ps0.setInt(2, userID);
            ps0.setString(3, "audiobook");

            try(ResultSet rsRented = ps0.executeQuery()){
                if(!rsRented.next()){
                    System.out.println("Given user has not rented given audiobook.");
                }
                else{
                    ps1.setInt(1, publicationID);
                    ps1.setInt(2, userID);
                    ps1.setString(3, "audiobook");
                    ps1.executeUpdate();
                    ps2.setInt(1, publicationID);
                    ps2.executeUpdate();
                    System.out.println("Audiobook returned successfully!");
                }

            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deletePublication(int audiobookID){
        String sql = "DELETE FROM audiobooks WHERE audiobook_ID = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, audiobookID);
            ps.executeUpdate();
            System.out.println("Audiobook nr. " + audiobookID + " deleted successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
