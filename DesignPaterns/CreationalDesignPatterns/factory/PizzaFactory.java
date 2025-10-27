package factory;

import models.Food;
import models.Pizza;

// Concrete Creator for Pizza
public class PizzaFactory extends FoodFactory {

    @Override
    public Food createFood() {
        return new Pizza();
    }
}
