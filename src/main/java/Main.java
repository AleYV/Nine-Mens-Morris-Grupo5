import controller.AutoNineMensMorrisGame;
import gui.NineMensMorrisGUI;

import javax.swing.JOptionPane;


public class Main {
    public static void main(String[] args) {

        String[] options = {"Humano", "Maquina"};

        int option = JOptionPane.showOptionDialog(null, "¿Contra quien desea jugar?", "Game",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        System.out.println("OPTION: " + option);

        if(option == -1){
            System.out.println("Se cerró el programa");
            return;
        }

        if(option == 0)
            new NineMensMorrisGUI();
        else
            new NineMensMorrisGUI(new AutoNineMensMorrisGame());
    }
}
