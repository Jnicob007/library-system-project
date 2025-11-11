import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookObjTest {

    private List<BookObj> books;

    @BeforeEach
    void setUp() {
        books = new ArrayList<>();
        books.add(new BookObj(1, "Zebra Tales", "Adventure", Date.valueOf(LocalDate.of(2010, 5, 20)), 1, 1, 10, "English", 200));
        books.add(new BookObj(2, "Alpha Code", "Sci-Fi", Date.valueOf(LocalDate.of(2020, 1, 10)), 2, 1, 5, "English", 350));
        books.add(new BookObj(3, "Beta Chronicles", "History", Date.valueOf(LocalDate.of(2015, 3, 12)), 3, 2, 3, "Polish", 150));
    }

    //Test tworzenia obiektu
    @Test
    void testBookCreation() {
        BookObj book = new BookObj("Test Book", "Fantasy", Date.valueOf(LocalDate.now()), 1, 1, 5, "English", 300);
        assertEquals("Test Book", book.getTitle());
        assertEquals("Fantasy", book.getGenre());
        assertEquals(300, book.getPagesAmmount());
    }

    //Test sortowania po tytule
    @Test
    void testSortByTitle() {
        Collections.sort(books, new BookObj.TitleComparator());
        assertEquals("Alpha Code", books.get(0).getTitle());
        assertEquals("Zebra Tales", books.get(2).getTitle());
    }

    //Test sortowania po dacie
    @Test
    void testSortByReleaseDate() {
        Collections.sort(books, new BookObj.ReleaseDateComparator());
        assertEquals("Zebra Tales", books.get(0).getTitle()); // najstarsza
        assertEquals("Alpha Code", books.get(2).getTitle());  // najnowsza
    }

    //Test sortowania po liczbie stron
    @Test
    void testSortByPages() {
        Collections.sort(books, new BookObj.PagesAmountComparator());
        assertEquals(150, books.get(0).getPagesAmmount());
        assertEquals(350, books.get(2).getPagesAmmount());
    }

    //Test walidacji i wyjątku
    @Test
    void testInvalidMagazineThrowsException() {
        assertThrows(InvalidPublicationException.class, () -> {
            new MagazineObj("", "TECHNOLOGY", Date.valueOf(LocalDate.now()), 1, 10, 5);
        });
    }

    //Test CRUD – dodanie i usunięcie książki
    @Test
    void testAddAndRemoveBook() {
        BookObj book = new BookObj("Temp Book", "Drama", Date.valueOf(LocalDate.now()), 1, 1, 2, "English", 250);
        books.add(book);
        assertEquals(4, books.size());
        books.remove(book);
        assertEquals(3, books.size());
    }
}
