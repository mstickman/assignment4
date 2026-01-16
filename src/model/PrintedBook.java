package model;

import exception.InvalidInputException;

public class PrintedBook extends BookBase {
    private final int pages;

    public PrintedBook(int id, String name, String author, Category category, double price, int pages)
    {
        super(id, name, author, category, price);
        this.pages = pages;
    }

    @Override
    public String getEntityType() {
        return "PRINTED";
    }

    @Override
    public String getDetails() {
        return name + " (" + pages + " pages)";
    }

    public int getPages() {
        return pages;
    }

    @Override
    public double getFileSizeMB() {
        return 0;
    }

    @Override
    public void validate() {
        if (getName() == null || getName().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        if (getAuthor() == null || getAuthor().isEmpty()) {
            throw new InvalidInputException("Author cannot be empty");
        }
        if (getPrice() <= 0) {
            throw new InvalidInputException("Price must be positive");
        }
        if (getPages() <= 0) {
            throw new InvalidInputException("Pages must be positive");
        }
    }
}