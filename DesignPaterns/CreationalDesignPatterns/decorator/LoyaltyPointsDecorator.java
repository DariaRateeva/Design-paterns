package decorator;

import models.Food;

// Concrete Decorator - adds loyalty points bonus
public class LoyaltyPointsDecorator extends FoodDecorator {
    private int bonusPoints;

    public LoyaltyPointsDecorator(Food food) {
        super(food);
        this.bonusPoints = (int)(food.getPrice() * 10); // 10 points per dollar
    }

    @Override
    public String getName() {
        return decoratedFood.getName() + " [+" + bonusPoints + " Loyalty Points]";
    }

    @Override
    public void prepare() {
        decoratedFood.prepare();
        System.out.println(" Bonus loyalty points earned: " + bonusPoints + " points");
    }

    public int getBonusPoints() {
        return bonusPoints;
    }
}
