package it.valentinamanduci.glossboss.model;

public class Mascara extends Product {

    public Mascara(String name, String brand, String expiryDate) {
        super(name, brand, expiryDate);
    }

    @Override
    public String getType() {
        return "Mascara";
    }
}
