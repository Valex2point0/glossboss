package it.valentinamanduci.glossboss.factory;

import it.valentinamanduci.glossboss.model.Foundation;
import it.valentinamanduci.glossboss.model.Lipstick;
import it.valentinamanduci.glossboss.model.Mascara;
import it.valentinamanduci.glossboss.model.Product;

/**
 * Factory Pattern:
 * questa classe centralizza la creazione degli oggetti Product.
 * Così la UI non fa "new Lipstick(...)" ovunque.
 */
public class ProductFactory {

    public Product createProduct(String type, String name, String brand, String expiryDate) {

        switch (type.toLowerCase()) {
            case "lipstick":
                return new Lipstick(name, brand, expiryDate);
            case "mascara":
                return new Mascara(name, brand, expiryDate);
            case "foundation":
                return new Foundation(name, brand, expiryDate);
            default:
                // Se il tipo è sconosciuto, segnalo l'errore.
                // Poi in ConsoleMenu lo gestisco con messaggio pulito (Exception Shielding).
                throw new IllegalArgumentException("Tipo prodotto non supportato: " + type);
        }
    }
}
