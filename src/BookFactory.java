public class BookFactory implements GUIFactory {
    @Override
    public Publication createPublication() {
        return new BookObj();
    }

    @Override
    public PublicationDB createPublicationDB() {
        return new BookDB();
    }
}
