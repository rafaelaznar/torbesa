package net.ausiasmarch.juegoSalinas.model;

import java.io.Serializable;
import java.util.Random;

public class JuegoSalinasBean implements Serializable {
    private String userChoice;
    private String computerChoice;
    private String result;

    private static final String[] CHOICES = {"Piedra", "Papel", "Tijera"};

    public void play(String userChoice) {
        this.userChoice = userChoice;
        this.computerChoice = CHOICES[new Random().nextInt(3)];
        this.result = calculateResult();
    }

    private String calculateResult() {
        if (userChoice.equals(computerChoice)) return "Empate";
        if ((userChoice.equals("Piedra") && computerChoice.equals("Tijera")) ||
            (userChoice.equals("Papel") && computerChoice.equals("Piedra")) ||
            (userChoice.equals("Tijera") && computerChoice.equals("Papel"))) {
            return "¡Ganaste!";
        }
        return "Perdiste";
    }

    public String getUserChoice() { return userChoice; }
    public String getComputerChoice() { return computerChoice; }
    public String getResult() { return result; }

    // esto es para el test 
    public void setComputerChoice(String computerChoice) {
        this.computerChoice = computerChoice;
        this.result = calculateResult();
    }
}
