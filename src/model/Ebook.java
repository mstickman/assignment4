package model;

import exception.InvalidInputException;

public class Ebook extends BookBase {
    private double fileSizeMB;

    public Ebook(int id, String name, String author, Category category, double price, double fileSizeMB) {
        super(id, name, author, category, price);
        this.fileSizeMB = fileSizeMB;
    }

    @Override
    public String getEntityType() {
        return "EBOOK";
    }

    @Override
    public String getDetails() {
        return name + " (" + fileSizeMB + "MB)";
    }

    @Override
    public double getFileSizeMB() {
        return fileSizeMB;
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
        if (getFileSizeMB() <= 0) {
            throw new InvalidInputException("File size must be positive");
        }
    }
}
