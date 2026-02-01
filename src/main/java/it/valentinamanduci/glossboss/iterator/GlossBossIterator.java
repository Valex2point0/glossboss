package it.valentinamanduci.glossboss.iterator;

import it.valentinamanduci.glossboss.model.Product;

import java.util.List;

/**
 * Implementazione concreta dell'iteratore.
 * Tiene un indice interno (position) e restituisce i prodotti uno alla volta.
 */
public class GlossBossIterator implements ProductIterator {

    private final List<Product> products;
    private int position = 0;

    public GlossBossIterator(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean hasNext() {
        return position < products.size();
    }

    @Override
    public Product next() {
        return products.get(position++);
    }
}
