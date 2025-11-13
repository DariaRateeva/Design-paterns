package decorator;

import models.Food;

// Concrete Decorator - adds express delivery functionality
public class ExpressDeliveryDecorator extends FoodDecorator {
    private static final double EXPRESS_FEE = 5.00;

    public ExpressDeliveryDecorator(Food food) {
        super(food);
    }

    @Override
    public String getName() {
        return decoratedFood.getName() + " [Express Delivery]";
    }

    @Override
    public double getPrice() {
        return decoratedFood.getPrice() + EXPRESS_FEE;
    }

    @Override
    public void prepare() {
        decoratedFood.prepare();
        System.out.println(" Express delivery added - Priority processing!");
    }

    public String getDeliveryInfo() {
        return "Express delivery in 30 minutes or less";
    }
}
