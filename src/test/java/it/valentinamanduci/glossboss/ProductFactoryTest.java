package it.valentinamanduci.glossboss;

import it.valentinamanduci.glossboss.factory.ProductFactory;
import it.valentinamanduci.glossboss.model.Foundation;
import it.valentinamanduci.glossboss.model.Lipstick;
import it.valentinamanduci.glossboss.model.Mascara;
import it.valentinamanduci.glossboss.model.Product;

import junit.framework.TestCase;

/**
 * Test semplici: controllo che la Factory crei il tipo corretto.
 * (JUnit 3 perché Maven quickstart spesso parte così di default)
 */
public class ProductFactoryTest extends TestCase {

    public void testCreateLipstick() {
        ProductFactory factory = new ProductFactory();

        Product p = factory.createProduct("lipstick", "Ruby Woo", "MAC", "2026-02-10");

        assertTrue(p instanceof Lipstick);
        assertEquals("Ruby Woo", p.getName());
        assertEquals("MAC", p.getBrand());
        assertEquals("2026-02-10", p.getExpiryDate());
    }

    public void testCreateMascara() {
        ProductFactory factory = new ProductFactory();

        Product p = factory.createProduct("mascara", "Lash Paradise", "L'Oreal", "2026-03-01");

        assertTrue(p instanceof Mascara);
        assertEquals("Lash Paradise", p.getName());
        assertEquals("L'Oreal", p.getBrand());
        assertEquals("2026-03-01", p.getExpiryDate());
    }

    public void testCreateFoundation() {
        ProductFactory factory = new ProductFactory();

        Product p = factory.createProduct("foundation", "Born This Way", "Too Faced", "2026-01-15");

        assertTrue(p instanceof Foundation);
        assertEquals("Born This Way", p.getName());
        assertEquals("Too Faced", p.getBrand());
        assertEquals("2026-01-15", p.getExpiryDate());
    }

    public void testInvalidTypeThrowsException() {
        ProductFactory factory = new ProductFactory();

        try {
            factory.createProduct("eyeliner", "Epic Ink", "NYX", "2026-01-01");
            fail("Doveva lanciare IllegalArgumentException per tipo non valido");
        } catch (IllegalArgumentException ex) {
            assertTrue(true);
        }
    }
}
