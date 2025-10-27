package client;

import factory.FoodFactory;
import factory.FoodFactoryProvider;
import factory.IngredientCatalog;
import builder.MealBuilder;
import builder.MealDirector;
import builder.IMealBuilder;
import builder.FoodBuilder;
import models.*;
import singleton.SystemConfig;
import singleton.OrderManager;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static SystemConfig config = SystemConfig.getInstance();

    public static void main(String[] args) {
        displayWelcome();

        boolean running = true;
        while (running) {
            int choice = displayMainMenu();

            switch (choice) {
                case 1:
                    orderSingleFood();
                    break;
                case 2:
                    buildCustomMeal();
                    break;
                case 3:
                    orderPredefinedMeal();
                    break;
                case 4:
                    viewOrderHistory();
                    break;
                case 5:
                    running = false;
                    displayGoodbye();
                    break;
                default:
                    System.out.println("\nInvalid choice! Please select 1-5.");
            }
        }

        scanner.close();
    }

    private static void displayWelcome() {
        System.out.println("\n===========================================");
        System.out.println("    FOOD ORDERING SYSTEM - WELCOME");
        System.out.println("===========================================\n");
        System.out.println("Restaurant: " + config.getRestaurantName());
        System.out.println("Status: " + config.getRestaurantStatus());
    }

    private static int displayMainMenu() {
        System.out.println("\n===========================================");
        System.out.println("               MAIN MENU");
        System.out.println("===========================================");
        System.out.println("1. Order Single Food");
        System.out.println("2. Build Custom Meal");
        System.out.println("3. Order Predefined Meal");
        System.out.println("4. View Order History");
        System.out.println("5. Exit");
        System.out.println("===========================================");
        System.out.print("\nEnter your choice (1-5): ");

        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void orderSingleFood() {
        System.out.println("\n===========================================");
        System.out.println("           ORDER SINGLE FOOD");
        System.out.println("===========================================");

        FoodFactoryProvider.displayMenu();
        System.out.print("\nEnter food type (pizza/burger/salad): ");
        String foodType = scanner.nextLine().trim();

        try {
            FoodFactory factory = FoodFactoryProvider.getFactory(foodType);
            Food food = factory.orderFood();

            System.out.print("\nWould you like to customize ingredients? (yes/no): ");
            String customize = scanner.nextLine().trim().toLowerCase();

            if (customize.equals("yes") || customize.equals("y")) {
                customizeFood(food, foodType);
            }

            System.out.println("\n===========================================");
            food.prepare();

            if (food instanceof Pizza) {
                System.out.println("\n" + ((Pizza) food).getDetailedDescription());
            } else if (food instanceof Burger) {
                System.out.println("\n" + ((Burger) food).getDetailedDescription());
            } else if (food instanceof Salad) {
                System.out.println("\n" + ((Salad) food).getDetailedDescription());
            }

            System.out.println("\nFood Price: $" + String.format("%.2f", food.getPrice()));
            System.out.println("Delivery Fee: $" + String.format("%.2f", config.getDeliveryFee()));
            System.out.println("===========================================");
            System.out.println("Total: $" + String.format("%.2f", food.getPrice() + config.getDeliveryFee()));

            System.out.print("\nEnter your name to complete order: ");
            String customerName = scanner.nextLine().trim();

            if (!customerName.isEmpty()) {
                OrderManager orderManager = OrderManager.getInstance();
                orderManager.placeOrder(customerName, food, null);
                System.out.println("Order confirmed! Total orders today: " + orderManager.getTotalOrders());
            }

        } catch (IllegalArgumentException e) {
            System.out.println("\nError: " + e.getMessage());
            System.out.println("Please choose from: pizza, burger, or salad");
        }
    }

    private static void customizeFood(Food food, String foodType) {
        List<Ingredient> availableIngredients;

        switch (foodType.toLowerCase()) {
            case "pizza":
                availableIngredients = IngredientCatalog.getPizzaIngredients();
                break;
            case "burger":
                availableIngredients = IngredientCatalog.getBurgerIngredients();
                break;
            case "salad":
                availableIngredients = IngredientCatalog.getSaladIngredients();
                break;
            default:
                return;
        }

        IngredientCatalog.displayIngredients(foodType);
        System.out.println("\nSelect ingredients by entering their numbers separated by commas");
        System.out.println("Example: 1,3,5 (or press Enter to skip)");
        System.out.print("Your selection: ");

        String input = scanner.nextLine().trim();
        if (input.isEmpty()) {
            return;
        }

        FoodBuilder foodBuilder = new FoodBuilder(food);
        String[] selections = input.split(",");

        System.out.println("\nBuilding your customized " + foodType + "...");
        for (String selection : selections) {
            try {
                int index = Integer.parseInt(selection.trim()) - 1;
                if (index >= 0 && index < availableIngredients.size()) {
                    Ingredient ingredient = availableIngredients.get(index);
                    foodBuilder.addIngredient(ingredient);
                    System.out.println("Added: " + ingredient.getName());
                } else {
                    System.out.println("Invalid number: " + selection.trim());
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + selection.trim());
            }
        }

        foodBuilder.build();
        System.out.println("Food customization complete!");
    }

    private static void buildCustomMeal() {
        System.out.println("\n===========================================");
        System.out.println("          BUILD CUSTOM MEAL");
        System.out.println("===========================================");
        System.out.println("Build your meal step by step\n");

        MealBuilder builder = new MealBuilder();
        Food customizedFood = null;

        System.out.println("Step 1: Choose Main Dish");
        FoodFactoryProvider.displayMenu();
        System.out.print("\nEnter main dish (pizza/burger/salad) or press Enter to skip: ");
        String mainDish = scanner.nextLine().trim();

        if (!mainDish.isEmpty()) {
            try {
                FoodFactory factory = FoodFactoryProvider.getFactory(mainDish);
                customizedFood = factory.orderFood();

                System.out.print("\nWould you like to customize ingredients? (yes/no): ");
                String customizeChoice = scanner.nextLine().trim().toLowerCase();

                if (customizeChoice.equals("yes") || customizeChoice.equals("y")) {
                    customizeFood(customizedFood, mainDish);

                    if (customizedFood instanceof Pizza) {
                        builder.setMainDish(((Pizza) customizedFood).getName() + " (Customized)");
                    } else if (customizedFood instanceof Burger) {
                        builder.setMainDish(((Burger) customizedFood).getName() + " (Customized)");
                    } else if (customizedFood instanceof Salad) {
                        builder.setMainDish(((Salad) customizedFood).getName() + " (Customized)");
                    }
                } else {
                    builder.setMainDish(customizedFood.getName());
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid choice, skipping main dish.");
            }
        }

        System.out.println("\nStep 2: Choose Side Dish");
        System.out.println("Options: French Fries, Garlic Bread, Onion Rings, Coleslaw");
        System.out.print("Enter side dish or press Enter to skip: ");
        String sideDish = scanner.nextLine().trim();
        if (!sideDish.isEmpty()) {
            builder.setSideDish(sideDish);
        }

        System.out.println("\nStep 3: Choose Drink");
        System.out.println("Options: Coca-Cola, Pepsi, Orange Juice, Water, Lemonade");
        System.out.print("Enter drink or press Enter to skip: ");
        String drink = scanner.nextLine().trim();
        if (!drink.isEmpty()) {
            builder.setDrink(drink);
        }

        System.out.println("\nStep 4: Choose Dessert");
        System.out.println("Options: Ice Cream, Tiramisu, Brownie, Cheesecake");
        System.out.print("Enter dessert or press Enter to skip: ");
        String dessert = scanner.nextLine().trim();
        if (!dessert.isEmpty()) {
            builder.setDessert(dessert);
        }

        Meal meal = builder.build();
        System.out.println("\n===========================================");
        System.out.println("         YOUR CUSTOM MEAL");
        System.out.println("===========================================");
        System.out.println(meal);

        if (customizedFood != null) {
            System.out.println("\nMain Dish Details:");
            System.out.println("-------------------------------------------");
            if (customizedFood instanceof Pizza) {
                System.out.println(((Pizza) customizedFood).getDetailedDescription());
            } else if (customizedFood instanceof Burger) {
                System.out.println(((Burger) customizedFood).getDetailedDescription());
            } else if (customizedFood instanceof Salad) {
                System.out.println(((Salad) customizedFood).getDetailedDescription());
            }
            System.out.println("\nMain Dish Price: $" + String.format("%.2f", customizedFood.getPrice()));
        }

        System.out.println("\nDelivery Fee: $" + String.format("%.2f", config.getDeliveryFee()));
        System.out.println("===========================================");

        if (customizedFood != null) {
            System.out.println("Total: $" + String.format("%.2f", customizedFood.getPrice() + config.getDeliveryFee()));
        }

        System.out.print("\nEnter your name to complete order: ");
        String customerName = scanner.nextLine().trim();

        if (!customerName.isEmpty()) {
            OrderManager orderManager = OrderManager.getInstance();
            orderManager.placeOrder(customerName, customizedFood, meal);
            System.out.println("Order confirmed! Total orders today: " + orderManager.getTotalOrders());
        }
    }

    private static void orderPredefinedMeal() {
        System.out.println("\n===========================================");
        System.out.println("       ORDER PREDEFINED MEAL");
        System.out.println("===========================================");

        IMealBuilder builder = new MealBuilder();
        MealDirector director = new MealDirector(builder);

        System.out.println("Choose from our predefined meal combinations:");
        System.out.println("-------------------------------------------");
        System.out.println("1. Standard Meal");
        System.out.println("   (Your choice + Fries + Coke + Ice Cream)");
        System.out.println("\n2. Healthy Meal");
        System.out.println("   (Your choice + Salad + Water)");
        System.out.println("\n3. Kids Meal");
        System.out.println("   (Your choice + Onion Rings + Juice + Brownie)");
        System.out.println("\n4. Budget Meal");
        System.out.println("   (Your choice + Water)");
        System.out.println("-------------------------------------------");
        System.out.print("\nEnter choice (1-4): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter main dish (pizza/burger/salad): ");
            String mainDish = scanner.nextLine().trim();

            Meal meal = null;
            String mealType = "";

            switch (choice) {
                case 1:
                    meal = director.constructStandardMeal(mainDish);
                    mealType = "Standard Meal";
                    break;
                case 2:
                    meal = director.constructHealthyMeal(mainDish);
                    mealType = "Healthy Meal";
                    break;
                case 3:
                    meal = director.constructKidsMeal(mainDish);
                    mealType = "Kids Meal";
                    break;
                case 4:
                    meal = director.constructBudgetMeal(mainDish);
                    mealType = "Budget Meal";
                    break;
                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            System.out.println("\n===========================================");
            System.out.println("         " + mealType.toUpperCase());
            System.out.println("===========================================");
            System.out.println(meal);
            System.out.println("\nDelivery Fee: $" + String.format("%.2f", config.getDeliveryFee()));
            System.out.println("===========================================");

            System.out.print("\nEnter your name to complete order: ");
            String customerName = scanner.nextLine().trim();

            if (!customerName.isEmpty()) {
                OrderManager orderManager = OrderManager.getInstance();
                orderManager.placeOrder(customerName, null, meal);
                System.out.println("Order confirmed! Total orders today: " + orderManager.getTotalOrders());
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input!");
        }
    }

    private static void viewOrderHistory() {
        System.out.println("\n===========================================");
        System.out.println("           ORDER HISTORY");
        System.out.println("===========================================");

        OrderManager orderManager = OrderManager.getInstance();
        orderManager.displayAllOrders();
    }

    private static void displayGoodbye() {
        System.out.println("\n===========================================");
        System.out.println("             THANK YOU!");
        System.out.println("===========================================");

        OrderManager orderManager = OrderManager.getInstance();
        if (orderManager.getTotalOrders() > 0) {
            System.out.println("\nSession Summary:");
            System.out.println("Orders placed: " + orderManager.getTotalOrders());
            System.out.println("Revenue: $" + String.format("%.2f", orderManager.getTotalRevenue()));
        }

        System.out.println("\nHave a great day!\n");
    }
}
