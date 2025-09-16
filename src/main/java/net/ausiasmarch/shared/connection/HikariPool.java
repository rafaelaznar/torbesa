package net.ausiasmarch.shared.connection;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.ausiasmarch.shared.service.DatabaseService;

public class HikariPool {

    private Connection oConnection;
    private HikariDataSource oConnectionPool;

    
    public Connection getConnection() throws SQLException {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DatabaseService.DB_URL);
        config.setUsername(DatabaseService.DB_USER);
        config.setPassword(DatabaseService.DB_PASS);
        config.setMaximumPoolSize(DatabaseService.DB_MAX_POOL_SIZE);
        config.setMinimumIdle(DatabaseService.DB_MIN_POOL_SIZE);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setLeakDetectionThreshold(15000);
        config.setConnectionTestQuery("SELECT 1");
        config.setConnectionTimeout(2000);

        oConnectionPool = new HikariDataSource(config);
        oConnection = oConnectionPool.getConnection();
        return oConnection;

    }
    
    public void disposeConnection() throws SQLException {
        if (oConnection != null) {
            oConnection.close();
        }
        if (oConnectionPool != null) {
            oConnectionPool.close();
        }
    }

}
