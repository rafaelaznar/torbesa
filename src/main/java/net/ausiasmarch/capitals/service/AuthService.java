package net.ausiasmarch.capitals.service;

import java.sql.*;

import net.ausiasmarch.capitals.connection.HikariConnection;
import net.ausiasmarch.capitals.model.UserBean;

public class AuthService {

    public boolean authenticate(String username, String password) throws SQLException {
        HikariConnection oHikariConnection = new HikariConnection();
        Connection oConnection = oHikariConnection.getConnection();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }

    public UserBean getUserByUsername(String username) throws SQLException {
        HikariConnection oHikariConnection = new HikariConnection();
        Connection oConnection = oHikariConnection.getConnection();
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

}
