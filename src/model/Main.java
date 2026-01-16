package model;

import model.*;
import service.BookService;
import exception.InvalidInputException;
import exception.ResourceNotFoundException;
import service.CategoryService;

public class Main {

    public static void main(String[] args) {

        BookService service = new BookService();
        CategoryService categoryService = new CategoryService();

        Category fiction = new Category(1, "Fiction");
        fiction = categoryService.addCategory(fiction);

        try {
            BookBase printed = new PrintedBook(
                    0,
                    "1488",
                    "George Floyd",
                    fiction,
                    12.50,
                    328
            );

            BookBase ebook = new Ebook(
                    0,
                    "Breaking bad",
                    "Mister White",
                    fiction,
                    15,
                    5
            );

            service.addBook(printed);
            service.addBook(ebook);

            System.out.println("Books created\n");

            service.getBookById(1);

            System.out.println("PricedItem: " + printed.getPrice());

            //error validation
            BookBase invalid = new PrintedBook(
                    0,
                    "",
                    "",
                    fiction,
                    -5,
                    0
            );
            service.addBook(invalid);

        } catch (InvalidInputException e) {
            System.out.println("Validation failed: " + e.getMessage());

        } catch (ResourceNotFoundException e) {
            System.out.println("Not found: " + e.getMessage());

        } finally {

            try {
                service.removeBook(999);
            } catch (ResourceNotFoundException e) {
                System.out.println("Expected error: " + e.getMessage());
            }
        }
    }
}