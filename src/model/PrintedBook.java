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
}