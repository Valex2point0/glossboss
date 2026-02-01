package it.valentinamanduci.glossboss.util;

import java.io.File;
import java.io.IOException;
import java.util.logging.*;

/**
 * Inizializza il logging su file.
 * Scopo: avere un file "logs/glossboss.log" con gli eventi importanti.
 */
public class LogConfig {

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        try {
            // Creo cartella logs/ se non esiste
            File dir = new File("logs");
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Logger rootLogger = Logger.getLogger("");
            rootLogger.setLevel(Level.INFO);

            // Tolgo i log su console per non sporcare la UI testuale
            for (Handler h : rootLogger.getHandlers()) {
                rootLogger.removeHandler(h);
            }

            // Log su file in append (true)
            FileHandler fileHandler = new FileHandler("logs/glossboss.log", true);
            fileHandler.setLevel(Level.INFO);
            fileHandler.setFormatter(new SimpleFormatter());

            rootLogger.addHandler(fileHandler);

            rootLogger.info("Logger inizializzato ✅");

        } catch (IOException ex) {
            // Se logging fallisce NON blocco l'app
            System.out.println("⚠️ Logging non disponibile (non riesco a creare logs/glossboss.log)");
        }
    }
}
