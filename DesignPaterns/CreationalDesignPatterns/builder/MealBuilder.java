package builder;

import models.Meal;

// Concrete Builder
public class MealBuilder implements IMealBuilder {
    private String mainDish;
    private String sideDish;
    private String drink;
    private String dessert;

    @Override
    public IMealBuilder setMainDish(String mainDish) {
        this.mainDish = mainDish;
        return this;
    }

    @Override
    public IMealBuilder setSideDish(String sideDish) {
        this.sideDish = sideDish;
        return this;
    }

    @Override
    public IMealBuilder setDrink(String drink) {
        this.drink = drink;
        return this;
    }

    @Override
    public IMealBuilder setDessert(String dessert) {
        this.dessert = dessert;
        return this;
    }

    @Override
    public IMealBuilder reset() {
        this.mainDish = null;
        this.sideDish = null;
        this.drink = null;
        this.dessert = null;
        return this;
    }

    @Override
    public Meal build() {
        return new Meal(mainDish, sideDish, drink, dessert);
    }
}
