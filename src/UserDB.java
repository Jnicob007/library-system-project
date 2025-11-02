import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class UserDB { // klasa użytkownika do obsługi operacji na bazie danych

    // ta klasa nie implementuje interfejsu PublicationDB, bo użytkownik nie jest publikacją (tj. produktem w bibliotece)
    // tutaj ta implementacja nie ma sensu, tak samo nie tworzę klasy UserFactory

    public void createTable(){ // tworzenie tabeli
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "user_ID INT PRIMARY KEY AUTO_INCREMENT NOT NULL," +
                "username varchar(100) NOT NULL," +
                "email VARCHAR(100) NOT NULL," +
                "password varchar(50) NOT NULL" +
                ");";

        try(Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement()){
            stmt.executeUpdate(sql);
            System.out.println("Users table created successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addUser(UserObj user){ // dodawanie użytkownika do tabeli
        String sql = "INSERT INTO users (username, email, password)" +
                "VALUES(?,?,?)";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.executeUpdate();
            System.out.println("User added successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    ArrayList<UserObj> getAllUsers(){ // zwracanie wszystkich użytkowników z tabeli
        ArrayList<UserObj> users = new ArrayList<>();
        String sql = "SELECT * FROM users;";

        try(Connection conn = DBConnection.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            if(!rs.next()){
                System.out.println("No users to display.");
            }
            else{
                while(rs.next()){
                    UserObj newUser = new UserObj(
                            rs.getInt("user_ID"),
                            rs.getString("username"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                    users.add(newUser);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return users;
    }

    public void deleteUser(int userID){ // usuwanie danego użytkownika z tabeli po ID
        String sql = "DELETE FROM users WHERE user_ID = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)){
            ps.setInt(1, userID);
            ps.executeUpdate();
            System.out.println("Book nr. " + userID + " deleted successfully!");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
