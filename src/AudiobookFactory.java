public class AudiobookFactory implements GUIFactory { // AudiobookFactory rozszerzające interfejs do fabryk
    @Override
    public Publication createPublication() { // metoda zwracająca nowy obiekt audiobooka
        return new AudiobookObj();
    }

    @Override
    public PublicationDB createPublicationDB() { // metoda zwracająca nowy obiekt audiobooka do baz danych
        return new AudiobookDB();
    }
}
