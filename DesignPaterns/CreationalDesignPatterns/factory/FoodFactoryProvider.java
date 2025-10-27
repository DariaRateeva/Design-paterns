package factory;

// This is a helper class to select which concrete factory to use
public class FoodFactoryProvider {

    public static FoodFactory getFactory(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Food type cannot be null or empty");
        }

        switch (type.toLowerCase()) {
            case "pizza":
                return new PizzaFactory();
            case "burger":
                return new BurgerFactory();
            case "salad":
                return new SaladFactory();
            default:
                throw new IllegalArgumentException("Unknown food type: " + type);
        }
    }

    public static void displayMenu() {
        System.out.println("\n===== MENU =====");
        System.out.println("1. Pizza - $8.99");
        System.out.println("2. Burger - $6.50");
        System.out.println("3. Salad - $5.75");
        System.out.println("================\n");
    }
}
