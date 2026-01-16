package repository;

import exception.DatabaseOperationException;
import model.Category;
import utils.DatabaseConnection;

import java.sql.*;

public class CategoryRepository {

    public Category create(Category category) {

        String checkSql = "SELECT id FROM category WHERE name = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement checkPs = con.prepareStatement(checkSql)) {

            checkPs.setString(1, category.getName());
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                return new Category(rs.getInt("id"), category.getName());
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to check category", e);
        }

        String sql = "INSERT INTO category (name) VALUES (?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, category.getName());
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) {
                return new Category(keys.getInt(1), category.getName());
            }

            throw new DatabaseOperationException("Failed to get generated key for category", null);

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to create category", e);
        }
    }

    public Category getById(int id) {
        String sql = "SELECT id, name FROM category WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("name"));
            }

            return null;

        } catch (SQLException e) {
            throw new DatabaseOperationException("Failed to fetch category", e);
        }
    }
}