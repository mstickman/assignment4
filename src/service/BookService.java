package service;

import model.BookBase;
import repository.BookRepository;

public class BookService {
    private final BookRepository repo = new BookRepository();

    public void addBook(BookBase book) {
        book.validate();
        repo.create(book);
    }

    public void showAll() {
        System.out.println(repo.getAll());
    }

    public void removeBook(int id) {
        repo.delete(id);
    }

    public BookBase getBookById(int i) {
        return repo.getById(i);
    }
}