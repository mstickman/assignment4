package model;
import repository.BookRepository;
import service.BookService;
import utils.ReflectionUtils;

public class Main {

    public static void main(String[] args) {
        BookService service = new BookService(new BookRepository());

        Category fiction = new Category(1, "Fiction");

        System.out.println("CREATE BOOKS");

        BookBase b1 = new PrintedBook(0, "1488", "Master Beach", fiction, 12.5, 328);

        BookBase b2 = new Ebook(0, "Tony Boltyn", "Valilonga", fiction, 15.0, 5.2);

        service.addBook(b1);
        service.addBook(b2);

        System.out.println("\nALL BOOKS");
        service.getAllBooks().forEach(System.out::println);

        System.out.println("\nSORTED BY PRICE");
        service.getBooksSortedByPrice().forEach(b -> System.out.println(b.getName() + " -> " + b.getPrice()));

        System.out.println("\nREFLECTION DEMO");
        ReflectionUtils.inspectClass(BookBase.class);

        System.out.println("\nDELETE BOOK");
        service.removeBook(1);
    }
}