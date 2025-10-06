package net.ausiasmarch.harrypotter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.ausiasmarch.harrypotter.model.HarryPotterCharacterBean;
import net.ausiasmarch.shared.exception.ResourceNotFoundException;
import net.ausiasmarch.shared.exception.ResourceNotModifiedException;

public class HarryPotterCharacterDAO {

    private Connection oConnection;

    public HarryPotterCharacterDAO(Connection oConnection) {
        this.oConnection = oConnection;
    }

    // 🔹 Obtener personaje por ID
    public HarryPotterCharacterBean getById(int id) throws SQLException, ResourceNotFoundException {
        String sql = "SELECT * FROM harrypotter_character WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new HarryPotterCharacterBean(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("house")
            );
        } else {
            throw new ResourceNotFoundException("Character with id '" + id + "' not found");
        }
    }

    // 🔹 Obtener personaje por nombre
    public HarryPotterCharacterBean getByName(String name) throws SQLException, ResourceNotFoundException {
        String sql = "SELECT * FROM harrypotter_character WHERE name = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new HarryPotterCharacterBean(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("house")
            );
        } else {
            throw new ResourceNotFoundException("Character with name '" + name + "' not found");
        }
    }

    // 🔹 Borrar personaje por ID
    public void deleteById(int id) throws SQLException, ResourceNotModifiedException {
        String sql = "DELETE FROM harrypotter_character WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, id);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new ResourceNotModifiedException("Character with id " + id + " was not deleted");
        }
    }

    // 🔹 Crear personaje (insertar en tabla)
    public Integer create(HarryPotterCharacterBean character)
            throws SQLException, ResourceNotModifiedException {

        // Evitar duplicados por nombre
        if (isPresent(character.getName())) {
            throw new ResourceNotModifiedException(
                    "Character with name '" + character.getName() + "' already exists");
        }

        String sql = "INSERT INTO harrypotter_character (name, house) VALUES (?, ?)";
        PreparedStatement stmt = oConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, character.getName());
        stmt.setString(2, character.getHouse());
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new ResourceNotModifiedException("Character was not created");
        }
        ResultSet generatedKeys = stmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        } else {
            throw new ResourceNotModifiedException("Character created but no ID obtained");
        }
    }

    // 🔹 Actualizar casa por ID (por ejemplo si quisieras editar datos)
    public void updateHouseById(int id, String newHouse)
            throws SQLException, ResourceNotModifiedException {
        String sql = "UPDATE harrypotter_character SET house = ? WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, newHouse);
        stmt.setInt(2, id);
        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected == 0) {
            throw new ResourceNotModifiedException("Character with id " + id + " was not updated");
        }
    }

    // 🔹 Comprobar existencia por nombre
    public boolean isPresent(String name) throws SQLException {
        String sql = "SELECT * FROM harrypotter_character WHERE name = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        return rs.next();
    }
}