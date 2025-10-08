package net.ausiasmarch.perro.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import net.ausiasmarch.perro.dao.PuntuacionDao;
import net.ausiasmarch.perro.model.PuntuacionDto;
import net.ausiasmarch.shared.connection.HikariPool;

public class PuntuacionService {
    public boolean set(int userId, boolean correct) throws SQLException {
        try (Connection oConnection = HikariPool.getConnection()) {
            PuntuacionDao puntuacionDao = new PuntuacionDao(oConnection);
            if (puntuacionDao.count(userId) > 1) {
                puntuacionDao.sanitize();
            }
            PuntuacionDto userScore = puntuacionDao.get(userId);
            if (!Objects.isNull(userScore)) {
                userScore.setTries(userScore.getTries() + 1);
                if (correct) {
                    userScore.setScore(userScore.getScore() + 1);
                }
                return puntuacionDao.update(userScore) > 0;
            } else {
                userScore = new PuntuacionDto();
                userScore.setUserId(userId);
                userScore.setTries(1);
                userScore.setScore(correct ? 1 : 0);
                userScore.setTimestamp(LocalDateTime.now());
                return puntuacionDao.insert(userScore) > 0;
            }
        }
    }

    public List<PuntuacionDto> getHighScores() throws SQLException {        
        try (Connection oConnection = HikariPool.getConnection()) {
            PuntuacionDao puntuacionDao = new PuntuacionDao(oConnection);
            
            // Limpieza automática si hay demasiados registros totales
            int totalRecords = puntuacionDao.count();
            if (totalRecords > 50) { // Si hay más de 50 registros, probablemente hay duplicados
                puntuacionDao.sanitize();
            }
            
            return puntuacionDao.getTop10();
        }
    }
}
