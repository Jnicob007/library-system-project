import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AudiobookObjTest {

    private List<AudiobookObj> audiobooks;

    @BeforeEach
    void setUp() {
        audiobooks = new ArrayList<>();
        audiobooks.add(new AudiobookObj(1, "The Time Machine", "Science Fiction", Date.valueOf(LocalDate.of(2018, 5, 15)), 1, 1, 10, "06:30:00", 3));
        audiobooks.add(new AudiobookObj(2, "The Hobbit", "Fantasy", Date.valueOf(LocalDate.of(2020, 12, 10)), 2, 1, 5, "10:15:00", 5));
        audiobooks.add(new AudiobookObj(3, "1984", "Dystopian", Date.valueOf(LocalDate.of(2015, 3, 8)), 3, 2, 7, "07:45:00", 4));
    }

    //Test poprawnego tworzenia obiektu
    @Test
    void testAudiobookCreation() {
        AudiobookObj ab = new AudiobookObj("Dune", "Science Fiction", Date.valueOf(LocalDate.of(2021, 6, 1)), 1, 2, 8, "09:30:00", 4);
        assertEquals("Dune", ab.getTitle());
        assertEquals("Science Fiction", ab.getGenre());
        assertEquals("09:30:00", ab.getRecordingLength());
        assertEquals(4, ab.getAvailableLanguagesAmmount());
    }

    //Test działania metody displayInfo (czy nie rzuca wyjątków)
    @Test
    void testDisplayInfoDoesNotThrow() {
        assertDoesNotThrow(() -> audiobooks.get(0).displayInfo());
    }

    //Test CRUD — dodanie i usunięcie audiobooka z listy
    @Test
    void testAddAndRemoveAudiobook() {
        AudiobookObj newAb = new AudiobookObj("Brave New World", "Dystopian", Date.valueOf(LocalDate.now()), 4, 3, 6, "08:00:00", 2);
        audiobooks.add(newAb);
        assertEquals(4, audiobooks.size());
        audiobooks.remove(newAb);
        assertEquals(3, audiobooks.size());
    }

    //Test aktualizacji (update)
    @Test
    void testUpdateAudiobookQuantity() {
        AudiobookObj ab = audiobooks.get(1);
        int oldQty = ab.getQuantityInStock();
        int newQty = oldQty + 5;

        // symulacja aktualizacji poprzez nowy obiekt
        audiobooks.set(1, new AudiobookObj(
                ab.getId(),
                ab.getTitle(),
                ab.getGenre(),
                ab.getReleaseDate(),
                ab.getAuthorID(),
                ab.getPublisherID(),
                newQty,
                ab.getRecordingLength(),
                ab.getAvailableLanguagesAmmount()
        ));

        assertEquals(newQty, audiobooks.get(1).getQuantityInStock());
    }

    //Test daty wydania (czy nie jest przyszła)
    @Test
    void testReleaseDateIsNotInFuture() {
        AudiobookObj ab = audiobooks.get(0);
        assertTrue(!ab.getReleaseDate().after(Date.valueOf(LocalDate.now())));
    }
}
