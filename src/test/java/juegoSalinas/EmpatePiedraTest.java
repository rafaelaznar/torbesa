package juegoSalinas;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import net.ausiasmarch.juegoSalinas.model.JuegoSalinasBean;

public class EmpatePiedraTest {
    @Test
public void testEmpatePiedra() {
    JuegoSalinasBean bean = new JuegoSalinasBean();
    bean.play("Piedra");
    bean.setComputerChoice("Piedra"); // en esta linea estoy forzando el empate
    assertEquals("Empate", bean.getResult());
}

}
