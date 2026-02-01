package it.valentinamanduci.glossboss.service;

import it.valentinamanduci.glossboss.iterator.GlossBossIterator;
import it.valentinamanduci.glossboss.iterator.ProductCollection;
import it.valentinamanduci.glossboss.iterator.ProductIterator;
import it.valentinamanduci.glossboss.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * CollectionService rappresenta la "logica applicativa"
 * che gestisce la collezione di prodotti in memoria.
 * Questo service si occupa solo di:
 * - aggiungere prodotti
 * - restituire la lista
 * - sostituire tutta la collezione
 * - fornire un iteratore (Iterator Pattern)
 */
public class CollectionService implements ProductCollection {

    // Logger per tracciare le operazioni importanti
    private static final Logger logger =
            Logger.getLogger(CollectionService.class.getName());

    // Lista interna che contiene tutti i prodotti
    private final List<Product> products = new ArrayList<>();

    /**
     * Aggiunge un prodotto alla collezione.
     */
    public void addProduct(Product product) {
        products.add(product);
        logger.info("Added product: " + product);
    }

    /**
     * Restituisce TUTTI i prodotti.
     * Ritorno una lista non modificabile per evitare
     * che venga alterata direttamente dall'esterno.
     */
    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    /**
     * Controlla se la collezione è vuota.
     */
    public boolean isEmpty() {
        return products.isEmpty();
    }

    /**
     * Sostituisce completamente la collezione in memoria.
     * Questo metodo viene usato dopo il caricamento da file.
     */
    public void replaceAll(List<Product> newProducts) {
        products.clear();
        products.addAll(newProducts);

        logger.info("Replaced collection with "
                + newProducts.size() + " products");
    }

    /**
     * Metodo richiesto dal ProductCollection (Iterator Pattern).
     * Crea e restituisce un iteratore sulla lista interna.
     */
    @Override
    public ProductIterator createIterator() {
        return new GlossBossIterator(products);
    }
}
