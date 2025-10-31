public class AudiobookFactory implements GUIFactory {
    @Override
    public Publication createPublication() {
        return new AudiobookObj();
    }

    @Override
    public PublicationDB createPublicationDB() {
        return new AudiobookDB();
    }
}
