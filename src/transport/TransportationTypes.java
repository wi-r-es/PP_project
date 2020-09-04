/*
 * Nome: Jose Paulo Nogueira Machado
 * Número: 8180192
 */
package transport;


public enum TransportationTypes {
    NORMAL(Color.green),
    DANGEROUS(Color.red),
    FRAGILE(Color.yellow),
    PRIORITY(Color.blue);

    private final Color color;

    private TransportationTypes(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}

