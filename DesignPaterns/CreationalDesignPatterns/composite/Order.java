package composite;

import java.util.ArrayList;
import java.util.List;

public class Order implements MenuComponent {
    private int orderId;
    private String customerName;
    private List<MenuComponent> items = new ArrayList<>();

    public Order(int orderId, String customerName) {
        this.orderId = orderId;
        this.customerName = customerName;
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
        return "Order #" + orderId;
    }

    @Override
    public double getPrice() {
        return items.stream()
                .mapToDouble(MenuComponent::getPrice)
                .sum();
    }

    @Override
    public void display(int level) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("ORDER #" + orderId + " - " + customerName);
        System.out.println("=".repeat(60));

        if (items.isEmpty()) {
            System.out.println("  (Cart is empty)");
        } else {
            for (MenuComponent item : items) {
                item.display(1);
            }
        }

        System.out.println("\n" + "-".repeat(60));
        System.out.printf("TOTAL: $%.2f%n", getPrice());
        System.out.println("=".repeat(60));
    }

    public int getOrderId() {
        return orderId;
    }

    public List<MenuComponent> getItems() {
        return items;
    }
}
