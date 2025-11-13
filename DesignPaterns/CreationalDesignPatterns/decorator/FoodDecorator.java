package decorator;

import models.Food;

// Abstract Decorator - maintains reference to Component and implements Component interface
public abstract class FoodDecorator implements Food {
    protected Food decoratedFood;

    public FoodDecorator(Food food) {
        this.decoratedFood = food;
    }

    @Override
    public void prepare() {
        decoratedFood.prepare();
    }

    @Override
    public String getName() {
        return decoratedFood.getName();
    }

    @Override
    public double getPrice() {
        return decoratedFood.getPrice();
    }
}
