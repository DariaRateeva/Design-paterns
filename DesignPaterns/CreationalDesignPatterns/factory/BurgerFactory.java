package factory;

import models.Food;
import models.Burger;

// Concrete Creator for Burger
public class BurgerFactory extends FoodFactory {

    @Override
    public Food createFood() {
        return new Burger();
    }
}
