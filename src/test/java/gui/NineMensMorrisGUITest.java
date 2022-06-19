package gui;

import controller.NineMensMorrisGame;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NineMensMorrisGUITest {
    private NineMensMorrisGame game;

    @Before
    public void setUp() {
        game = new NineMensMorrisGame(false);
    }
    @After
    public void tearDown() {
    }

    @Test
    public void testEmptyBoard(){
        new NineMensMorrisGUI(game);
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}