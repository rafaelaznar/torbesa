package net.ausiasmarch.codequest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.codequest.model.TechnologyBean;

public class TechnologyDao {

    private Connection oConnection;

    public TechnologyDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    public TechnologyBean get(Long id) throws SQLException {
        TechnologyBean technology = null;
        String sql = "SELECT * FROM codequest_technology WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            technology = new TechnologyBean(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("difficulty")
            );
        }
        return technology;
    }

    public List<TechnologyBean> getAll() throws SQLException {
        List<TechnologyBean> technologies = new ArrayList<>();
        String sql = "SELECT * FROM codequest_technology ORDER BY name ASC";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        while (rs.next()) {
            technologies.add(new TechnologyBean(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("difficulty")
            ));
        }
        return technologies;
    }

    public List<TechnologyBean> getRandomTechnologies(int count) throws SQLException {
        List<TechnologyBean> technologies = new ArrayList<>();
        String sql = "SELECT * FROM codequest_technology ORDER BY RAND() LIMIT ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, count);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            technologies.add(new TechnologyBean(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("difficulty")
            ));
        }
        return technologies;
    }

    public TechnologyBean getTechnologyByName(String name) throws SQLException {
        TechnologyBean technology = null;
        String sql = "SELECT * FROM codequest_technology WHERE name = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, name);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            technology = new TechnologyBean(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("difficulty")
            );
        }
        return technology;
    }

    public List<TechnologyBean> getTechnologiesByType(String type) throws SQLException {
        List<TechnologyBean> technologies = new ArrayList<>();
        String sql = "SELECT * FROM codequest_technology WHERE type = ? ORDER BY name ASC";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, type);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            technologies.add(new TechnologyBean(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("difficulty")
            ));
        }
        return technologies;
    }

    public List<TechnologyBean> getTechnologiesByCategory(String category) throws SQLException {
        List<TechnologyBean> technologies = new ArrayList<>();
        String sql = "SELECT * FROM codequest_technology WHERE category = ? ORDER BY name ASC";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, category);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            technologies.add(new TechnologyBean(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("difficulty")
            ));
        }
        return technologies;
    }

    public List<TechnologyBean> getTechnologiesByDifficulty(String difficulty) throws SQLException {
        List<TechnologyBean> technologies = new ArrayList<>();
        String sql = "SELECT * FROM codequest_technology WHERE difficulty = ? ORDER BY name ASC";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, difficulty);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            technologies.add(new TechnologyBean(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getString("difficulty")
            ));
        }
        return technologies;
    }

    public int create(TechnologyBean technology) throws SQLException {
        String sql = "INSERT INTO codequest_technology (name, type, description, category, difficulty) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, technology.getName());
        stmt.setString(2, technology.getType());
        stmt.setString(3, technology.getDescription());
        stmt.setString(4, technology.getCategory());
        stmt.setString(5, technology.getDifficulty());
        return stmt.executeUpdate();
    }

    public int update(TechnologyBean technology) throws SQLException {
        String sql = "UPDATE codequest_technology SET name = ?, type = ?, description = ?, category = ?, difficulty = ? WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setString(1, technology.getName());
        stmt.setString(2, technology.getType());
        stmt.setString(3, technology.getDescription());
        stmt.setString(4, technology.getCategory());
        stmt.setString(5, technology.getDifficulty());
        stmt.setLong(6, technology.getId());
        return stmt.executeUpdate();
    }

    public int delete(Long id) throws SQLException {
        String sql = "DELETE FROM codequest_technology WHERE id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setLong(1, id);
        return stmt.executeUpdate();
    }

    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM codequest_technology";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            return rs.getInt("total");
        }
        return 0;
    }
}

