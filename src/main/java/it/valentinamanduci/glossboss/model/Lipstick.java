package it.valentinamanduci.glossboss.model;

/**
 * Sottoclasse concreta di Product.
 * È un "tipo" specifico di prodotto.
 */
public class Lipstick extends Product {

    public Lipstick(String name, String brand, String expiryDate) {
        super(name, brand, expiryDate);
    }

    @Override
    public String getType() {
        return "Lipstick";
    }
}
