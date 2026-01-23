package service;

import model.BookBase;
import repository.BookRepository;

public class BookService {
    private final BookRepository repo = new BookRepository();

    public void addBook(BookBase book) {
        repo.create(book);
    }

    public void showAll() {

        for (BookBase book : repo.getAll()) {
            System.out.println(book.getId() + " | " +
                    book.getEntityType() + " | " +
                    book.getDetails() + " | " +
                    book.getPrice());
        }
    }

    public void removeBook(int id) {
        repo.delete(id);
    }

    public BookBase getBookById(int i) {
        return repo.getById(i);
    }
}