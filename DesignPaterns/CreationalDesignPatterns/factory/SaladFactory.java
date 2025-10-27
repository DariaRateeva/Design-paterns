package factory;

import models.Food;
import models.Salad;

// Concrete Creator for Salad
public class SaladFactory extends FoodFactory {

    @Override
    public Food createFood() {
        return new Salad();
    }
}
