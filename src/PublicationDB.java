import java.util.ArrayList;

public interface PublicationDB<T extends Publication> { // T oznacza jakikolwiek typ danych, który rozszerza/implementuje interfejs Publication (tj. BookObj, MagazineObj lub AudiobookObj)
    void createTable();
    void addPublication(T publication);
    ArrayList<T> getAllPublications();
    void rentPublication(int publicationID, int userID);
    void returnPublication(int publicationID, int userID);
    void deletePublication(int publicationID);
}

// ten uniwersalny typ danych T, jest użyty po to, żeby można było później uniwersalnie nadpisywać metody
// z takimi typami danych (BookObj, MagazineObj, AudiobookObj), jakie będą wtedy potrzebne

// Interfejs do obsługi baz danych
// Implementują go: BookDB, MagazineDB, AudiobookDB
// Posiada wszystkie metody, które wymagają połączenia z bazą danych, a więc:
// tworzenie tabeli, dodawanie publikacji (książki, czasopisma, audiobooku), zwracanie wszystkich publikacji
// wypożyczanie danej publikacji (po ID) przez danego użytkownika (po ID)
// zwracanie danej publikacji (po ID) przez danego użytkownika (po ID)
// usuwanie danej publikacji po ID
