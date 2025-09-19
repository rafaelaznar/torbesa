package net.ausiasmarch.shared.dao;

import java.sql.*;

import net.ausiasmarch.shared.exception.ResourceNotFoundException;
import net.ausiasmarch.shared.exception.ResourceNotModifiedException;
import net.ausiasmarch.shared.model.UserBean;

public class UserDao {

    Connection oConnection = null;

    public UserDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public UserBean getUserById(int id) throws SQLException, ResourceNotFoundException {
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
            throw new ResourceNotFoundException("User with id '" + id + "' not found");
        }
    }

    public UserBean getByUsername(String username) throws SQLException, ResourceNotFoundException {
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
            throw new ResourceNotFoundException("User with username '" + username + "' not found");
        }
    }

    public void deleteUserById(int id) throws SQLException, ResourceNotModifiedException {
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, id);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new ResourceNotModifiedException("User with id " + id + " was not deleted");
        }
    }

    public Integer create(UserBean oUserBean) throws SQLException, ResourceNotModifiedException {
        // comprobar si el username ya existe
        if (isPresent(oUserBean.getUsername())) {
            throw new ResourceNotModifiedException(
                    "User with username '" + oUserBean.getUsername() + "' already exists");
        }
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        PreparedStatement stmt = oConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, oUserBean.getUsername());
        stmt.setString(2, oUserBean.getPassword());
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new ResourceNotModifiedException("User was not created");
        }
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new ResourceNotModifiedException("User created but no ID obtained");
        }
    }

    public void changePasswordById(int id, String newPassword) throws SQLException, ResourceNotModifiedException {
        String sql = "UPDATE users SET password = ? WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, newPassword);
        stmt.setInt(2, id);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new ResourceNotModifiedException("Password for user with id " + id + " was not updated");
        }
    }

    public boolean isPresent(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        boolean result = rs.next();
        return result;
    }

    public boolean isPresent(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        boolean result = rs.next();
        return result;
    }

}
