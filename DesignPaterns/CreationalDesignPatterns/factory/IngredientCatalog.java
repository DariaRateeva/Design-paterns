package factory;

import models.Ingredient;
import java.util.*;

public class IngredientCatalog {

    // Pizza ingredients
    public static List<Ingredient> getPizzaIngredients() {
        return Arrays.asList(
                new Ingredient("Mozzarella Cheese", 1.50),
                new Ingredient("Tomato Sauce", 0.75),
                new Ingredient("Pepperoni", 2.00),
                new Ingredient("Mushrooms", 1.25),
                new Ingredient("Bell Peppers", 1.00),
                new Ingredient("Onions", 0.75),
                new Ingredient("Olives", 1.00),
                new Ingredient("Basil", 0.50),
                new Ingredient("Extra Cheese", 1.75)
        );
    }

    // Burger ingredients
    public static List<Ingredient> getBurgerIngredients() {
        return Arrays.asList(
                new Ingredient("Beef Patty", 2.50),
                new Ingredient("Cheese Slice", 1.00),
                new Ingredient("Lettuce", 0.50),
                new Ingredient("Tomato", 0.50),
                new Ingredient("Onion", 0.40),
                new Ingredient("Pickles", 0.40),
                new Ingredient("Bacon", 1.50),
                new Ingredient("Avocado", 1.75),
                new Ingredient("Special Sauce", 0.75)
        );
    }

    // Salad ingredients
    public static List<Ingredient> getSaladIngredients() {
        return Arrays.asList(
                new Ingredient("Lettuce Mix", 1.00),
                new Ingredient("Cherry Tomatoes", 1.25),
                new Ingredient("Cucumber", 0.75),
                new Ingredient("Carrots", 0.60),
                new Ingredient("Feta Cheese", 1.50),
                new Ingredient("Grilled Chicken", 2.50),
                new Ingredient("Croutons", 0.75),
                new Ingredient("Caesar Dressing", 0.80),
                new Ingredient("Parmesan Cheese", 1.25)
        );
    }

    public static void displayIngredients(String foodType) {
        List<Ingredient> ingredients;

        switch (foodType.toLowerCase()) {
            case "pizza":
                ingredients = getPizzaIngredients();
                System.out.println("\nPIZZA INGREDIENTS:");
                break;
            case "burger":
                ingredients = getBurgerIngredients();
                System.out.println("\nBURGER INGREDIENTS:");
                break;
            case "salad":
                ingredients = getSaladIngredients();
                System.out.println("\nSALAD INGREDIENTS:");
                break;
            default:
                return;
        }

        System.out.println("─────────────────────────────────────────");
        for (int i = 0; i < ingredients.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, ingredients.get(i));
        }
        System.out.println("─────────────────────────────────────────");
    }

    // Add this single method to your existing IngredientCatalog.java
    public static List<Ingredient> getIngredientsForType(String foodType) {
        switch (foodType.toLowerCase()) {
            case "pizza":
                return getPizzaIngredients();
            case "burger":
                return getBurgerIngredients();
            case "salad":
                return getSaladIngredients();
            default:
                return new ArrayList<>();
        }
    }

}
