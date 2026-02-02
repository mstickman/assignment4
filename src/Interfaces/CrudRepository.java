package Interfaces;

import java.util.List;

public interface CrudRepository<T, ID> {

    void create(T entity);

    T getById(ID id);

    List<T> getAll();

    void update(ID id, T entity);

    void delete(ID id);
}
