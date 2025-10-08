package net.ausiasmarch.shared.connection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import net.ausiasmarch.harrypotter.service.HarryPotterCharacterService;  // 👈 importa tu servicio

@WebListener
public class AppContextListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cant load MySQL driver", e);
        }
        // Inicializar pool global
        HikariPool.initGlobalPool();

        // 👇 Inicializar datos del juego de Harry Potter
        try {
            HarryPotterCharacterService.initialize(sce.getServletContext());
            System.out.println("✅ Harry Potter data loaded at startup");
        } catch (Exception e) {
            System.err.println("❌ Error loading Harry Potter data: " + e.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Cerrar pool global
        HikariPool.closeGlobalPool();
    }
}
