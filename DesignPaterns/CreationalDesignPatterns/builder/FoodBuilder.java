package builder;

import models.*;

public class FoodBuilder {
    private Food food;

    public FoodBuilder(Food food) {
        this.food = food;
    }

    // Builder method - adds ingredient and returns this for chaining
    public FoodBuilder addIngredient(Ingredient ingredient) {
        if (food instanceof Pizza) {
            ((Pizza) food).addIngredient(ingredient);
        } else if (food instanceof Burger) {
            ((Burger) food).addIngredient(ingredient);
        } else if (food instanceof Salad) {
            ((Salad) food).addIngredient(ingredient);
        }
        return this;  // Enable method chaining
    }

    // Convenience method for adding multiple ingredients
    public FoodBuilder addIngredient(String name, double price) {
        return addIngredient(new Ingredient(name, price));
    }

    // Build method - returns the completed Food object
    public Food build() {
        return food;
    }

    // Reset method - creates a new food of the same type
    public FoodBuilder reset() {
        if (food instanceof Pizza) {
            food = new Pizza();
        } else if (food instanceof Burger) {
            food = new Burger();
        } else if (food instanceof Salad) {
            food = new Salad();
        }
        return this;
    }
}
