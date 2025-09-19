package net.ausiasmarch.shared.connection;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import net.ausiasmarch.shared.service.DatabaseService;

public class HikariPool {
    private static HikariDataSource globalPool = null;

    public static void initGlobalPool() {
        if (globalPool == null) {
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
            globalPool = new HikariDataSource(config);
        }
    }

    public static void closeGlobalPool() {
        if (globalPool != null) {
            globalPool.close();
            globalPool = null;
        }
    }

    public static Connection getConnection() throws SQLException {
        if (globalPool == null) {
            initGlobalPool();
        }
        return globalPool.getConnection();
    }

    public static disposeConnection(){
        
    }
}
