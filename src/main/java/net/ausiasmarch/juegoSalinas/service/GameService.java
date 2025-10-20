package net.ausiasmarch.juegoSalinas.service;

import net.ausiasmarch.juegoSalinas.model.JuegoSalinasBean;

public class GameService {
    public JuegoSalinasBean play(String userChoice) {
        JuegoSalinasBean game = new JuegoSalinasBean();
        game.play(userChoice);
        return game;
    }
}
