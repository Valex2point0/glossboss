package it.valentinamanduci.glossboss.model;

/**
 * Product è la classe BASE (astratta) per tutti i prodotti make-up.
 * Questa classe contiene i campi comuni a tutti i prodotti:
 * - nome
 * - brand
 * - data di scadenza
 */
public abstract class Product {

    // campi comuni a tutti i prodotti
    private final String name;
    private final String brand;

    // tengo la data come String (es: "2026-02-10")
    private final String expiryDate;

    /**
     * Costruttore base usato dalle sottoclassi.
     */
    public Product(String name, String brand, String expiryDate) {
        this.name = name;
        this.brand = brand;
        this.expiryDate = expiryDate;
    }

    // Getter: servono per leggere i dati fuori dalla classe (es. nella UI)
    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * Metodo astratto: ogni sottoclasse deve dire che tipo è.
     * Es: "Lipstick", "Mascara", "Foundation"
     */
    public abstract String getType();

    /**
     * toString() per stampare il prodotto in modo ordinato in console.
     */
    @Override
    public String toString() {
        return getType()
                + " {name='" + name
                + "', brand='" + brand
                + "', expiry='" + expiryDate + "'}";
    }
}
