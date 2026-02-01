package it.valentinamanduci.glossboss.ui;

import it.valentinamanduci.glossboss.composite.ExpiryGroup;
import it.valentinamanduci.glossboss.composite.ExpiryLeaf;
import it.valentinamanduci.glossboss.factory.ProductFactory;
import it.valentinamanduci.glossboss.io.StorageService;
import it.valentinamanduci.glossboss.iterator.ProductIterator;
import it.valentinamanduci.glossboss.model.Product;
import it.valentinamanduci.glossboss.service.CollectionService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * ConsoleMenu è la UI (interfaccia utente) testuale.
 *Qui gestisco:
 * - stampa del menu
 * - lettura input con Scanner
 * - chiamate ai servizi (CollectionService, StorageService)
 *Per non far crashare l'app: se l'utente sbaglia input o succede un errore file,
 * mostro un messaggio semplice (Exception Shielding).
 */
public class ConsoleMenu {

    // Scanner per leggere input da tastiera
    private final Scanner scanner = new Scanner(System.in);

    // Service che gestisce i prodotti in memoria
    private final CollectionService collectionService = new CollectionService();

    // Factory che crea il tipo corretto di prodotto
    private final ProductFactory productFactory = new ProductFactory();

    // Service per salvare e caricare dal file (I/O)
    private final StorageService storageService = new StorageService();

    // Percorso del file dove salvo i prodotti
    private final String FILE_PATH = "data/products.csv";

    /**
     * Metodo principale della UI:
     * stampa menu in loop finché l'utente non sceglie 0.
     */
    public void start() {

        boolean running = true;

        while (running) {

            printMenu();

            // leggo come String per evitare crash se scrive testo
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAddProduct();
                    break;

                case "2":
                    handleShowAllProducts();
                    break;

                case "3":
                    handleSaveToFile();
                    break;

                case "4":
                    handleLoadFromFile();
                    break;

                case "5":
                    handleShowExpiriesComposite();
                    break;

                case "0":
                    System.out.println("Bye bye 💋");
                    running = false;
                    break;

                default:
                    System.out.println("Scelta non valida. Inserisci 1, 2, 3, 4, 5 o 0.");
            }

            System.out.println(); // riga vuota per leggibilità
        }

        // chiudo lo scanner alla fine
        scanner.close();
    }

    /**
     * Stampa del menu.
     */
    private void printMenu() {
        System.out.println("=================================");
        System.out.println("💄 GlossBoss - Makeup Organizer");
        System.out.println("=================================");
        System.out.println("1) Aggiungi prodotto");
        System.out.println("2) Mostra tutti i prodotti (Iterator)");
        System.out.println("3) Salva su file (I/O)");
        System.out.println("4) Carica da file (I/O)");
        System.out.println("5) Mostra scadenze (Composite)");
        System.out.println("0) Esci");
        System.out.print("Scelta: ");
    }

    /**
     * Aggiunge un prodotto chiedendo i dati all'utente.
     * Usa Factory Pattern per creare l'oggetto giusto.
     */
    private void handleAddProduct() {

        System.out.println("Tipo prodotto: lipstick / mascara / foundation");
        System.out.print("Tipo: ");
        String type = scanner.nextLine().trim();

        System.out.print("Nome prodotto: ");
        String name = scanner.nextLine().trim();

        System.out.print("Brand: ");
        String brand = scanner.nextLine().trim();

        System.out.print("Scadenza (YYYY-MM-DD) es. 2026-02-10: ");
        String expiryDate = scanner.nextLine().trim();

        // input sanitization minima
        if (type.isEmpty() || name.isEmpty() || brand.isEmpty() || expiryDate.isEmpty()) {
            System.out.println("❌ Campi vuoti non ammessi.");
            return;
        }

        try {
            // Factory Pattern: creo il prodotto corretto
            Product product = productFactory.createProduct(type, name, brand, expiryDate);

            // lo salvo nella collezione in memoria
            collectionService.addProduct(product);

            System.out.println("✅ Prodotto aggiunto: " + product);

        } catch (IllegalArgumentException ex) {
            // shield: niente stack trace
            System.out.println("❌ Tipo non valido. Usa lipstick / mascara / foundation");
        }
    }

    /**
     * Mostra tutti i prodotti.
     * Qui uso Iterator Pattern (createIterator()).
     */
    private void handleShowAllProducts() {

        if (collectionService.isEmpty()) {
            System.out.println("📦 Nessun prodotto in collezione.");
            return;
        }

        System.out.println("📦 La tua collezione:");

        ProductIterator iterator = collectionService.createIterator();

        while (iterator.hasNext()) {
            System.out.println("- " + iterator.next());
        }
    }

    /**
     * Salva su file CSV usando StorageService.
     */
    private void handleSaveToFile() {

        try {
            storageService.saveToFile(collectionService.getAllProducts(), FILE_PATH);
            System.out.println("💾 Salvato su: " + FILE_PATH);

        } catch (IOException ex) {
            // shield: messaggio semplice
            System.out.println("❌ Errore durante il salvataggio su file.");
        }
    }

    /**
     * Carica da file CSV usando StorageService.
     */
    private void handleLoadFromFile() {

        try {
            List<Product> loaded = storageService.loadFromFile(FILE_PATH);

            // sostituisco la collezione in memoria con quella caricata
            collectionService.replaceAll(loaded);

            System.out.println("📂 Caricati " + loaded.size() + " prodotti da: " + FILE_PATH);

        } catch (IOException ex) {
            System.out.println("❌ Errore durante il caricamento da file.");
        } catch (IllegalArgumentException ex) {
            // se nel file c'è un tipo prodotto non supportato dalla factory
            System.out.println("❌ Nel file c'è un tipo prodotto non valido.");
        }
    }

    /**
     * Composite Pattern:
     * creo una struttura gerarchica di scadenze raggruppate per mese (YYYY-MM).
     *
     * Root:
     *  - Scadenze prodotti
     * Gruppi:
     *  - 2026-02
     *  - 2026-03
     * Foglie:
     *  - singoli prodotti con scadenza
     */
    private void handleShowExpiriesComposite() {

        if (collectionService.isEmpty()) {
            System.out.println("📦 Nessun prodotto in collezione.");
            return;
        }

        // root del composite
        ExpiryGroup root = new ExpiryGroup("Scadenze prodotti");

        // mappa per creare/ritrovare facilmente i gruppi per mese
        Map<String, ExpiryGroup> groupsByMonth = new HashMap<>();

        for (Product p : collectionService.getAllProducts()) {

            // ricavo il mese (YYYY-MM) dalla data
            String monthKey = getMonthKeySafe(p.getExpiryDate());

            // se il gruppo non esiste, lo creo
            if (!groupsByMonth.containsKey(monthKey)) {
                groupsByMonth.put(monthKey, new ExpiryGroup(monthKey));
            }

            // aggiungo il prodotto come foglia
            groupsByMonth.get(monthKey).add(new ExpiryLeaf(p));
        }

        // aggiungo tutti i gruppi al root
        for (ExpiryGroup g : groupsByMonth.values()) {
            root.add(g);
        }

        // stampa ricorsiva del composite
        root.print("");
    }

    /**
     * Provo a trasformare una data (YYYY-MM-DD) in un mese (YYYY-MM).
     * Se la data è scritta male, ritorno "Senza data / non valida".
     */
    private String getMonthKeySafe(String expiryDate) {

        if (expiryDate == null || expiryDate.trim().isEmpty()) {
            return "Senza data / non valida";
        }

        try {
            LocalDate d = LocalDate.parse(expiryDate.trim());

            int year = d.getYear();
            int month = d.getMonthValue();

            // mese a due cifre: 02, 11, ...
            String mm = (month < 10) ? "0" + month : String.valueOf(month);

            return year + "-" + mm;

        } catch (DateTimeParseException ex) {
            return "Senza data / non valida";
        }
    }
}
