package it.valentinamanduci.glossboss.model;

public class Foundation extends Product {

    public Foundation(String name, String brand, String expiryDate) {
        super(name, brand, expiryDate);
    }

    @Override
    public String getType() {
        return "Foundation";
    }
}
