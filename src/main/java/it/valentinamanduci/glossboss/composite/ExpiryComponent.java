package it.valentinamanduci.glossboss.composite;

/**
 * Interfaccia del Composite Pattern.
 * Un componente può essere:
 * - un gruppo (cartella) -> Composite
 * - un prodotto singolo -> Leaf
 */
public interface ExpiryComponent {

    String getName();

    // stampa ricorsiva con indentazione
    void print(String indent);
}
