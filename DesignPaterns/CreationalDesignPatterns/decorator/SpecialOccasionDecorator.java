package decorator;

import models.Food;

// Concrete Decorator - adds special occasion message
public class SpecialOccasionDecorator extends FoodDecorator {
    private String occasionMessage;
    private static final double MESSAGE_CARD_FEE = 1.50;

    public SpecialOccasionDecorator(Food food, String occasionMessage) {
        super(food);
        this.occasionMessage = occasionMessage;
    }

    @Override
    public String getName() {
        return decoratedFood.getName() + " [Special Occasion]";
    }

    @Override
    public double getPrice() {
        return decoratedFood.getPrice() + MESSAGE_CARD_FEE;
    }

    @Override
    public void prepare() {
        decoratedFood.prepare();
        System.out.println(" Special occasion card included");
        System.out.println("   Message: \"" + occasionMessage + "\"");
    }

    public String getOccasionMessage() {
        return occasionMessage;
    }
}
