public interface GUIFactory {
    Publication createPublication() throws InvalidPublicationException;
    PublicationDB createPublicationDB();
}

// Interfejs do fabryk
// Będzie to interfejs do sterowania programem
// Implementują go: BookFactory, MagazineFactory, AudiobookFactory
// zawiera metody:
// - createPublication() do tworzenia i zwrócenia obiektu publikacji (BookObj, MagazineObj lub AudiobookObj)
// - createPublicationDB() do tworzenia obiektu do obsługi operacji na bazie danych (BookDB, MagazineDB lub AudiobookDB)
// metody te będą nadpisywane w klasach implementujących ten interfejs, gdzie będą zwracały puste obiekty publikacji oraz do obsługi bazy zależnie od swojego typu (tj. książka, czasopismo, audiobook)