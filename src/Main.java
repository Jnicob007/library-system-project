import java.awt.print.Book;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    public static void main() {
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
    }
}