import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MagazineObjTest {

    private List<MagazineObj> magazines;

    @BeforeEach
    void setUp() throws InvalidPublicationException {
        magazines = new ArrayList<>();
        magazines.add(new MagazineObj(1, "Tech World", "TECHNOLOGY", Date.valueOf(LocalDate.of(2022, 1, 10)), 1, 100, 8));
        magazines.add(new MagazineObj(2, "Healthy Life", "HEALTH", Date.valueOf(LocalDate.of(2023, 5, 5)), 2, 80, 5));
        magazines.add(new MagazineObj(3, "Travel Planet", "TRAVEL", Date.valueOf(LocalDate.of(2021, 11, 1)), 3, 50, 6));
    }

    //Test poprawnego tworzenia obiektu
    @Test
    void testMagazineCreation() throws InvalidPublicationException {
        MagazineObj mag = new MagazineObj("Business Weekly", "BUSINESS", Date.valueOf(LocalDate.now()), 1, 120, 10);
        assertEquals("Business Weekly", mag.getTitle());
        assertEquals("BUSINESS", mag.getTopic());
        assertEquals(120, mag.getQuantityInStock());
    }

    //Test rzucenia wyjątku przy niepoprawnych danych
    @Test
    void testInvalidMagazineThrowsException() {
        assertThrows(InvalidPublicationException.class, () -> {
            new MagazineObj("", "HEALTH", Date.valueOf(LocalDate.now()), 1, 50, 4);
        });
        assertThrows(InvalidPublicationException.class, () -> {
            new MagazineObj("Tech Guide", "TECHNOLOGY", Date.valueOf(LocalDate.now()), 1, -5, 4);
        });
        assertThrows(InvalidPublicationException.class, () -> {
            new MagazineObj("Science Digest", "SCIENCE", Date.valueOf(LocalDate.now()), 1, 10, -2);
        });
    }

    //Test metody displayInfo (czy nie rzuca błędów i coś wypisuje)
    @Test
    void testDisplayInfoDoesNotThrow() {
        assertDoesNotThrow(() -> magazines.get(0).displayInfo());
    }

    //Test CRUD (dodanie i usunięcie magazynu)
    @Test
    void testAddAndRemoveMagazine() throws InvalidPublicationException {
        MagazineObj newMag = new MagazineObj("Fashion Now", "LIFESTYLE", Date.valueOf(LocalDate.now()), 4, 70, 6);
        magazines.add(newMag);
        assertEquals(4, magazines.size());
        magazines.remove(newMag);
        assertEquals(3, magazines.size());
    }

    //Test poprawności pól po modyfikacji
    @Test
    void testUpdateMagazineQuantity() throws InvalidPublicationException {
        MagazineObj mag = magazines.get(0);
        int oldQty = mag.getQuantityInStock();
        int newQty = oldQty + 20;
        // symulacja aktualizacji danych
        magazines.set(0, new MagazineObj(
                mag.getId(),
                mag.getTitle(),
                mag.getTopic(),
                mag.getReleaseDate(),
                mag.getPublisherID(),
                newQty,
                mag.getArticlesAmmount()
        ));
        assertEquals(newQty, magazines.get(0).getQuantityInStock());
    }
}
