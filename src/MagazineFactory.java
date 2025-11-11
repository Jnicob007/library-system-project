public class MagazineFactory implements GUIFactory { // klasa MagazineFactory implementuje interfejs do fabryk
    @Override
    public Publication createPublication() throws InvalidPublicationException { // metoda zwracająca nowy obiekt MagazineObj
        return new MagazineObj();
    }

    @Override
    public PublicationDB createPublicationDB() { // metoda zwracająca nowy obiekt MagazineDB
        return new MagazineDB();
    }
}
