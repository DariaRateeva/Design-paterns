package composite;

public class SimpleItem implements MenuComponent {
    private String name;
    private double price;

    public SimpleItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void display(int level) {
        String indent = "  ".repeat(level);
        System.out.printf("%s└─ %s - $%.2f%n", indent, name, price);
    }
}
