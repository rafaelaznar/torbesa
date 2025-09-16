package net.ausiasmarch.capitals.dao;

import java.sql.*;

import net.ausiasmarch.shared.model.UserBean;

public class UserDao {

    Connection oConnection = null;

    public UserDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public UserBean getUserById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
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
    }

    public boolean authenticate(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public UserBean getByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
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
    }

    // delete user by id
    public boolean deleteUserById(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, id);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }

    // change password by id
    public boolean changePasswordById(int id, String newPassword) throws SQLException {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, newPassword);
        stmt.setInt(2, id);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }

}
