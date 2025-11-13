package decorator;

import models.Food;

// Concrete Decorator - applies percentage discount
public class DiscountCouponDecorator extends FoodDecorator {
    private double discountPercentage;

    public DiscountCouponDecorator(Food food, double discountPercentage) {
        super(food);
        this.discountPercentage = Math.min(discountPercentage, 50.0); // Max 50% discount
    }

    @Override
    public String getName() {
        return decoratedFood.getName() + " [" + (int)discountPercentage + "% OFF]";
    }

    @Override
    public double getPrice() {
        double originalPrice = decoratedFood.getPrice();
        double discount = originalPrice * (discountPercentage / 100.0);
        return originalPrice - discount;
    }

    @Override
    public void prepare() {
        decoratedFood.prepare();
        System.out.println(" Discount coupon applied: " + (int)discountPercentage + "% OFF");
    }

    public double getSavedAmount() {
        return decoratedFood.getPrice() * (discountPercentage / 100.0);
    }
}
