package client;

import factory.*;
import builder.*;
import models.*;
import singleton.*;
import decorator.*;
import facade.OrderFacade;
import adapter.*;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SystemConfig config = SystemConfig.getInstance();
    private static OrderManager orderManager = OrderManager.getInstance();
    private static OrderFacade orderFacade = new OrderFacade();

    private static List<Food> cart = new ArrayList<>();
    private static String customerName = "";
    private static String deliveryAddress = "";

    public static void main(String[] args) {
        displayWelcome();

        boolean running = true;
        while (running) {
            int choice = displayMainMenu();

            switch (choice) {
                case 1:
                    browseAndOrderFood();
                    break;
                case 2:
                    addEnhancements();
                    break;
                case 3:
                    reviewCart();
                    break;
                case 4:
                    publishToDeliveryPlatforms();
                    break;
                case 5:
                    completeOrderWithPayment();
                    break;
                case 6:
                    viewOrderHistory();
                    break;
                case 7:
                    running = false;
                    displayGoodbye();
                    break;
                default:
                    System.out.println("\nInvalid choice! Please select 1-7.");
            }
        }

        scanner.close();
    }

    private static void displayWelcome() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Welcome to " + config.getRestaurantName() + "!");
        System.out.println("Your Digital Food Ordering Experience");
        System.out.println("Status: " + config.getRestaurantStatus());
        System.out.println("=".repeat(70));
    }

    private static int displayMainMenu() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(70));
        System.out.println("1. Browse & Order Food");
        System.out.println("2. Add Order Enhancements");
        System.out.println("3. Review Cart");
        System.out.println("4. Publish to Delivery Platforms");
        System.out.println("5. Complete Order & Payment");
        System.out.println("6. View Order History");
        System.out.println("7. Exit");
        System.out.println("=".repeat(70));

        System.out.print("\nEnter your choice: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // ============= OPTION 1: BROWSE & ORDER =============

    private static void browseAndOrderFood() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("MENU - SELECT YOUR FOOD");
        System.out.println("=".repeat(70));

        FoodFactoryProvider.displayMenu();

        System.out.print("\nSelect item (1-Pizza, 2-Burger, 3-Salad, 0-Cancel): ");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }

        if (choice == 0) return;

        String foodType = getFoodType(choice);
        if (foodType == null) {
            System.out.println("Invalid selection!");
            return;
        }

        FoodFactory factory = FoodFactoryProvider.getFactory(foodType);
        Food food = factory.createFood();

        System.out.print("\nAdd custom ingredients? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            food = addCustomIngredients(food, foodType);
        }

        cart.add(food);
        System.out.println("\n" + food.getName() + " added to cart!");
        System.out.println("Current cart total: $" + String.format("%.2f", calculateCartTotal()));

        System.out.print("\nAdd another item? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            browseAndOrderFood();
        }
    }

    private static String getFoodType(int choice) {
        switch (choice) {
            case 1: return "pizza";
            case 2: return "burger";
            case 3: return "salad";
            default: return null;
        }
    }

    private static Food addCustomIngredients(Food food, String foodType) {
        System.out.println("\n" + "-".repeat(70));
        System.out.println("CUSTOMIZE YOUR " + foodType.toUpperCase());

        factory.IngredientCatalog.displayIngredients(foodType);

        List<Ingredient> availableIngredients = factory.IngredientCatalog.getIngredientsForType(foodType);

        System.out.print("\nEnter ingredient numbers (comma-separated) or press Enter to skip: ");
        String input = scanner.nextLine().trim();

        if (!input.isEmpty()) {
            builder.FoodBuilder builder = new builder.FoodBuilder(food);
            String[] selections = input.split(",");

            for (String sel : selections) {
                try {
                    int index = Integer.parseInt(sel.trim()) - 1;
                    if (index >= 0 && index < availableIngredients.size()) {
                        builder.addIngredient(availableIngredients.get(index));
                        System.out.println("   Added: " + availableIngredients.get(index).getName());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("   Invalid: " + sel);
                }
            }

            food = builder.build();
        }

        return food;
    }

    // ============= OPTION 2: ADD ENHANCEMENTS (DECORATOR PATTERN) =============

    private static void addEnhancements() {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty! Please add items first.");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("ADD ORDER ENHANCEMENTS");
        System.out.println("=".repeat(70));

        System.out.println("\nYour Current Order:");
        displayCartItems();
        double currentTotal = calculateCartTotal();
        System.out.println("\nCurrent Total: $" + String.format("%.2f", currentTotal));

        System.out.println("\nAvailable Enhancements:");
        System.out.println("-".repeat(70));
        System.out.println("1. Express Delivery (Priority + Faster)");
        System.out.println("2. Apply Discount Coupon");
        System.out.println("3. Earn Loyalty Points");
        System.out.println("4. Add Special Occasion Message");
        System.out.println("5. Apply All Available Enhancements");
        System.out.println("0. Back to Main Menu");

        System.out.print("\nSelect enhancement: ");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }

        switch (choice) {
            case 1:
                applyExpressDeliveryToOrder();
                break;
            case 2:
                applyDiscountToOrder();
                break;
            case 3:
                applyLoyaltyPointsToOrder();
                break;
            case 4:
                applySpecialMessageToOrder();
                break;
            case 5:
                applyAllEnhancements();
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice!");
        }

        double newTotal = calculateCartTotal();
        if (newTotal != currentTotal) {
            System.out.println("\n" + "-".repeat(70));
            System.out.println("Previous Total: $" + String.format("%.2f", currentTotal));
            System.out.println("New Total: $" + String.format("%.2f", newTotal));
            if (newTotal < currentTotal) {
                System.out.println("You Saved: $" + String.format("%.2f", currentTotal - newTotal));
            } else {
                System.out.println("Additional Cost: $" + String.format("%.2f", newTotal - currentTotal));
            }
            System.out.println("-".repeat(70));
        }
    }

    private static void applyExpressDeliveryToOrder() {
        System.out.println("\nEXPRESS DELIVERY");
        System.out.println("-".repeat(70));
        System.out.println("Priority processing");
        System.out.println("Faster delivery time");
        System.out.println("Cost: +$5.00 per item");
        System.out.print("\nConfirm? (y/n): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            for (int i = 0; i < cart.size(); i++) {
                cart.set(i, new ExpressDeliveryDecorator(cart.get(i)));
            }
            System.out.println("Express Delivery added to entire order!");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private static void applyDiscountToOrder() {
        System.out.println("\nAPPLY DISCOUNT COUPON");
        System.out.println("-".repeat(70));
        System.out.println("Available codes:");
        System.out.println("  SAVE10  -> 10% OFF");
        System.out.println("  SAVE20  -> 20% OFF");
        System.out.println("  SAVE30  -> 30% OFF");
        System.out.println("  HALFOFF -> 50% OFF");
        System.out.print("\nEnter coupon code (or custom % 10-50): ");

        String input = scanner.nextLine().trim().toUpperCase();
        int discountPercent = 0;

        switch (input) {
            case "SAVE10":
                discountPercent = 10;
                break;
            case "SAVE20":
                discountPercent = 20;
                break;
            case "SAVE30":
                discountPercent = 30;
                break;
            case "HALFOFF":
                discountPercent = 50;
                break;
            default:
                try {
                    discountPercent = Integer.parseInt(input);
                    if (discountPercent < 10 || discountPercent > 50) {
                        System.out.println("Discount must be 10-50%!");
                        return;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid coupon code!");
                    return;
                }
        }

        double originalTotal = calculateCartTotal();

        for (int i = 0; i < cart.size(); i++) {
            cart.set(i, new DiscountCouponDecorator(cart.get(i), discountPercent));
        }

        double newTotal = calculateCartTotal();

        System.out.println("\n" + discountPercent + "% Discount applied to entire order!");
        System.out.println("Original: $" + String.format("%.2f", originalTotal));
        System.out.println("After Discount: $" + String.format("%.2f", newTotal));
        System.out.println("Saved: $" + String.format("%.2f", originalTotal - newTotal));
    }

    private static void applyLoyaltyPointsToOrder() {
        System.out.println("\nLOYALTY POINTS PROGRAM");
        System.out.println("-".repeat(70));
        System.out.println("Earn 10 points per $1 spent");
        System.out.println("Redeem points for future discounts");
        System.out.print("\nActivate loyalty points? (y/n): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            int totalPoints = 0;
            for (int i = 0; i < cart.size(); i++) {
                Food item = cart.get(i);
                Food decorated = new LoyaltyPointsDecorator(item);
                cart.set(i, decorated);
                totalPoints += (int)(item.getPrice() * 10);
            }
            System.out.println("Loyalty points activated!");
            System.out.println("You will earn approximately " + totalPoints + " points with this order!");
        } else {
            System.out.println("Cancelled.");
        }
    }

    private static void applySpecialMessageToOrder() {
        System.out.println("\nSPECIAL OCCASION MESSAGE");
        System.out.println("-".repeat(70));
        System.out.println("Add a personalized message card to your order");
        System.out.println("Perfect for: Birthdays, Anniversaries, Thank You, etc.");
        System.out.println("Cost: +$1.50");

        System.out.print("\nEnter your message: ");
        String message = scanner.nextLine().trim();

        if (message.isEmpty()) {
            System.out.println("Message cannot be empty!");
            return;
        }

        cart.set(0, new SpecialOccasionDecorator(cart.get(0), message));

        System.out.println("Special message added to order!");
        System.out.println("Message: \"" + message + "\"");
    }

    private static void applyAllEnhancements() {
        System.out.println("\nAPPLY ALL ENHANCEMENTS");
        System.out.println("-".repeat(70));
        System.out.println("This will add:");
        System.out.println("  Express Delivery");
        System.out.println("  20% Discount (standard combo)");
        System.out.println("  Loyalty Points");
        System.out.println("  Special Message (optional)");

        System.out.print("\nApply all? (y/n): ");
        if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Cancelled.");
            return;
        }

        double originalTotal = calculateCartTotal();

        for (int i = 0; i < cart.size(); i++) {
            Food item = cart.get(i);
            item = new DiscountCouponDecorator(item, 20);
            item = new LoyaltyPointsDecorator(item);
            item = new ExpressDeliveryDecorator(item);
            cart.set(i, item);
        }

        System.out.print("\nAdd special message? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.print("Enter message: ");
            String message = scanner.nextLine().trim();
            if (!message.isEmpty()) {
                cart.set(0, new SpecialOccasionDecorator(cart.get(0), message));
            }
        }

        double newTotal = calculateCartTotal();
        int totalPoints = (int)(originalTotal * 10 * 0.8);

        System.out.println("\n" + "=".repeat(70));
        System.out.println("ALL ENHANCEMENTS APPLIED!");
        System.out.println("=".repeat(70));
        System.out.println("Original Total: $" + String.format("%.2f", originalTotal));
        System.out.println("Final Total: $" + String.format("%.2f", newTotal));
        System.out.println("Points to Earn: ~" + totalPoints + " points");
        System.out.println("=".repeat(70));
    }

    // ============= OPTION 3: REVIEW CART =============

    private static void reviewCart() {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty!");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("YOUR SHOPPING CART");
        System.out.println("=".repeat(70));

        for (int i = 0; i < cart.size(); i++) {
            Food item = cart.get(i);
            System.out.println("\n" + (i + 1) + ". " + item.getName());
            System.out.println("   Price: $" + String.format("%.2f", item.getPrice()));
        }

        double total = calculateCartTotal();
        System.out.println("\n" + "-".repeat(70));
        System.out.println("SUBTOTAL: $" + String.format("%.2f", total));
        System.out.println("Delivery Fee: $" + String.format("%.2f", config.getDeliveryFee()));
        System.out.println("=".repeat(70));
        System.out.println("TOTAL: $" + String.format("%.2f", total + config.getDeliveryFee()));
        System.out.println("=".repeat(70));

        System.out.print("\nRemove an item? Enter number (or 0 to keep all): ");
        try {
            int remove = Integer.parseInt(scanner.nextLine()) - 1;
            if (remove >= 0 && remove < cart.size()) {
                Food removed = cart.remove(remove);
                System.out.println("Removed: " + removed.getName());
            }
        } catch (NumberFormatException e) {
            // Keep all
        }
    }

    // ============= OPTION 4: PUBLISH TO DELIVERY PLATFORMS (ADAPTER PATTERN) =============

    private static void publishToDeliveryPlatforms() {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty! Add items first.");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("PUBLISH TO DELIVERY PLATFORMS");
        System.out.println("=".repeat(70));

        System.out.println("\nADAPTER PATTERN - Integrating Different Platform APIs");
        System.out.println("-".repeat(70));
        System.out.println("Your restaurant lists menu items on multiple platforms.");
        System.out.println("Each platform has DIFFERENT, INCOMPATIBLE APIs:");
        System.out.println();
        System.out.println("1. UberEats");
        System.out.println("   API: addMenuItem(String jsonData)");
        System.out.println("   Format: JSON with specific fields");
        System.out.println("   Price: Must be in cents");
        System.out.println();
        System.out.println("2. DoorDash");
        System.out.println("   API: publishItem(String xmlData)");
        System.out.println("   Format: XML structure");
        System.out.println("   Requires: Store code");
        System.out.println();
        System.out.println("3. Glovo");
        System.out.println("   API: syncMenuItem(Map data, String apiKey)");
        System.out.println("   Format: HashMap with custom keys");
        System.out.println("   Requires: API authentication");
        System.out.println();
        System.out.println("4. Publish to ALL Platforms");
        System.out.println("0. Cancel");

        System.out.print("\nSelect platform: ");
        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }

        DeliveryPlatform[] platforms;

        switch (choice) {
            case 1:
                platforms = new DeliveryPlatform[]{new UberEatsAdapter()};
                break;
            case 2:
                platforms = new DeliveryPlatform[]{new DoorDashAdapter()};
                break;
            case 3:
                platforms = new DeliveryPlatform[]{new GlovoAdapter()};
                break;
            case 4:
                platforms = new DeliveryPlatform[]{
                        new UberEatsAdapter(),
                        new DoorDashAdapter(),
                        new GlovoAdapter()
                };
                break;
            case 0:
                System.out.println("Cancelled.");
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("PUBLISHING MENU ITEMS");
        System.out.println("=".repeat(70));

        for (Food food : cart) {
            System.out.println("\nPublishing: " + food.getName() + " ($" +
                    String.format("%.2f", food.getPrice()) + ")");
            System.out.println("-".repeat(70));

            for (DeliveryPlatform platform : platforms) {
                System.out.println(platform.getPlatformName() + " Adapter:");
                boolean success = platform.publishMenuItem(food);

                if (success) {
                    System.out.println("   Published to " + platform.getPlatformName());
                } else {
                    System.out.println("   Failed to publish to " + platform.getPlatformName());
                }
                System.out.println();
            }
        }

        System.out.println("=".repeat(70));
        System.out.println("PUBLISHING COMPLETE!");
        System.out.println("=".repeat(70));
        System.out.println("The Adapter Pattern allowed us to:");
        System.out.println("   - Use ONE unified interface: DeliveryPlatform");
        System.out.println("   - Support THREE different APIs (JSON, XML, Map)");
        System.out.println("   - Add new platforms WITHOUT changing Food class");
        System.out.println("=".repeat(70));
    }

    // ============= OPTION 5: COMPLETE ORDER WITH PAYMENT (FACADE PATTERN) =============

    private static void completeOrderWithPayment() {
        if (cart.isEmpty()) {
            System.out.println("\nYour cart is empty! Add items first.");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("ORDER COMPLETION & PAYMENT");
        System.out.println("=".repeat(70));

        if (customerName.isEmpty()) {
            System.out.print("\nEnter your name: ");
            customerName = scanner.nextLine().trim();
        }

        if (deliveryAddress.isEmpty()) {
            System.out.print("Enter delivery address: ");
            deliveryAddress = scanner.nextLine().trim();
        }

        System.out.println("\nSELECT PAYMENT METHOD:");
        System.out.println("-".repeat(70));
        System.out.println("1. Cash on Delivery");
        System.out.println("2. PayPal");
        System.out.println("3. Stripe");
        System.out.println("0. Cancel");

        System.out.print("\nEnter choice: ");
        int paymentChoice = 0;
        try {
            paymentChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }

        PaymentProcessor selectedPayment = null;
        switch (paymentChoice) {
            case 1:
                selectedPayment = new CashPayment();
                break;
            case 2:
                selectedPayment = new PayPalAdapter();
                break;
            case 3:
                selectedPayment = new StripeAdapter();
                break;
            case 0:
                System.out.println("Payment cancelled.");
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        System.out.println("Payment method: " + selectedPayment.getPaymentMethod());

        System.out.print("\nExpress delivery? (y/n): ");
        boolean isExpress = scanner.nextLine().equalsIgnoreCase("y");

        System.out.println("\n" + "=".repeat(70));
        System.out.println("FACADE PATTERN - Coordinating Subsystems");
        System.out.println("=".repeat(70));
        System.out.println("Orchestrating:");
        System.out.println("  1. InventoryService");
        System.out.println("  2. PaymentService");
        System.out.println("  3. DeliveryService");
        System.out.println("  4. NotificationService");
        System.out.println("=".repeat(70));

        System.out.println("\nProcessing through Facade...\n");

        boolean success = true;
        for (Food food : cart) {
            boolean orderPlaced = orderFacade.placeCompleteOrder(
                    customerName,
                    deliveryAddress,
                    food,
                    selectedPayment.getPaymentMethod(),
                    isExpress
            );

            if (!orderPlaced) {
                success = false;
                break;
            }
        }

        if (success) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("ORDER COMPLETED SUCCESSFULLY!");
            System.out.println("=".repeat(70));
            System.out.println("Confirmation: " + customerName);
            System.out.println("Delivery: " + (isExpress ? "30 min" : "60 min"));
            System.out.println("Address: " + deliveryAddress);
            System.out.println("\nFacade coordinated all subsystems in one call!");
            System.out.println("=".repeat(70));

            cart.clear();
            deliveryAddress = "";
        } else {
            System.out.println("\nOrder failed. Please try again.");
        }
    }

    // ============= OPTION 6: ORDER HISTORY =============

    private static void viewOrderHistory() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("ORDER HISTORY");
        System.out.println("=".repeat(70));

        orderManager.displayAllOrders();
    }

    // ============= UTILITY METHODS =============

    private static double calculateCartTotal() {
        return cart.stream().mapToDouble(Food::getPrice).sum();
    }

    private static void displayCartItems() {
        for (int i = 0; i < cart.size(); i++) {
            System.out.println((i + 1) + ". " + cart.get(i).getName() +
                    " - $" + String.format("%.2f", cart.get(i).getPrice()));
        }
    }

    private static void displayGoodbye() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Thank you for visiting " + config.getRestaurantName() + "!");
        System.out.println("We hope to serve you again soon!");
        System.out.println("Total orders today: " + orderManager.getTotalOrders());
        System.out.println("=".repeat(70));
    }
}
