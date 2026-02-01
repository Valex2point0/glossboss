package it.valentinamanduci.glossboss.iterator;

/**
 * Interfaccia "collection" dell'Iterator Pattern.
 * per implementare fornire un iteratore.
 */
public interface ProductCollection {

    ProductIterator createIterator();
}
