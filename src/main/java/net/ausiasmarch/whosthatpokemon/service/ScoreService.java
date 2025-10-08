package net.ausiasmarch.whosthatpokemon.service;

import java.util.List;
import java.util.Objects;

import net.ausiasmarch.whosthatpokemon.dao.ScoreDao;

import net.ausiasmarch.whosthatpokemon.model.ScoreBean;
import net.ausiasmarch.shared.connection.HikariPool;

import java.sql.*;

public class ScoreService {
    //le pasamos el userId y si ha acertado o no
    public boolean set(int userId, boolean correct) throws SQLException {
        //recuperamos los datos con la hikaripool
        try (Connection oConnection = HikariPool.getConnection()) {
            //te manda al saintize que elimina la vieja puntuación si tienes menos puntos
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            if (oScoreDao.count(userId) > 1) {
                oScoreDao.sanitize();
            }
            //recupera la puntuación del usuario
            ScoreBean oUserScore = oScoreDao.get(userId);
            //si no es null actualiza la puntuación y los intentos
            if (!Objects.isNull(oUserScore)) {
                oUserScore.setTries(oUserScore.getTries() + 1);
                //la puntuación solo en caso de ser correcto
                if (correct) {
                    oUserScore.setScore(oUserScore.getScore() + 1);
                }
                return oScoreDao.update(oUserScore) > 0;
            } 
            //sino crea una nueva puntuación
            else {
                oUserScore = new ScoreBean();
                oUserScore.setUserId(userId);
                oUserScore.setTries(1);
                if (correct) {
                    oUserScore.setScore(1);
                } else {
                    oUserScore.setScore(0);
                }
                return oScoreDao.insert(oUserScore) > 0;
            }
        }

    }
    //recupera las 10 mejores puntuaciones
    public List<ScoreBean> getHighScores() throws SQLException {
        //recuperamos los datos con la hikaripool
        try (Connection oConnection = HikariPool.getConnection()) {
            ScoreDao oScoreDao = new ScoreDao(oConnection);
            //nos recupera las 10 mejores puntuaciones
            return oScoreDao.getTop10();
        }
    }
}

