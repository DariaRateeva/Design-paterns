package composite;

import java.util.ArrayList;
import java.util.List;

public class OrderItem implements MenuComponent {
    private String name;
    private List<MenuComponent> items = new ArrayList<>();

    public OrderItem(String name) {
        this.name = name;
    }

    @Override
    public void add(MenuComponent component) {
        items.add(component);
    }

    @Override
    public void remove(MenuComponent component) {
        items.remove(component);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return items.stream()
                .mapToDouble(MenuComponent::getPrice)
                .sum();
    }

    @Override
    public void display(int level) {
        String indent = "  ".repeat(level);
        System.out.printf("%s package %s - $%.2f%n", indent, name, getPrice());
        for (MenuComponent item : items) {
            item.display(level + 1);
        }
    }
}
