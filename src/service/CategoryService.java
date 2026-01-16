package service;

import model.Category;
import repository.CategoryRepository;

public class CategoryService {

    private final CategoryRepository repo = new CategoryRepository();

    public Category addCategory(Category category) {
        return repo.create(category);
    }

    public Category getCategoryById(int id) {
        return repo.getById(id);
    }
}