package controller;

import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.TestExecutionListener;
import controller.NineMensMorrisGame.Cells;
import static org.junit.jupiter.api.Assertions.*;

class NineMensMorrisGameTest implements TestExecutionListener {

    @Test
    void testgetCell(){
        NineMensMorrisGame game = new NineMensMorrisGame();
        System.out.println("VERIFICACIÓN MANUAL");
        System.out.println("Verificar que la celda este en el rango.");
        assertNull( game.getCell(-3,-5));

        System.out.println("Verificar que la celda este vacia antes de iniciar el juego.");
        assertEquals(Cells.EMPTY,game.getCell(1,1));

        System.out.println("Verificar que el primer turno sea de las fichas blancas\n");
        assertEquals('W', game.getTurn());

        System.out.println("Verificación automática del tablero");
        for(int i=0; i<game.getRows();i++){
            for(int j=0; j<game.getColumns();j++){
                if (i==3 && j==3) assertEquals(Cells.DISABLED,game.getCell(i,j));
                else if(Math.abs(i-3)==Math.abs(j-3)){
                    assertEquals(Cells.EMPTY,game.getCell(i,j));}
                else if(j==3) assertEquals(Cells.EMPTY,game.getCell(i,j));
                else if(i==3) assertEquals(Cells.EMPTY,game.getCell(i,j));
                else assertEquals(Cells.DISABLED,game.getCell(i,j));
            }
        }
    }
}