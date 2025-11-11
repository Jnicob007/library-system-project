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
        List<MagazineObj> magazines = new ArrayList<>();
        List<BookObj> books = new ArrayList<>();
        List<AudiobookObj> audiobooks = new ArrayList<>();
        boolean running = true;

        while (running) {
            System.out.println("\n=== Publication Management Menu ===");
            System.out.println("1. Add Magazine");
            System.out.println("2. View All Magazines");
            System.out.println("3. Sort Magazines by Title");
            System.out.println("4. Sort Books");
            System.out.println("5. Add Book");
            System.out.println("6. View All Books");
            System.out.println("7. Add Audiobook");
            System.out.println("8. View All Audiobooks");
            System.out.println("9. Delete Audiobook");
            System.out.println("10. Exit");
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

                    // Walidacja tematu
                    String topic;
                    try {
                        MagazineObj.Topic chosenTopic = MagazineObj.Topic.valueOf(topicInput);
                        topic = chosenTopic.name(); // zapisujemy nazwę jako String
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid topic. Defaulting to TECHNOLOGY.");
                        topic = MagazineObj.Topic.TECHNOLOGY.name();
                    }

                    // Pozostałe dane
                    System.out.print("Enter publisher ID: ");
                    int publisherID = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter quantity in stock: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter number of articles: ");
                    int articles = Integer.parseInt(scanner.nextLine());

                    // Tworzenie obiektu MagazineObj
                    MagazineObj magazine = null;
                    try {
                        magazine = new MagazineObj(title, topic, Date.valueOf(LocalDate.now()), publisherID, quantity, articles);
                    } catch (InvalidPublicationException e) {
                        throw new RuntimeException(e);
                    }
                    magazines.add(magazine);
                    System.out.println("Magazine added successfully!");
                    break;

                case 2:
                    if (magazines.isEmpty()) {
                        System.out.println("No magazines available.");
                    } else {
                        System.out.println("\n--- List of Magazines ---");
                        for (MagazineObj m : magazines) {
                            m.displayInfo();
                            System.out.println("------------------");
                        }
                    }
                    break;

                case 3:
                    if (magazines.isEmpty()) {
                        System.out.println("No magazines to sort.");
                    } else {
                        magazines.sort(Comparator.comparing(MagazineObj::getTitle, String.CASE_INSENSITIVE_ORDER));
                        System.out.println("Magazines sorted by title.");
                    }
                    break;

                case 4:
                    if (books.isEmpty()) {
                        System.out.println("No books to sort.");
                    } else {
                        System.out.println("Choose sorting method:");
                        System.out.println("1. By title");
                        System.out.println("2. By release date");
                        System.out.println("3. By number of pages");
                        int choice1 = Integer.parseInt(scanner.nextLine());

                        switch (choice1) {
                            case 1:
                                books.sort(new BookObj.TitleComparator());
                                System.out.println("Books sorted by title.");
                                break;
                            case 2:
                                books.sort(new BookObj.ReleaseDateComparator());
                                System.out.println("Books sorted by release date.");
                                break;
                            case 3:
                                books.sort(new BookObj.PagesAmountComparator());
                                System.out.println("Books sorted by number of pages.");
                                break;
                            default:
                                System.out.println("Invalid choice.");
                        }

                        for (BookObj b : books) {
                            b.displayInfo();
                        }
                    }
                    break;

                case 5: // Dodawanie książki
                    System.out.print("Enter book title: ");
                    String bookTitle = scanner.nextLine();

                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();

                    System.out.print("Enter number of pages: ");
                    int pages = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter release date (yyyy-mm-dd): ");
                    Date releaseDate = Date.valueOf(scanner.nextLine());

                    BookObj book = null;
                    book = new BookObj(bookTitle, author, pages, releaseDate);
                    books.add(book);
                    System.out.println("Book added successfully!");
                    break;

                case 6: // Wyświetlanie książek
                    if (books.isEmpty()) {
                        System.out.println("No books available.");
                    } else {
                        System.out.println("\n--- List of Books ---");
                        for (BookObj b : books) {
                            b.displayInfo();
                            System.out.println("------------------");
                        }
                    }
                    break;

                case 7: // Dodawanie audiobooka
                    System.out.print("Enter audiobook title: ");
                    String audioTitle = scanner.nextLine();

                    System.out.print("Enter author: ");
                    String audioAuthor = scanner.nextLine();

                    System.out.print("Enter duration in minutes: ");
                    int duration = Integer.parseInt(scanner.nextLine());

                    AudiobookObj audiobook = null;
                    audiobook = new AudiobookObj(audioTitle, audioAuthor, duration);
                    audiobooks.add(audiobook);
                    System.out.println("Audiobook added successfully!");
                    break;

                case 8: // Wyświetlanie audiobooków
                    if (audiobooks.isEmpty()) {
                        System.out.println("No audiobooks available.");
                    } else {
                        System.out.println("\n--- List of Audiobooks ---");
                        for (AudiobookObj a : audiobooks) {
                            a.displayInfo();
                            System.out.println("------------------");
                        }
                    }
                    break;

                case 9: // Usuwanie audiobooka
                    if (audiobooks.isEmpty()) {
                        System.out.println("No audiobooks to delete.");
                    } else {
                        System.out.print("Enter the index of audiobook to delete (starting from 1): ");
                        int index = Integer.parseInt(scanner.nextLine()) - 1;
                        if (index >= 0 && index < audiobooks.size()) {
                            audiobooks.remove(index);
                            System.out.println("Audiobook deleted successfully!");
                        } else {
                            System.out.println("Invalid index.");
                        }
                    }
                    break;

                case 10:
                    System.out.println("Exiting program...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();

    }
}