package net.ausiasmarch.capitals.dao;

import java.sql.*;

import net.ausiasmarch.capitals.model.UserBean;
import net.ausiasmarch.capitals.service.DatabaseService;

public class UserDao {

    // get one user by id
    public UserBean getUserById(int id) {
        try (Connection conn = DatabaseService.getConnection()) {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserBean(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean authenticate(String username, String password) {
        try (Connection conn = DatabaseService.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public UserBean getUserByUsername(String username) {
        try (Connection conn = DatabaseService.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new UserBean(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    // delete user by id
    public boolean deleteUserById(int id) {
        try (Connection conn = DatabaseService.getConnection()) {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // change password by id
    public boolean changePasswordById(int id, String newPassword) {
        try (Connection conn = DatabaseService.getConnection()) {
            String sql = "UPDATE users SET password = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
