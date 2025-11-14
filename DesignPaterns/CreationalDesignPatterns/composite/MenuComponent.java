package composite;

public interface MenuComponent {
    String getName();
    double getPrice();
    void display(int level);

    // Optional - only composites implement these
    default void add(MenuComponent component) {
        throw new UnsupportedOperationException("Cannot add to a leaf node");
    }

    default void remove(MenuComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf node");
    }
}
