package facade;

import models.Food;

// Subsystem Class - manages inventory
public class InventoryService {

    public boolean checkAvailability(Food food) {
        System.out.println("\n[Inventory Service] Checking availability...");
        System.out.println("  Item: " + food.getName());

        // Simulate inventory check (always available for demo)
        System.out.println("Item available in stock");
        return true;
    }

    public void reserveItem(Food food) {
        System.out.println("[Inventory Service] Reserved: " + food.getName());
    }

    public void releaseItem(Food food) {
        System.out.println("[Inventory Service] Released reservation for: " + food.getName());
    }
}
