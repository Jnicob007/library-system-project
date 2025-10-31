import java.util.ArrayList;

public interface PublicationDB<T extends Publication> {
    void createTable();
    void addPublication(T publication);
    ArrayList<T> getAllPublications();
    void rentPublication(int publicationID, int userID);
    void returnPublication(int publicationID, int userID);
    void deletePublication(int publicationID);
}
