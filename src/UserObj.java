public class UserObj {
    private int id;
    private String username;
    private String email;
    private String password;

    public UserObj(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserObj(String username, String email, String password) {
        this(0, username, email, password);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
