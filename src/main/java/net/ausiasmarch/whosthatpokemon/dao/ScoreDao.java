package net.ausiasmarch.whosthatpokemon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import net.ausiasmarch.whosthatpokemon.model.ScoreBean;

public class ScoreDao {

    Connection oConnection = null;

    //le pasamos la conexión
    public ScoreDao(Connection oConnection) {
        this.oConnection = oConnection;
    }

    //recupera la puntuación del usuario
    public ScoreBean get(int userId) throws SQLException {
        if (count(userId) > 1) {
            sanitize();
        }
        ScoreBean oScore = null;
        String sql = "SELECT * FROM wtpokemon_score, users WHERE wtpokemon_score.user_id = ? AND users.id = ? ORDER BY score DESC";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            oScore = new ScoreBean(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getString("username"));
        }
        return oScore;
    }

    public int count(int userId) throws SQLException {
        int count = 0;
        String sql = "SELECT COUNT(*) AS score_count FROM wtpokemon_score WHERE user_id = ?";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            count = rs.getInt("score_count");
        }
        return count;
    }

    public int count() throws SQLException {
        String countSql = "SELECT COUNT(*) AS total FROM wtpokemon_score";
        PreparedStatement countStmt = oConnection.prepareStatement(countSql);
        ResultSet rs = countStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }
    //elemina las puntuaciones viejas si tienes una nueva con mejor puntuación
    public void sanitize() throws SQLException {
        String sql = "DELETE s1 FROM wtpokemon_score s1 " +
                "INNER JOIN wtpokemon_score s2 ON s1.user_id = s2.user_id " +
                "WHERE s1.score < s2.score";
        PreparedStatement stmt = oConnection.prepareStatement(sql);
        int deletedRows = stmt.executeUpdate();
        System.out.println("Sanitized " + deletedRows + " duplicate scores");
    }

    //recupera las 10 mejores puntuaciones
    public List<ScoreBean> getTop10() throws SQLException {
        //creamos un array donde guardarlas
        List<ScoreBean> scores = new ArrayList<>();
        String sql = "SELECT * FROM wtpokemon_score, users ";
        sql += "WHERE wtpokemon_score.user_id = users.id ";
        sql += "ORDER BY wtpokemon_score.score DESC LIMIT 10";
        Statement stmt = oConnection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        //guardamos cada puntuación en el array
        while (rs.next()) {
            scores.add(new ScoreBean(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getString("username")));
        }
        //devolvemos el array
        return scores;
    }

    public List<ScoreBean> getAll() throws SQLException {
        String sql = "SELECT * FROM wtpokemon_score, users ";
        sql += "WHERE wtpokemon_score.user_id = users.id";
        PreparedStatement getAllStmt = oConnection.prepareStatement(sql);
        ResultSet rs = getAllStmt.executeQuery();
        List<ScoreBean> scores = new ArrayList<>();
        while (rs.next()) {
            scores.add(new ScoreBean(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("score"),
                    rs.getInt("tries"),
                    rs.getString("username")));
        }
        return scores;
    }

    //cuando el usuario es creado nuevo inserta una nueva puntuación
    public int insert(ScoreBean oScore) throws SQLException {
        String insertSql = "INSERT INTO wtpokemon_score (user_id, score, tries) VALUES (?, ?, ?)";
        PreparedStatement insertStmt = oConnection.prepareStatement(insertSql);
        insertStmt.setInt(1, oScore.getUserId());
        insertStmt.setInt(2, oScore.getScore());
        insertStmt.setInt(3, oScore.getTries());
        return insertStmt.executeUpdate();
    }

    //actualiza la puntuación y los intentos si el usuario ya exstia
    public int update(ScoreBean oScore) throws SQLException {
        String updateSql = "UPDATE wtpokemon_score SET score = ?, tries = ? WHERE user_id = ?";
        PreparedStatement updateStmt = oConnection.prepareStatement(updateSql);
        updateStmt.setInt(1, oScore.getScore());
        updateStmt.setInt(2, oScore.getTries());
        updateStmt.setInt(3, oScore.getUserId());
        return updateStmt.executeUpdate();
    }

    public int delete(int id) throws SQLException {
        String deleteSql = "DELETE FROM wtpokemon_score WHERE id = ?";
        PreparedStatement deleteStmt = oConnection.prepareStatement(deleteSql);
        deleteStmt.setInt(1, id);
        return deleteStmt.executeUpdate();
    }

}
