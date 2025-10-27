package factory;

import models.Food;

// Abstract Creator
public abstract class FoodFactory {

    // Factory Method - abstract, to be implemented by subclasses
    public abstract Food createFood();

    // Template method that uses the factory method
    public Food orderFood() {
        Food food = createFood();  // Call factory method
        System.out.println("✓ " + food.getName() + " created by factory");
        return food;
    }
}
