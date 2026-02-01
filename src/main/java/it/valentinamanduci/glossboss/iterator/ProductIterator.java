package it.valentinamanduci.glossboss.iterator;

import it.valentinamanduci.glossboss.model.Product;

/**
 * Interfaccia dell'Iterator Pattern.
 * Serve per scorrere i prodotti senza esporre direttamente la lista interna.
 */
public interface ProductIterator {

    boolean hasNext();

    Product next();
}
