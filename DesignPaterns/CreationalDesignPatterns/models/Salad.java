package models;

import java.util.ArrayList;
import java.util.List;

public class Salad implements Food {
    private String name = "Custom Salad";
    private double basePrice = 3.99;
    private List<Ingredient> ingredients;

    public Salad() {
        this.ingredients = new ArrayList<>();
    }

    public Salad(List<Ingredient> ingredients) {
        this.ingredients = ingredients != null ? ingredients : new ArrayList<>();
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    @Override
    public void prepare() {
        System.out.println("Preparing " + name + "...");
        System.out.println("Mixing fresh ingredients:");
        if (ingredients.isEmpty()) {
            System.out.println("  - Plain lettuce base");
        } else {
            for (Ingredient ingredient : ingredients) {
                System.out.println("  - " + ingredient.getName());
            }
        }
        System.out.println("Tossing and serving chilled!");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        double total = basePrice;
        for (Ingredient ingredient : ingredients) {
            total += ingredient.getPrice();
        }
        return total;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public String getDetailedDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append(" - Base: $").append(String.format("%.2f", basePrice)).append("\n");
        if (!ingredients.isEmpty()) {
            sb.append("Ingredients:\n");
            for (Ingredient ing : ingredients) {
                sb.append("  • ").append(ing.toString()).append("\n");
            }
        }
        sb.append("Total Price: $").append(String.format("%.2f", getPrice()));
        return sb.toString();
    }
}
