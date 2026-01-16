package model;

import Interfaces.Validatable;
import Interfaces.PricedItem;

public abstract class BookBase extends BaseEntity implements Validatable, PricedItem {

    protected String author;
    protected Category category;
    protected double price;

    public BookBase(int id, String name, String author, Category category, double price) {
        super(id, name);
        this.author = author;
        this.category = category;
        this.price = price;
    }

    @Override
    public void validate() {

    }

    @Override
    public double getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }

    public abstract double getFileSizeMB();
}
