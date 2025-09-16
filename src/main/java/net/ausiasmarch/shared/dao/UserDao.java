package net.ausiasmarch.shared.dao;

import java.sql.*;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

public class UserDao {

    public boolean authenticate(String username, String password) throws SQLException {
        HikariPool oPool = new HikariPool();
        try (Connection oConnection = oPool.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = oConnection.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            boolean result = rs.next();
            return result;
        } finally {
            oPool.disposeConnection();
        }
    }

    public UserBean getByUsername(String username) throws SQLException {
        HikariPool oPool = new HikariPool();
        try (Connection oConnection = oPool.getConnection()) {
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement stmt = oConnection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            UserBean oUserBean = null;
            if (rs.next()) {
                oUserBean = new UserBean(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"));
            } else {
                return null;
            }
            return oUserBean;
        } finally {
            oPool.disposeConnection();
        }
    }

}
