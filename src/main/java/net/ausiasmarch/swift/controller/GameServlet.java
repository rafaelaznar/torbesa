package net.ausiasmarch.swift.controller;

//PENDIENTE
//--------------------------------------------------
//Cambiar todos los nombres relacionados con capitales por los de canciones
//actualizar game.jsp a el de swift
//--------------------------------------------------

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.ausiasmarch.swift.dao.ScoreDao;
import net.ausiasmarch.swift.model.SongBean;
import net.ausiasmarch.swift.model.ScoreDto;
import net.ausiasmarch.swift.service.SongService;
import net.ausiasmarch.swift.service.ScoreService;
import net.ausiasmarch.shared.connection.HikariPool;
import net.ausiasmarch.shared.model.UserBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/swift/GameServlet")
public class GameServlet extends HttpServlet {

    // cambiar los nombres relacionados a capitales con los de songs
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        UserBean user = (UserBean) session.getAttribute("sessionUser");
        if (user == null) {
            try {
                response.sendRedirect("../shared/login.jsp");
            } catch (IOException e) {
                System.err.println("Error al redirigir a la página de inicio de sesión: " + e.getMessage());
            }
            return;
        } else {
            request.setAttribute("sessionUser", user);
        }

        SongService oSongService = new SongService(request.getServletContext());
        SongBean selectedSong = oSongService.getOneRandomSong(); 
        ArrayList<String> optionsListForAlbumTest = oSongService.getRandomAlbumsForTest(selectedSong, 3);        
        
        //se pide la canción y los albumes
        request.setAttribute("title", selectedSong.getCancion());        
        request.setAttribute("options", optionsListForAlbumTest);
        //esto hay que actualizarlo una vez se cree el game.jsp de swift
        RequestDispatcher dispatcher = request.getRequestDispatcher("/torbesa/src/main/webapp/swift/game.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println("Error al redirigir a la página del juego: " + e.getMessage());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            HttpSession session = request.getSession();
            UserBean user = (UserBean) session.getAttribute("sessionUser");
            if (user == null) {
                response.sendRedirect("../shared/login.jsp");
                return;
            } else {
                request.setAttribute("username", user.getUsername());
            }

            ScoreService scoreService = new ScoreService();
            String song = request.getParameter("song");
            String albumGuess = request.getParameter("albumGuess");
            SongService oSongService = new SongService(request.getServletContext());
            String correctAlbum = oSongService.fetchAllSongs().stream()
                    .filter(c -> c.getCancion().equals(song))
                    .map(SongBean::getAlbum)
                    .findFirst()
                    .orElse("");
            request.setAttribute("song", song);
            request.setAttribute("correctAlbum", correctAlbum);
            request.setAttribute("albumGuess", albumGuess);
            if (albumGuess.equals(correctAlbum)) {

                scoreService.set(user.getId(), true);

                request.setAttribute("message", "Correct! Well done.");
            } else {
                request.setAttribute("message", "Incorrect. Try again!");
                scoreService.set(user.getId(), false);
            }

            try (Connection oConnection = HikariPool.getConnection()) {

                ScoreDao oScoreDao = new ScoreDao(oConnection);
                ScoreDto userScore = oScoreDao.get(user.getId());
                request.setAttribute("userScore", userScore);

                List<ScoreDto> highScores = oScoreDao.getTop10();
                request.setAttribute("highScores", highScores);

                RequestDispatcher dispatcher = request.getRequestDispatcher("scores.jsp");
                dispatcher.forward(request, response);

            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            request.setAttribute("errorMessage", "Database error");
            RequestDispatcher dispatcher = request.getRequestDispatcher("../shared/error.jsp");
            dispatcher.forward(request, response);
        }

    }
}
