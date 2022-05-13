package controller;

import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.TestExecutionListener;

import static org.junit.jupiter.api.Assertions.*;

class NineMensMorrisGameTest implements TestExecutionListener {

    @Test
    void testgetCell(){
        NineMensMorrisGame game = new NineMensMorrisGame();

        System.out.println("Verificar que la celda este en el rango.");
        assertEquals(null, game.getCell(-3,-5));
    }
}