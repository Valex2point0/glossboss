package it.valentinamanduci.glossboss.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite: rappresenta un "gruppo" (tipo cartella) che contiene altri componenti.
 * Per noi: un gruppo può essere un mese (es. 2026-02).
 */
public class ExpiryGroup implements ExpiryComponent {

    private final String name;
    private final List<ExpiryComponent> children = new ArrayList<>();

    public ExpiryGroup(String name) {
        this.name = name;
    }

    public void add(ExpiryComponent component) {
        children.add(component);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void print(String indent) {
        System.out.println(indent + "📁 " + name);
        for (ExpiryComponent c : children) {
            c.print(indent + "  ");
        }
    }
}
