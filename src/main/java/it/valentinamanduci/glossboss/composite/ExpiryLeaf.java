package it.valentinamanduci.glossboss.composite;

import it.valentinamanduci.glossboss.model.Product;

/**
 * Leaf: elemento finale, non contiene figli.
 * Per noi: un singolo Product con la sua scadenza.
 */
public class ExpiryLeaf implements ExpiryComponent {

    private final Product product;

    public ExpiryLeaf(Product product) {
        this.product = product;
    }

    @Override
    public String getName() {
        return product.getName();
    }

    @Override
    public void print(String indent) {
        System.out.println(indent
                + "💄 " + product.getType()
                + " - " + product.getName()
                + " (" + product.getBrand() + ")"
                + " | scade: " + product.getExpiryDate());
    }
}
