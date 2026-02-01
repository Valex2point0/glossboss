package it.valentinamanduci.glossboss.io;

import it.valentinamanduci.glossboss.factory.ProductFactory;
import it.valentinamanduci.glossboss.model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Classe che gestisce l'I/O su file.
 *
 * Responsabilità:
 * - salvare la collezione su file CSV
 * - caricare la collezione dal file CSV
 *
 * Formato di ogni riga:
 * type|name|brand|expiryDate
 *
 * Esempio:
 * lipstick|Ruby Woo|MAC|2026-02-10
 */
public class StorageService {

    // Logger per tracciare le operazioni principali
    private static final Logger logger =
            Logger.getLogger(StorageService.class.getName());

    // Factory usata per ricreare i prodotti durante il load
    private final ProductFactory productFactory =
            new ProductFactory();

    /**
     * Salva la lista di prodotti in un file CSV.
     */
    public void saveToFile(List<Product> products, String filePath)
            throws IOException {

        File file = new File(filePath);

        // Creo la cartella (es. data/) se non esiste
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }

        // try-with-resources: chiude automaticamente il writer
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(file))) {

            for (Product p : products) {

                // Scrivo SEMPRE 4 campi
                String line = p.getType().toLowerCase()
                        + "|" + p.getName()
                        + "|" + p.getBrand()
                        + "|" + p.getExpiryDate();

                writer.write(line);
                writer.newLine();
            }
        }

        logger.info("Saved " + products.size()
                + " products to " + filePath);
    }

    /**
     * Carica i prodotti da un file CSV.
     * Se una riga non ha esattamente 4 campi, viene ignorata.
     */
    public List<Product> loadFromFile(String filePath)
            throws IOException {

        File file = new File(filePath);

        // Se il file non esiste, ritorno lista vuota
        if (!file.exists()) {
            logger.info("File not found: " + filePath);
            return new ArrayList<>();
        }

        List<Product> loaded = new ArrayList<>();

        // try-with-resources: chiude automaticamente il reader
        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\\|");

                // Accetto SOLO il formato corretto
                if (parts.length != 4) {
                    logger.warning("Skipped invalid line: " + line);
                    continue;
                }

                String type = parts[0];
                String name = parts[1];
                String brand = parts[2];
                String expiryDate = parts[3];

                // Ricreo il prodotto usando la factory
                Product product =
                        productFactory.createProduct(
                                type, name, brand, expiryDate);

                loaded.add(product);
            }
        }

        logger.info("Loaded " + loaded.size()
                + " products from " + filePath);

        return loaded;
    }
}
