import java.time.LocalDate;

public interface Publication {
    void displayInfo();
}

// Glowny interfejs
// Implementują go: BookObj, MagazineObj, AudioBookObj
// zawiera metode displayInfo(), która będzie Override'owana przez powyższe obiekty, gdzie będzie służyła do wyświetlania wszystkich pól obiektu