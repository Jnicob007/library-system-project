public class MagazineFactory implements GUIFactory {
    @Override
    public Publication createPublication() {
        return new MagazineObj();
    }

    @Override
    public PublicationDB createPublicationDB() {
        return new MagazineDB();
    }
}
