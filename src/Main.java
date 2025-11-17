import java.awt.print.Book;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class Main{
    public static void main() throws InvalidPublicationException {
        DBConnection.initDatabase(); // inicjacja połączenia z bazą

        // tworzenie skanera, zmiennej interfejsów
        Scanner keyboard = new Scanner(System.in);
        GUIFactory factory;
        Publication p;
        PublicationDB pDB;

        // aby obsłużyć program, do interfejsu z fabryką przypisujemy odpowiednią fabrykę, np. AudiobookFactory
        // następnie do zmiennych p oraz pDB przypisujemy odpowiedni obiekt publikacji oraz obiekt do obslugi operacji na bazie danych
        // program sam wykrywa odpowiednie typy obiektów przez wczesniejsze przypisanie odpowiedniej fabryki do zmiennej factory
        factory = new AudiobookFactory();
        p = factory.createPublication();
        pDB = factory.createPublicationDB();
        p.displayInfo();
        pDB.addPublication(p);
        pDB.deletePublication(3);

        // tutaj menu jakies ze switchem
        Scanner scanner = new Scanner(System.in);
        ArrayList<MagazineObj> magazinesTempArray;
        ArrayList<BookObj> booksTempArray;
        ArrayList<AudiobookObj> audiobooksTempArray;
        int publicationID, userID;
        boolean running = true;

        while (running) {
            System.out.println("\n=== Publication Management Menu ===");
            System.out.println("1. Add Magazine");
            System.out.println("2. View All Magazines");
            System.out.println("3. Sort Magazines by Title");
            System.out.println("4. Rent a Magazine");
            System.out.println("5. Return a Magazine to Stock");
            System.out.println("6. Delete a Magazine");
            System.out.println("7. Add Book");
            System.out.println("8. View All Books");
            System.out.println("9. Sort Books");
            System.out.println("10. Rent a Book");
            System.out.println("11. Return a Book to Stock");
            System.out.println("12. Delete a Book");
            System.out.println("13. Add Audiobook");
            System.out.println("14. View All Audiobooks");
            System.out.println("15. Rent an Audiobook");
            System.out.println("16. Return an Audiobook to Stock");
            System.out.println("17. Delete an Audiobook");
            System.out.println("18. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter magazine title: ");
                    String title = scanner.nextLine();

                    // Wyświetlenie dostępnych tematów z enum
                    System.out.println("Available topics: " + Arrays.toString(MagazineObj.Topic.values()));
                    System.out.print("Choose topic: ");
                    String topicInput = scanner.nextLine().toUpperCase();
                    MagazineObj.Topic chosenTopic;

                    // Walidacja tematu
                    String topic;
                    try {
                        chosenTopic = MagazineObj.Topic.valueOf(topicInput);
//                        topic = chosenTopic.name(); // zapisujemy nazwę jako String
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid topic. Defaulting to TECHNOLOGY.");
                        chosenTopic = MagazineObj.Topic.TECHNOLOGY;
                    }

                    // Pozostałe dane
                    System.out.print("Enter publisher ID: ");
                    int publisherID = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter quantity in stock: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter number of articles: ");
                    int articles = Integer.parseInt(scanner.nextLine());

                    // Tworzenie obiektu MagazineObj
                    try {
                        factory = new MagazineFactory();
                        pDB = factory.createPublicationDB();
                        p = new MagazineObj(title, chosenTopic, Date.valueOf(LocalDate.now()), publisherID, quantity, articles);
                        pDB.addPublication(p);
                    } catch (InvalidPublicationException e) {
                        System.out.println(e.getMessage());
                        throw new RuntimeException(e);
                    }

                    break;
                case 2:
                    factory = new MagazineFactory();
                    pDB = factory.createPublicationDB();
                    magazinesTempArray = pDB.getAllPublications();
                    for(MagazineObj m : magazinesTempArray){
                        m.displayInfo();
                    }
//
                    break;
                case 3:
                    factory = new MagazineFactory();
                    pDB = factory.createPublicationDB();
                    magazinesTempArray = pDB.getAllPublications();
                    magazinesTempArray.sort(Comparator.comparing(MagazineObj::getTitle, String.CASE_INSENSITIVE_ORDER));
//                    if (magazines.isEmpty()) {
//                        System.out.println("No magazines to sort.");
//                    } else {
//                        magazines.sort(Comparator.comparing(MagazineObj::getTitle, String.CASE_INSENSITIVE_ORDER));
//                        System.out.println("Magazines sorted by title.");
//                    }
                    break;
                case 4:
                    factory = new MagazineFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Magazine ID: ");
                    publicationID = scanner.nextInt();
                    System.out.println("Enter User ID: ");
                    userID = scanner.nextInt();
                    pDB.rentPublication(publicationID, userID);

                    break;
                case 5:
                    factory = new MagazineFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Magazine ID: ");
                    publicationID = scanner.nextInt();
                    System.out.println("Enter User ID: ");
                    userID = scanner.nextInt();
                    pDB.returnPublication(publicationID, userID);

                    break;
                case 6:
                    factory = new MagazineFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Magazine ID: ");
                    publicationID = scanner.nextInt();
                    pDB.deletePublication(publicationID);

                    break;
                case 7:
                    factory = new BookFactory();
                    pDB = factory.createPublicationDB();

                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();
                    System.out.println("Enter genre: ");
                    String bookGenre = scanner.nextLine();
                    System.out.print("Enter release date (yyyy-mm-dd): ");
                    Date bookReleaseDate = Date.valueOf(scanner.nextLine());
                    System.out.print("Enter author ID: ");
                    int bookAuthorID = scanner.nextInt();
                    System.out.println("Enter publisher ID: ");
                    int bookPublisherID = scanner.nextInt();
                    System.out.println("Enter quantity in stock: ");
                    int bookQuantityInStock = scanner.nextInt();
                    System.out.println("Enter language: ");
                    String bookLanguage = scanner.nextLine();
                    System.out.print("Enter number of pages: ");
                    int bookPagesAmmount = Integer.parseInt(scanner.nextLine());

                    p = new BookObj(bookTitle, bookGenre, bookReleaseDate, bookAuthorID, bookPublisherID, bookQuantityInStock, bookLanguage, bookPagesAmmount);
                    pDB.addPublication(p);
                    System.out.println("Book added successfully!");

                    break;
                case 8:
                    factory = new BookFactory();
                    pDB = factory.createPublicationDB();
                    booksTempArray = pDB.getAllPublications();
                    for(BookObj b : booksTempArray){
                        b.displayInfo();
                    }
//
                    break;
                case 9:
                    factory = new BookFactory();
                    pDB = factory.createPublicationDB();
                    booksTempArray = pDB.getAllPublications();

                    if (booksTempArray.isEmpty()) {
                        System.out.println("No books to sort.");
                    } else {
                        System.out.println("Choose sorting method:");
                        System.out.println("1. By title");
                        System.out.println("2. By release date");
                        System.out.println("3. By number of pages");
                        int choice1 = Integer.parseInt(scanner.nextLine());

                        switch (choice1) {
                            case 1:
                                booksTempArray.sort(new BookObj.TitleComparator());
                                System.out.println("Books sorted by title.");
                                break;
                            case 2:
                                booksTempArray.sort(new BookObj.ReleaseDateComparator());
                                System.out.println("Books sorted by release date.");
                                break;
                            case 3:
                                booksTempArray.sort(new BookObj.PagesAmountComparator());
                                System.out.println("Books sorted by number of pages.");
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }

                        for (BookObj b : booksTempArray) {
                            b.displayInfo();
                        }
                    }
                    break;
                case 10:
                    factory = new BookFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Book ID: ");
                    publicationID = scanner.nextInt();
                    System.out.println("Enter User ID: ");
                    userID = scanner.nextInt();
                    pDB.rentPublication(publicationID, userID);

                    break;
                case 11:
                    factory = new BookFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Book ID: ");
                    publicationID = scanner.nextInt();
                    System.out.println("Enter User ID: ");
                    userID = scanner.nextInt();
                    pDB.returnPublication(publicationID, userID);

                    break;
                case 12:
                    factory = new BookFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Book ID: ");
                    publicationID = scanner.nextInt();
                    pDB.deletePublication(publicationID);

                    break;
                case 13:
                    factory = new AudiobookFactory();
                    pDB = factory.createPublicationDB();

                    System.out.print("Enter audiobook title: ");
                    String audiobookTitle = scanner.nextLine();
                    System.out.println("Enter genre: ");
                    String audiobookGenre = scanner.nextLine();
                    System.out.print("Enter release date (yyyy-mm-dd): ");
                    Date audiobookReleaseDate = Date.valueOf(scanner.nextLine());
                    System.out.print("Enter author ID: ");
                    int audiobookAuthorID = scanner.nextInt();
                    System.out.println("Enter publisher ID: ");
                    int audiobookPublisherID = scanner.nextInt();
                    System.out.println("Enter quantity in stock: ");
                    int audiobookQuantityInStock = scanner.nextInt();
                    System.out.println("Enter recording length (00:00:00): ");
                    String audiobookRecordingLength = scanner.nextLine();
                    System.out.print("Enter ammount of available languages: ");
                    int audiobookAvailableLanguagesAmmount = Integer.parseInt(scanner.nextLine());

                    p = new AudiobookObj(audiobookTitle, audiobookGenre, audiobookReleaseDate, audiobookAuthorID, audiobookPublisherID, audiobookQuantityInStock, audiobookRecordingLength, audiobookAvailableLanguagesAmmount);
                    pDB.addPublication(p);
                    System.out.println("Audiobook added successfully!");

                    break;
                case 14:
                    factory = new AudiobookFactory();
                    pDB = factory.createPublicationDB();
                    audiobooksTempArray = pDB.getAllPublications();
                    for(AudiobookObj a : audiobooksTempArray){
                        a.displayInfo();
                    }
//
                    break;
                case 15:
                    factory = new AudiobookFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Audiobook ID: ");
                    publicationID = scanner.nextInt();
                    System.out.println("Enter User ID: ");
                    userID = scanner.nextInt();
                    pDB.rentPublication(publicationID, userID);

                    break;
                case 16:
                    factory = new AudiobookFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Audiobook ID: ");
                    publicationID = scanner.nextInt();
                    System.out.println("Enter User ID: ");
                    userID = scanner.nextInt();
                    pDB.returnPublication(publicationID, userID);

                    break;
                case 17:
                    factory = new AudiobookFactory();
                    pDB = factory.createPublicationDB();

                    System.out.println("Enter Audiobook ID: ");
                    publicationID = scanner.nextInt();
                    pDB.deletePublication(publicationID);

                    break;
                case 18:
                    System.out.println("Exiting program...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
//                case 4:
//
//
//                case 5: // Dodawanie książki
//
//                case 6: // Wyświetlanie książek
//                    if (books.isEmpty()) {
//                        System.out.println("No books available.");
//                    } else {
//                        System.out.println("\n--- List of Books ---");
//                        for (BookObj b : books) {
//                            b.displayInfo();
//                            System.out.println("------------------");
//                        }
//                    }
//                    break;
//
//                case 7: // Dodawanie audiobooka
//                    System.out.print("Enter audiobook title: ");
//                    String audioTitle = scanner.nextLine();
//
//                    System.out.print("Enter author: ");
//                    String audioAuthor = scanner.nextLine();
//
//                    System.out.print("Enter duration in minutes: ");
//                    int duration = Integer.parseInt(scanner.nextLine());
//
//                    AudiobookObj audiobook = null;
//                    audiobook = new AudiobookObj(audioTitle, audioAuthor, duration);
//                    audiobooks.add(audiobook);
//                    System.out.println("Audiobook added successfully!");
//                    break;
//
//                case 8: // Wyświetlanie audiobooków
//                    if (audiobooks.isEmpty()) {
//                        System.out.println("No audiobooks available.");
//                    } else {
//                        System.out.println("\n--- List of Audiobooks ---");
//                        for (AudiobookObj a : audiobooks) {
//                            a.displayInfo();
//                            System.out.println("------------------");
//                        }
//                    }
//                    break;
//
//                case 9: // Usuwanie audiobooka
//                    if (audiobooks.isEmpty()) {
//                        System.out.println("No audiobooks to delete.");
//                    } else {
//                        System.out.print("Enter the index of audiobook to delete (starting from 1): ");
//                        int index = Integer.parseInt(scanner.nextLine()) - 1;
////                        if (index >= 0 && index < audiobooks.size()) {
////                            audiobooks.remove(index);
////                            System.out.println("Audiobook deleted successfully!");
////                        } else {
////                            System.out.println("Invalid index.");
////                        }
//
//                    }
////                    break;
            }
        }
        scanner.close();

    }
}