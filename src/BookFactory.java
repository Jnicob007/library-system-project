public class BookFactory implements GUIFactory { // klasa BookFactory implementująca interfejs do fabryk
    @Override
    public Publication createPublication() { // metoda zwracająca nowy obiekt książki
        return new BookObj();
    }

    @Override
    public PublicationDB createPublicationDB() { // metoda zwracająca nowy obiekt książki do baz danych
        return new BookDB();
    }
}
