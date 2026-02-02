package service;

import Interfaces.CrudRepository;
import model.BookBase;
import repository.BookRepository;
import utils.SortingUtils;

import java.util.List;

public class BookService {

    private final CrudRepository<BookBase, Integer> repo;

    public BookService(CrudRepository<BookBase, Integer> repo) {
        this.repo = repo;
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

    public void addBook(BookBase book) {
        repo.create(book);
    }

    public List<BookBase> getAllBooks() {
        return List.of();
    }

    public List<BookBase> getBooksSortedByPrice() {
        return SortingUtils.sortByPrice(repo.getAll());
    }

    public List<BookBase> getBooksWithMinPrice(double minPrice) {
        return SortingUtils.filterByPrice(repo.getAll(), minPrice);
    }
}