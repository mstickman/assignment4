package repository;

import Interfaces.CrudRepository;
import exception.DatabaseOperationException;
import exception.ResourceNotFoundException;
import model.BookBase;
import model.Ebook;
import model.Category;
import model.PrintedBook;
import utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookRepository implements CrudRepository<BookBase, Integer> {

    @Override
    public void create(BookBase book) {

        book.validate();
        String insertBookSql = "INSERT INTO books (name, author, price, category_id, book_type) VALUES (?, ?, ?, ?, ?)";

        String insertEBookSql = "INSERT INTO ebooks (book_id, file_size_mb) VALUES (?, ?)";

        String insertPrintedSql = "INSERT INTO printed_books (book_id, pages) VALUES (?, ?)";

        Connection con = null;
        PreparedStatement bookPs = null;
        PreparedStatement childPs = null;
        ResultSet keys = null;

        try {
            con = DatabaseConnection.getConnection();
            con.setAutoCommit(false);

            bookPs = con.prepareStatement(insertBookSql, Statement.RETURN_GENERATED_KEYS);

            bookPs.setString(1, book.getName());
            bookPs.setString(2, book.getAuthor());
            bookPs.setDouble(3, book.getPrice());
            bookPs.setInt(4, book.getCategory().getId());
            bookPs.setString(5, book.getEntityType());

            bookPs.executeUpdate();

            keys = bookPs.getGeneratedKeys();
            if (!keys.next()) {
                throw new SQLException("No ID returned");
            }

            int bookId = keys.getInt(1);

            if (book instanceof Ebook ebook) {
                childPs = con.prepareStatement(insertEBookSql);
                childPs.setInt(1, bookId);
                childPs.setDouble(2, ebook.getFileSizeMB());

            }
            if (book instanceof PrintedBook printed) {
                childPs = con.prepareStatement(insertPrintedSql);
                childPs.setInt(1, bookId);
                childPs.setInt(2, printed.getPages());
            }

            childPs.executeUpdate();
            con.commit();

        } catch (Exception e) {
            rollback(con);
            throw new DatabaseOperationException("Failed to create book", e);
        } finally {
            close(keys, childPs, bookPs, con);
        }
    }

    @Override
    public BookBase getById(Integer integer) {
        return null;
    }

    public BookBase getById(int id) {

        String sql = """
        SELECT b.id, b.name, b.author, b.price, b.book_type,
               b.category_id,
               e.file_size_mb,
               p.pages
        FROM books b
        LEFT JOIN ebooks e ON b.id = e.book_id
        LEFT JOIN printed_books p ON b.id = p.book_id
        WHERE b.id = ?
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new ResourceNotFoundException("Book with id " + id + " not found");
            }

            Category category = new Category(
                    rs.getInt("category_id"),
                    "UNKNOWN"
            );

            String type = rs.getString("book_type");

            if ("EBOOK".equals(type)) {
                return new Ebook(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        category,
                        rs.getDouble("price"),
                        rs.getDouble("file_size_mb")
                );
            }

            if ("PRINTED".equals(type)) {
                return new PrintedBook(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        category,
                        rs.getDouble("price"),
                        rs.getInt("pages")
                );
            }

            throw new DatabaseOperationException("Unknown book type: " + type, null);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch book", e);
        }
    }

    public List<BookBase> getAll() {

        List<BookBase> books = new ArrayList<>();

        String sql = """
        SELECT b.id, b.name, b.author, b.price, b.book_type,
               b.category_id,
               e.file_size_mb,
               p.pages
        FROM books b
        LEFT JOIN ebooks e ON b.id = e.book_id
        LEFT JOIN printed_books p ON b.id = p.book_id
        ORDER BY b.id
    """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Category category = new Category(rs.getInt("category_id"), "UNKNOWN");

                String type = rs.getString("book_type");

                if ("EBOOK".equals(type)) {
                    books.add(new Ebook(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            category,
                            rs.getDouble("price"),
                            rs.getDouble("file_size_mb")
                    ));
                } else if ("PRINTED".equals(type)) {
                    books.add(new PrintedBook(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("author"),
                            category,
                            rs.getDouble("price"),
                            rs.getInt("pages")
                    ));
                } else {
                    throw new DatabaseOperationException("Unknown book type: " + type, null);
                }
            }

            return books;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed when getting all books", e);
        }
    }

    @Override
    public void update(Integer integer, BookBase entity) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer integer) {

    }

    public void delete(int id) {
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int affected = ps.executeUpdate();

            if (affected == 0) {
                throw new ResourceNotFoundException("Book with id " + id + " not found");
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to delete book", e);
        }
    }

    private void rollback(Connection con) {
        try {
            if (con != null) con.rollback();
        } catch (SQLException ignored) {}
    }

    private void close(AutoCloseable... res) {
        for (AutoCloseable r : res) {
            try {
                if (r != null) r.close();
            } catch (Exception ignored) {}
        }
    }
}