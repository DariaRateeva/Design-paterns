package builder;

import models.Meal;

// Director class - controls construction sequence
public class MealDirector {
    private IMealBuilder builder;

    public MealDirector(IMealBuilder builder) {
        this.builder = builder;
    }

    public void setBuilder(IMealBuilder builder) {
        this.builder = builder;
    }

    // Constructs a "Standard Meal" with predefined steps
    public Meal constructStandardMeal(String mainDish) {
        return builder
                .reset()
                .setMainDish(mainDish)
                .setSideDish("French Fries")
                .setDrink("Coca-Cola")
                .setDessert("Ice Cream")
                .build();
    }

    // Constructs a "Healthy Meal" with different configuration
    public Meal constructHealthyMeal(String mainDish) {
        return builder
                .reset()
                .setMainDish(mainDish)
                .setSideDish("Salad")
                .setDrink("Water")
                // No dessert for healthy meal
                .build();
    }

    // Constructs a "Kids Meal"
    public Meal constructKidsMeal(String mainDish) {
        return builder
                .reset()
                .setMainDish(mainDish)
                .setSideDish("Onion Rings")
                .setDrink("Orange Juice")
                .setDessert("Brownie")
                .build();
    }

    // Constructs a "Budget Meal" (minimal options)
    public Meal constructBudgetMeal(String mainDish) {
        return builder
                .reset()
                .setMainDish(mainDish)
                .setDrink("Water")
                .build();
    }
}
