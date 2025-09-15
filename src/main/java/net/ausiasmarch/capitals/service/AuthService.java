package net.ausiasmarch.capitals.service;

import java.sql.*;

import net.ausiasmarch.capitals.model.User;

public class AuthService {
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

    public User getUserByUsername(String username) {
 
            try (Connection conn = DatabaseService.getConnection()) {
                String sql = "SELECT * FROM users WHERE username = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new User(
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

}
