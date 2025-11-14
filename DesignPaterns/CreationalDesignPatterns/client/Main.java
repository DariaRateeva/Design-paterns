package client;

import factory.*;
import builder.*;
import models.*;
import singleton.*;
import decorator.*;
import adapter.*;
import composite.*;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SystemConfig config = SystemConfig.getInstance();
    private static Order currentOrder;
    private static String customerName = "";
    private static String deliveryAddress = "";
    private static DeliveryPlatform selectedPlatform = null;
    private static int orderIdCounter = 1001;
    private static double appliedDiscountPercent = 0;
    private static List<String> specialRequests = new ArrayList<>();

    public static void main(String[] args) {
        displayWelcome();
        currentOrder = new Order(orderIdCounter++, "Guest");

        boolean running = true;
        while (running) {
            int choice = displayMainMenu();
            switch (choice) {
                case 1: selectDeliveryPlatform(); break;
                case 2: browseAndOrderFood(); break;
                case 3: createComboMeal(); break;
                case 4: addEnhancements(); break;
                case 5: reviewCart(); break;
                case 6: completeOrder(); break;
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
        if (selectedPlatform != null) {
            System.out.println("Ordering via: " + selectedPlatform.getPlatformName());
            System.out.println("-".repeat(70));
        }
        System.out.println("1. Select Delivery Platform" + (selectedPlatform == null ? " (REQUIRED FIRST)" : ""));
        System.out.println("2. Browse Menu & Order Food");
        System.out.println("3. Build Your Own Combo Meal");
        System.out.println("4. Apply Discounts & Special Requests (DECORATOR PATTERN)");
        System.out.println("5. Review Your Order (COMPOSITE PATTERN)");
        System.out.println("6. Checkout & Pay");
        System.out.println("7. Exit");
        System.out.println("=".repeat(70));
        System.out.print("\nYour choice: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void selectDeliveryPlatform() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("SELECT DELIVERY PLATFORM (ADAPTER PATTERN)");
        System.out.println("=".repeat(70));
        System.out.println("\n1. UberEats");
        System.out.println("2. DoorDash");
        System.out.println("3. Glovo");
        System.out.println("0. Back");
        System.out.print("\nSelect platform: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }

        DeliveryPlatform newPlatform = null;
        switch (choice) {
            case 1:
                newPlatform = new UberEatsAdapter();
                System.out.println("\nYou'll order via UberEats");
                break;
            case 2:
                newPlatform = new DoorDashAdapter();
                System.out.println("\nYou'll order via DoorDash");
                break;
            case 3:
                newPlatform = new GlovoAdapter();
                System.out.println("\nYou'll order via Glovo");
                break;
            case 0:
                return;
            default:
                System.out.println("Invalid choice!");
                return;
        }

        if (selectedPlatform != null && currentOrder.getItems().size() > 0) {
            System.out.println("\nChanging platform will clear your cart!");
            System.out.print("Continue? (y/n): ");
            if (!scanner.nextLine().trim().equalsIgnoreCase("y")) {
                return;
            }
            currentOrder = new Order(orderIdCounter++, customerName.isEmpty() ? "Guest" : customerName);
            appliedDiscountPercent = 0;
            specialRequests.clear();
        }

        selectedPlatform = newPlatform;
    }

    private static void browseAndOrderFood() {
        if (selectedPlatform == null) {
            System.out.println("\nPlease select a delivery platform first!");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("OUR MENU");
        System.out.println("=".repeat(70));
        System.out.println("\n1. Pizza - Delicious wood-fired pizza");
        System.out.println("2. Burger - Juicy grilled burger");
        System.out.println("3. Salad - Fresh and healthy salad");
        System.out.println("0. Back to main menu");
        System.out.print("\nWhat would you like? ");

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

        System.out.print("\nWould you like to customize it? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            food = addCustomIngredients(food, foodType);
        }

        currentOrder.add((MenuComponent) food);
        System.out.println("\n" + food.getName() + " added to your order!");

        System.out.print("\nOrder something else? (y/n): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            browseAndOrderFood();
        }
    }

    private static void createComboMeal() {
        if (selectedPlatform == null) {
            System.out.println("\nPlease select a delivery platform first!");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("BUILD YOUR OWN COMBO MEAL (COMPOSITE PATTERN)");
        System.out.println("=".repeat(70));
        System.out.println("\nCreate your perfect meal combo!");
        System.out.print("Give your combo a name (e.g., 'My Special Combo'): ");
        String comboName = scanner.nextLine().trim();
        if (comboName.isEmpty()) comboName = "My Combo";

        OrderItem combo = new OrderItem(comboName);

        System.out.println("\n--- STEP 1: Choose Your Main Dish ---");
        System.out.println("1. Pizza");
        System.out.println("2. Burger");
        System.out.println("3. Salad");
        System.out.print("Main dish: ");

        try {
            int mainChoice = Integer.parseInt(scanner.nextLine());
            String foodType = getFoodType(mainChoice);
            if (foodType != null) {
                FoodFactory factory = FoodFactoryProvider.getFactory(foodType);
                Food mainDish = factory.createFood();

                System.out.print("Customize it? (y/n): ");
                if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                    mainDish = addCustomIngredients(mainDish, foodType);
                }

                combo.add((MenuComponent) mainDish);
                System.out.println("Main dish added!");
            }
        } catch (Exception e) {
            System.out.println("Invalid selection, skipping main dish");
        }

        System.out.println("\n--- STEP 2: Pick a Side ---");
        System.out.println("1. French Fries ($3.00)");
        System.out.println("2. Onion Rings ($3.50)");
        System.out.println("3. Garlic Bread ($3.50)");
        System.out.println("4. Side Salad ($2.50)");
        System.out.println("0. No side");
        System.out.print("Side: ");

        try {
            int sideChoice = Integer.parseInt(scanner.nextLine());
            String sideName = null;
            double sidePrice = 0;

            switch (sideChoice) {
                case 1: sideName = "French Fries"; sidePrice = 3.00; break;
                case 2: sideName = "Onion Rings"; sidePrice = 3.50; break;
                case 3: sideName = "Garlic Bread"; sidePrice = 3.50; break;
                case 4: sideName = "Side Salad"; sidePrice = 2.50; break;
            }

            if (sideName != null) {
                combo.add(new SimpleItem(sideName, sidePrice));
                System.out.println(sideName + " added!");
            }
        } catch (Exception e) {
            System.out.println("Skipping side...");
        }

        System.out.println("\n--- STEP 3: Choose Your Drink ---");
        System.out.println("1. Coca-Cola ($2.50)");
        System.out.println("2. Pepsi ($2.50)");
        System.out.println("3. Sprite ($2.50)");
        System.out.println("4. Water ($1.00)");
        System.out.println("5. Orange Juice ($3.00)");
        System.out.println("0. No drink");
        System.out.print("Drink: ");

        try {
            int drinkChoice = Integer.parseInt(scanner.nextLine());
            String drinkName = null;
            double drinkPrice = 0;

            switch (drinkChoice) {
                case 1: drinkName = "Coca-Cola"; drinkPrice = 2.50; break;
                case 2: drinkName = "Pepsi"; drinkPrice = 2.50; break;
                case 3: drinkName = "Sprite"; drinkPrice = 2.50; break;
                case 4: drinkName = "Water"; drinkPrice = 1.00; break;
                case 5: drinkName = "Orange Juice"; drinkPrice = 3.00; break;
            }

            if (drinkName != null) {
                combo.add(new SimpleItem(drinkName, drinkPrice));
                System.out.println(drinkName + " added!");
            }
        } catch (Exception e) {
            System.out.println("Skipping drink...");
        }

        System.out.println("\n--- STEP 4: Add Dessert? (Optional) ---");
        System.out.println("1. Ice Cream ($3.50)");
        System.out.println("2. Brownie ($4.00)");
        System.out.println("3. Apple Pie ($4.50)");
        System.out.println("0. No dessert");
        System.out.print("Dessert: ");

        try {
            int dessertChoice = Integer.parseInt(scanner.nextLine());
            String dessertName = null;
            double dessertPrice = 0;

            switch (dessertChoice) {
                case 1: dessertName = "Ice Cream"; dessertPrice = 3.50; break;
                case 2: dessertName = "Brownie"; dessertPrice = 4.00; break;
                case 3: dessertName = "Apple Pie"; dessertPrice = 4.50; break;
            }

            if (dessertName != null) {
                combo.add(new SimpleItem(dessertName, dessertPrice));
                System.out.println(dessertName + " added!");
            }
        } catch (Exception e) {
            System.out.println("No dessert selected.");
        }

        if (combo.getPrice() > 0) {
            currentOrder.add(combo);
            System.out.println("\n" + "=".repeat(70));
            System.out.println("Your combo is ready!");
            System.out.printf("Total combo price: $%.2f%n", combo.getPrice());
            System.out.println("=".repeat(70));
        } else {
            System.out.println("\nCombo is empty. Nothing added to cart.");
        }
    }

    private static void addEnhancements() {
        if (selectedPlatform == null) {
            System.out.println("\nPlease select a delivery platform first!");
            return;
        }

        if (currentOrder.getItems().isEmpty()) {
            System.out.println("\nYour cart is empty! Add items first.");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("DECORATOR PATTERN - Apply Discounts & Special Requests");
        System.out.println("=".repeat(70));
        System.out.printf("Current Order Total: $%.2f%n", currentOrder.getPrice());
        if (appliedDiscountPercent > 0) {
            System.out.printf("Current Discount: %.0f%% OFF%n", appliedDiscountPercent);
        }

        System.out.println("\n1. Apply Discount Coupon (10-50% OFF)");
        System.out.println("2. Express Delivery (+$5.00)");
        System.out.println("3. Add Special Cooking Instructions");
        System.out.println("4. Include Gift Message (+$1.50)");
        System.out.println("5. Earn Loyalty Points");
        System.out.println("0. Back");
        System.out.print("\nYour choice: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return;
        }

        switch (choice) {
            case 1:
                System.out.print("\nEnter discount percentage (10-50): ");
                try {
                    int discount = Integer.parseInt(scanner.nextLine());
                    if (discount >= 10 && discount <= 50) {
                        appliedDiscountPercent = discount;
                        double savings = currentOrder.getPrice() * (discount / 100.0);
                        System.out.printf("\n" + discount + "%% discount applied!");
                        System.out.printf("\nYou save: $%.2f%n", savings);
                        System.out.printf("New total: $%.2f%n",
                                currentOrder.getPrice() * (1 - discount/100.0));
                    } else {
                        System.out.println("\nDiscount must be between 10% and 50%");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\nInvalid discount!");
                }
                break;
            case 2:
                specialRequests.add("EXPRESS_DELIVERY");
                System.out.println("\nExpress delivery added! (+$5.00)");
                System.out.println("Your order will arrive in 30 minutes or less.");
                break;
            case 3:
                System.out.print("\nSpecial instructions (e.g., 'no onions', 'extra sauce'): ");
                String instructions = scanner.nextLine();
                specialRequests.add("INSTRUCTIONS: " + instructions);
                System.out.println("Your instructions: \"" + instructions + "\"");
                break;
            case 4:
                System.out.print("\nGift message: ");
                String message = scanner.nextLine();
                specialRequests.add("GIFT_MESSAGE: " + message);
                System.out.println("Gift message added: \"" + message + "\" (+$1.50)");
                break;
            case 5:
                int points = (int)(currentOrder.getPrice() * 10);
                System.out.printf("\nYou'll earn approximately %d loyalty points with this order!%n", points);
                System.out.println("(10 points per dollar spent)");
                break;
        }
    }

    private static void reviewCart() {
        if (selectedPlatform == null) {
            System.out.println("\nPlease select a delivery platform first!");
            return;
        }

        if (currentOrder.getItems().isEmpty()) {
            System.out.println("\nYour cart is empty!");
            System.out.println("Browse our menu to add items.");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("YOUR ORDER (COMPOSITE PATTERN - Hierarchical Display)");
        System.out.println("=".repeat(70));

        currentOrder.display(0);

        double subtotal = currentOrder.getPrice();
        double discount = subtotal * (appliedDiscountPercent / 100.0);
        double subtotalAfterDiscount = subtotal - discount;

        double expressDeliveryFee = 0;
        double giftMessageFee = 0;
        for (String request : specialRequests) {
            if (request.equals("EXPRESS_DELIVERY")) {
                expressDeliveryFee = 5.00;
            }
            if (request.startsWith("GIFT_MESSAGE:")) {
                giftMessageFee = 1.50;
            }
        }

        double deliveryFee = config.getDeliveryFee() + expressDeliveryFee;
        double grandTotal = subtotalAfterDiscount + deliveryFee + giftMessageFee;

        System.out.println("\n" + "-".repeat(70));
        System.out.printf("Subtotal: $%.2f%n", subtotal);

        if (appliedDiscountPercent > 0) {
            System.out.printf("Discount (%.0f%% OFF): -$%.2f%n", appliedDiscountPercent, discount);
            System.out.printf("Subtotal after discount: $%.2f%n", subtotalAfterDiscount);
        }

        if (expressDeliveryFee > 0) {
            System.out.printf("Express Delivery Fee: $%.2f%n", expressDeliveryFee);
        }
        System.out.printf("Standard Delivery Fee: $%.2f%n", config.getDeliveryFee());

        if (giftMessageFee > 0) {
            System.out.printf("Gift Message: $%.2f%n", giftMessageFee);
        }

        if (!specialRequests.isEmpty()) {
            System.out.println("\nSpecial Requests:");
            for (String request : specialRequests) {
                if (request.startsWith("INSTRUCTIONS:")) {
                    System.out.println("  - " + request.substring(14));
                } else if (request.startsWith("GIFT_MESSAGE:")) {
                    System.out.println("  - Gift: " + request.substring(14));
                } else if (request.equals("EXPRESS_DELIVERY")) {
                    System.out.println("  - Express Delivery (30 min)");
                }
            }
        }

        System.out.println("=".repeat(70));
        System.out.printf("TOTAL TO PAY: $%.2f%n", grandTotal);
        System.out.println("=".repeat(70));
    }

    private static void completeOrder() {
        if (selectedPlatform == null) {
            System.out.println("\nPlease select a delivery platform first!");
            return;
        }

        if (currentOrder.getItems().isEmpty()) {
            System.out.println("\nYour cart is empty!");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("CHECKOUT");
        System.out.println("=".repeat(70));

        if (customerName.isEmpty()) {
            System.out.print("\nYour name: ");
            customerName = scanner.nextLine().trim();
            if (customerName.isEmpty()) customerName = "Guest";
        }

        if (deliveryAddress.isEmpty()) {
            System.out.print("Delivery address: ");
            deliveryAddress = scanner.nextLine().trim();
            if (deliveryAddress.isEmpty()) {
                System.out.println("Address is required!");
                return;
            }
        }

        System.out.println("\n--- Payment Method (ADAPTER PATTERN) ---");
        System.out.println("1. Cash on Delivery");
        System.out.println("2. PayPal");
        System.out.println("3. Credit Card (Stripe)");
        System.out.print("\nPay with: ");

        int paymentChoice = 0;
        try {
            paymentChoice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
            return;
        }

        PaymentProcessor payment = null;
        switch (paymentChoice) {
            case 1: payment = new CashPayment(); break;
            case 2: payment = new PayPalAdapter(); break;
            case 3: payment = new StripeAdapter(); break;
            default:
                System.out.println("Invalid payment method!");
                return;
        }

        double subtotal = currentOrder.getPrice();
        double discount = subtotal * (appliedDiscountPercent / 100.0);
        double subtotalAfterDiscount = subtotal - discount;

        double expressDeliveryFee = 0;
        double giftMessageFee = 0;
        for (String request : specialRequests) {
            if (request.equals("EXPRESS_DELIVERY")) expressDeliveryFee = 5.00;
            if (request.startsWith("GIFT_MESSAGE:")) giftMessageFee = 1.50;
        }

        double total = subtotalAfterDiscount + config.getDeliveryFee() + expressDeliveryFee + giftMessageFee;

        System.out.println("\n" + "-".repeat(70));
        System.out.println("Processing your order via " + selectedPlatform.getPlatformName() + "...");

        for (MenuComponent item : currentOrder.getItems()) {
            if (item instanceof Food) {
                selectedPlatform.publishMenuItem((Food)item);
            }
        }

        String orderId = "ORD" + currentOrder.getOrderId();
        selectedPlatform.acceptOrder(orderId, total);

        boolean paymentSuccess = payment.processPayment(customerName, total);

        if (paymentSuccess) {
            System.out.println("\n" + "=".repeat(70));
            System.out.println("ORDER CONFIRMED!");
            System.out.println("=".repeat(70));
            System.out.println("Order ID: " + orderId);
            System.out.println("Customer: " + customerName);
            System.out.println("Delivering to: " + deliveryAddress);
            System.out.println("Via: " + selectedPlatform.getPlatformName());
            System.out.printf("Amount Paid: $%.2f%n", total);
            if (appliedDiscountPercent > 0) {
                System.out.printf("You saved: $%.2f with your discount!%n", discount);
            }
            System.out.println("\nEstimated delivery: " + (expressDeliveryFee > 0 ? "30" : "45-60") + " minutes");
            System.out.println("Confirmation sent to your email");
            System.out.println("=".repeat(70));
            System.out.println("\nThank you for your order!");

            currentOrder = new Order(orderIdCounter++, customerName);
            deliveryAddress = "";
            appliedDiscountPercent = 0;
            specialRequests.clear();
        } else {
            System.out.println("\nPayment failed! Please try again.");
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
        IngredientCatalog.displayIngredients(foodType);
        List<Ingredient> availableIngredients = IngredientCatalog.getIngredientsForType(foodType);

        System.out.print("\nSelect extras (comma-separated, e.g., 1,3,5) or press Enter to skip: ");
        String input = scanner.nextLine().trim();

        if (!input.isEmpty()) {
            FoodBuilder builder = new FoodBuilder(food);
            String[] selections = input.split(",");
            for (String sel : selections) {
                try {
                    int index = Integer.parseInt(sel.trim()) - 1;
                    if (index >= 0 && index < availableIngredients.size()) {
                        builder.addIngredient(availableIngredients.get(index));
                        System.out.println("  Added: " + availableIngredients.get(index).getName());
                    }
                } catch (NumberFormatException e) {
                    // Skip invalid input
                }
            }
            food = builder.build();
        }

        return food;
    }

    private static void displayGoodbye() {
        System.out.println("\n" + "=".repeat(70));
        System.out.println("Thank you for choosing " + config.getRestaurantName() + "!");
        System.out.println("We hope to serve you again soon!");
        System.out.println("=".repeat(70));
    }
}
