package it.valentinamanduci.glossboss;

import it.valentinamanduci.glossboss.ui.ConsoleMenu;
import it.valentinamanduci.glossboss.util.LogConfig;

public class App {

    public static void main(String[] args) {

        // Inizializzo il logger una volta sola all'avvio
        LogConfig.init();

        // Avvio la UI da console
        ConsoleMenu menu = new ConsoleMenu();
        menu.start();
    }
}
